package ktc.nhom1ktc.service.impl.expense;

import ktc.nhom1ktc.entity.Account;
import ktc.nhom1ktc.entity.expense.Status;
import ktc.nhom1ktc.entity.expense.management.category.Category;
import ktc.nhom1ktc.entity.expense.management.category.CategoryType;
import ktc.nhom1ktc.event.AdminInitEvent;
import ktc.nhom1ktc.repository.expense.management.CategoryRepository;
import ktc.nhom1ktc.service.expense.ICategoryService;
import ktc.nhom1ktc.service.impl.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CategoryService implements ICategoryService<Category, UUID> {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private Authentication authenticationContext;

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

    @EventListener
    public void handleAdminInit(AdminInitEvent e) {
        Optional<Account> adminAccountOp = accountService.findByUsername(e.getMessage());
        adminAccountOp.ifPresentOrElse(this::initCommonCategories, RuntimeException::new);
    }

    private void initCommonCategories(Account account) {
        List<Category> categories = commonCategoryNames.stream()
                .map(name -> makeCategoryByName(name, account))
                .toList();
        categoryRepository.saveAll(categories);
    }

    @Override
    public Optional<Category> findOne(UUID id) {
        return categoryRepository.findById(id);
    }

    @Override
    public List<Category> findAll(UUID accountId) {
        return categoryRepository.findByTypeOrAccountId(CategoryType.COMMON, accountId);
    }

    @Override
    public Optional<Category> createOne(String name, UUID accountId) {

        Account account = accountService.getByID(accountId);
        if (Objects.isNull(account)) {
            return Optional.empty();
        }

        List<Category> categories = categoryRepository.findByTypeOrAccountId(CategoryType.COMMON, accountId);
        String validName = name.trim().replaceAll("\\s{2,}", " ");
        for (Category category : categories) {
            if (category.getName().equals(validName)) {
                return Optional.empty();
            }
        }

        return Optional.of(categoryRepository.save(makeCategoryByName(validName, account)));
    }

    @Override
    public Category updateCategoryNameWithRoleAdmin(UUID id, String name) {
        categoryRepository.updateCategoryNameByIdWithRoleIsAdmin(id, name, LocalDateTime.now(), authenticationContext.getName());
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category updateCategoryName(UUID id, UUID accountId, String name) {
        categoryRepository.updateCategoryNameById(id, accountId, name, LocalDateTime.now(), authenticationContext.getName());
        return categoryRepository.findById(accountId).orElse(null);
    }

    @Override
    public Category updateCategoryStatus(UUID id, UUID accountId, Status status) {
        categoryRepository.updateCategoryStatusById(id, accountId, status, LocalDateTime.now(), authenticationContext.getName());
        return categoryRepository.findById(accountId).orElse(null);
    }


    private Category makeCategoryByName(final String name, final Account account) {
        final String roleName = account.getRole().getName();
        LocalDateTime dateTime = LocalDateTime.now();
        final CategoryType type;

        if (RoleType.ADMIN.name().equals(roleName)) {
            commonCategoryNames.add(name);
            type = CategoryType.COMMON;
        } else if (RoleType.USER.name().equals(roleName)) {
            type = CategoryType.PERSONAL;
        } else {
            type = CategoryType.PERSONAL;
        }

        return Category.builder()
                .account(account)
                .type(type)
                .name(name)
                .status(Status.ACTIVATED)
                .createdAt(dateTime)
                .updatedAt(dateTime)
                .createdBy(account.getUsername())
                .updatedBy(account.getUsername())
                .build();
    }

    protected enum RoleType {
        ADMIN,
        USER,
    }
}
