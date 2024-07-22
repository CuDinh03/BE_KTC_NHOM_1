package ktc.nhom1ktc.repository.expense.management;

import ktc.nhom1ktc.entity.expense.Status;
import ktc.nhom1ktc.entity.expense.management.category.Category;
import ktc.nhom1ktc.entity.expense.management.category.CategoryType;
import ktc.nhom1ktc.repository.RepositoryTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

class CategoryRepositoryTest extends RepositoryTests {

    private static final Set<String> commonCategoryNames = new HashSet<>() {{
        add("Apparel");
        add("Household");
        add("Education");
        add("Transportation");
        add("Beauty");
        add("Gift");
        add("Health");
        add("Other");
    }};

    private static final Set<String> personalCategoryNames = new HashSet<>() {{
        add("Personal Category 1");
        add("Personal Category 2");
    }};

    Set<Category> commonCategorySet;
    Set<Category> personalCategorySet;

    private Category makeCommonCategoryByName(final String name) {
        return Category.builder()
                .account(adminAcc)
                .type(CategoryType.COMMON)
                .name(name)
                .status(Status.ACTIVATED)
                .createdAt(dateTime)
                .updatedAt(dateTime)
                .createdBy(adminUsername)
                .updatedBy(adminUsername)
                .build();
    }

    private Category makePersonalCategoryByName(final String name) {
        return Category.builder()
                .account(userAcc)
                .type(CategoryType.PERSONAL)
                .name(name)
                .status(Status.ACTIVATED)
                .createdAt(dateTime)
                .updatedAt(dateTime)
                .createdBy(normalUsername)
                .updatedBy(normalUsername)
                .build();
    }

    @Test
    public void testSaveCategories() {
        commonCategorySet = commonCategoryNames.stream()
                .map(this::makeCommonCategoryByName)
                .collect(Collectors.toSet());

        categoryRepository.saveAll(commonCategorySet);

        List<Category> categoryList = categoryRepository.findByAccountId(adminAcc.getId());

        Set<String> expectedCategoryNames = categoryList.stream()
                .map(Category::getName)
                .collect(Collectors.toSet());

        Assertions.assertEquals(expectedCategoryNames, commonCategoryNames);

    }

    @Test
    public void testLoadCommonAndPersonalCategoriesByAccountId() {
        commonCategorySet = commonCategoryNames.stream()
                .map(this::makeCommonCategoryByName)
                .collect(Collectors.toSet());

        personalCategorySet = personalCategoryNames.stream()
                .map(this::makePersonalCategoryByName)
                .collect(Collectors.toSet());

        categoryRepository.saveAll(commonCategorySet);
        categoryRepository.saveAll(personalCategorySet);

        List<Category> catList = categoryRepository.findByCreatedByOrUpdatedByOrType(normalUsername, CategoryType.COMMON);
        Set<String> catSet = catList.stream().map(Category::getName).collect(Collectors.toSet());

        List<Category> categoryList = categoryRepository.findByTypeOrAccountId(CategoryType.COMMON, userAcc.getId());
        Set<String> expectedCategoryNames = categoryList.stream().map(Category::getName).collect(Collectors.toSet());

        Assertions.assertTrue(expectedCategoryNames.containsAll(commonCategoryNames));
        Assertions.assertTrue(expectedCategoryNames.containsAll(personalCategoryNames));

        Assertions.assertEquals(expectedCategoryNames, catSet);
    }

    @Test
    public void testUpdateCategoryNameByAccount() {
        String commonCategoryName = commonCategoryNames.stream().findFirst().orElse("Common Category");
        String personalCategoryName = personalCategoryNames.stream().findFirst().orElse("Personal Category");

        Category commonCategory = categoryRepository.save(makeCommonCategoryByName(commonCategoryName));
        Category personalCategory = categoryRepository.save(makePersonalCategoryByName(personalCategoryName));

        /*
            Update one category name of type COMMON by admin account
         */
        // JPA query shared for both ADMIN and USER roles
        AtomicReference<String> updatedName = new AtomicReference<>();
        String nameToUpdate = "New COMMON name";
        UUID categoryId = commonCategory.getId(),
                accountId = adminAcc.getId();
        int updatedResult = categoryRepository.setCategoryNameById(categoryId, accountId, nameToUpdate, dateTime, adminUsername);
        Assertions.assertEquals(1, updatedResult);

        categoryRepository.findById(commonCategory.getId()).ifPresent(c -> updatedName.set(c.getName()));
        Assertions.assertEquals(nameToUpdate, updatedName.get());

        // JPA query for ADMIN role only
        nameToUpdate = "Native COMMON name";
        updatedResult = categoryRepository.setCategoryNameByIdWithRoleIsAdmin(categoryId, nameToUpdate, dateTime, adminUsername);
        Assertions.assertEquals(1, updatedResult);

        categoryRepository.findById(commonCategory.getId()).ifPresent(c -> updatedName.set(c.getName()));
        Assertions.assertEquals(nameToUpdate, updatedName.get());

        /*
            Update one category name of type PERSONAL by user account
         */
        nameToUpdate = "New PERSONAL name";
        categoryId = personalCategory.getId();
        accountId = userAcc.getId();
        updatedResult = categoryRepository.setCategoryNameById(categoryId,
                accountId,
                nameToUpdate,
                dateTime,
                normalUsername);
        Assertions.assertEquals(1, updatedResult);

        categoryRepository.findById(personalCategory.getId())
                .ifPresent(c -> updatedName.set(c.getName()));
        Assertions.assertEquals(nameToUpdate, updatedName.get());

        /*
            Update one category name of type COMMON by user account
         */
        nameToUpdate = "New COMMON name";
        categoryId = commonCategory.getId();
        accountId = userAcc.getId();
        updatedResult = categoryRepository.setCategoryNameById(categoryId,
                accountId,
                nameToUpdate,
                dateTime,
                normalUsername);
        Assertions.assertEquals(0, updatedResult);

        /*
            Update one category name of type PERSONAL by admin account
         */
        nameToUpdate = "New PERSONAL name";
        categoryId = personalCategory.getId();
        accountId = adminAcc.getId();
        updatedResult = categoryRepository.setCategoryNameById(categoryId,
                accountId,
                nameToUpdate,
                dateTime,
                adminUsername);
        Assertions.assertEquals(0, updatedResult);
    }

    @Test
    public void testUpdateCategoryStatusById() {
        String commonCategoryName = commonCategoryNames.stream().findFirst().orElse("Common Category");
        String personalCategoryName = personalCategoryNames.stream().findFirst().orElse("Personal Category");

        Category commonCategory = categoryRepository.save(makeCommonCategoryByName(commonCategoryName));
        Category personalCategory = categoryRepository.save(makePersonalCategoryByName(personalCategoryName));

        Status commonStatus = commonCategory.getStatus();
        Status personalStatus = personalCategory.getStatus();

        // Update one category name of type COMMON by admin account
        AtomicReference<Status> updatedStatus = new AtomicReference<>();
        Status statusToUpdate = Status.DEACTIVATED;
        UUID categoryId = commonCategory.getId(),
                accountId = adminAcc.getId();
        int updatedResult = categoryRepository.setCategoryStatusById(categoryId,
                accountId,
                statusToUpdate,
                dateTime,
                adminUsername);
        Assertions.assertEquals(1, updatedResult);

        categoryRepository.findById(categoryId)
                .ifPresent(c -> updatedStatus.set(c.getStatus()));
        Assertions.assertEquals(statusToUpdate, updatedStatus.get());


        Assertions.assertTrue(categoryRepository.existsByType(CategoryType.COMMON));
    }

}