package ktc.nhom1ktc.repository.expense.management;

import ktc.nhom1ktc.entity.Account;
import ktc.nhom1ktc.entity.Role;
import ktc.nhom1ktc.entity.expense.Status;
import ktc.nhom1ktc.entity.expense.management.category.Category;
import ktc.nhom1ktc.entity.expense.management.category.CategoryType;
import ktc.nhom1ktc.repository.RepositoryTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

class CategoryRepositoryTest extends RepositoryTests {

    private static final Set<String> commonCategoryNames = new HashSet<>() {{
        add("Apparel");
        add("Household");
        add("Education");
        add("Transportation");
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

    final LocalDateTime dateTime = LocalDateTime.now();
    final Date date = Date.from(dateTime.toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant());

    Role userRole = makeRole(RoleType.USER, date);
    Role adminRole = makeRole(RoleType.ADMIN, date);

    final String normalUsername = codePrefix + "user";
    final String adminUsername = codePrefix + "admin";

    Account userAcc = makeAccount(normalUsername, userRole, date);
    Account adminAcc = makeAccount(adminUsername, adminRole, date);

    @BeforeEach
    public void before() {
        roleRepository.findByName(RoleType.USER.name()).ifPresentOrElse(
                role -> userAcc = makeAccount(normalUsername, role, date),
                () -> saveRole(userRole));
        roleRepository.findByName(RoleType.ADMIN.name()).ifPresentOrElse(
                role -> adminAcc = makeAccount(adminUsername, role, date),
                () -> saveRole(adminRole));

        accountRepository.findByUsername(normalUsername).ifPresentOrElse(
                acc -> { acc.setRole(userRole);
                    userAcc = accountRepository.save(acc); },
                () -> userAcc = saveAccount(userAcc));
        accountRepository.findByUsername(adminUsername).ifPresentOrElse(
                acc -> { acc.setRole(adminRole);
                    adminAcc = accountRepository.save(acc); },
                () -> adminAcc = saveAccount(adminAcc));
    }

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

        List<Category> categoryList = categoryRepository.findCategoriesByAccountId(adminAcc.getId());

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

        List<Category> categoryList = categoryRepository.findCategoriesByTypeOrAccountId(CategoryType.COMMON, userAcc.getId());

        Set<String> expectedCategoryNames = categoryList.stream()
                .map(Category::getName)
                .collect(Collectors.toSet());

        Assertions.assertTrue(expectedCategoryNames.containsAll(commonCategoryNames));
        Assertions.assertTrue(expectedCategoryNames.containsAll(personalCategoryNames));
    }

}