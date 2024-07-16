package ktc.nhom1ktc.service.impl.expense;

import jakarta.annotation.PostConstruct;
import ktc.nhom1ktc.configuration.RoleType;
import ktc.nhom1ktc.entity.Account;
import ktc.nhom1ktc.entity.expense.Status;
import ktc.nhom1ktc.entity.expense.management.category.Category;
import ktc.nhom1ktc.entity.expense.management.category.CategoryType;
import ktc.nhom1ktc.event.AdminInitEvent;
import ktc.nhom1ktc.repository.expense.management.category.CategoryRepository;
import ktc.nhom1ktc.service.expense.ICategoryService;
import ktc.nhom1ktc.service.impl.AccountService;
import ktc.nhom1ktc.service.impl.AccountUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class CategoryService implements ICategoryService<Category, UUID> {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountUtil accountUtil;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

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

    @PostConstruct
    private void init() {
        Set<Category> dbCategories = categoryRepository.findAllByTypeAndUpdatedBy(CategoryType.COMMON, accountUtil.getUsername());
        dbCategories.forEach(c -> commonCategoryNames.add(c.getName()));
    }

    @EventListener
    public void handleAdminInit(AdminInitEvent e) {
        if (categoryRepository.existsByType(CategoryType.COMMON)) {
            return;
        }
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
    public Optional<Category> findOneByCategoryId(UUID id) {
        return categoryRepository.findById(id);
    }

    @Override
    public List<Category> findAllByAccountId(UUID accountId) {
        return categoryRepository.findByTypeOrAccountId(CategoryType.COMMON, accountId);
    }

    @Override
    public List<Category> findAllByUsername(String username) {
        return categoryRepository.findByCreatedByOrUpdatedByOrType(username, CategoryType.COMMON);
    }

    private String validateCategoryName(String name, UUID accountId) {
        if (StringUtils.isBlank(name)) {
            // throw BlackOrEmptyCategoryNameException
            return null;
        }

        List<Category> categories = categoryRepository.findByTypeOrAccountId(CategoryType.COMMON, accountId);
        String validName = name.trim().replaceAll("\\s{2,}", " ");

        for (Category category : categories) {
            if (category.getName().equals(validName)) {
                // throw DuplicateCategoryNameException
                return null;
            }
        }

        return validName;
    }

    private Account validateAccountId(UUID accountId) {
        return accountService.getByID(accountId);
    }

    @Override
    public Optional<Category> createOne(String name, UUID accountId) {
        Account account = validateAccountId(accountId);
        if (Objects.isNull(account)) {
            return Optional.empty();
        }

        String validName = validateCategoryName(name, accountId);
        if (StringUtils.isBlank(validName)) {
            return Optional.empty();
        }

        if (account.getRole().getName().equals(RoleType.ADMIN.name())) {
            commonCategoryNames.add(validName);
        }
        return Optional.of(categoryRepository.save(makeCategoryByName(validName, account)));
    }

    @Override
    public Category updateCategoryNameWithRoleAdmin(UUID id, UUID accountId, String name) {
        String validName = validateCategoryName(name, accountId);
        if (StringUtils.isBlank(validName)) {
            return null;
        }

        commonCategoryNames.add(validName);

        categoryRepository.setCategoryNameByIdWithRoleIsAdmin(id, validName, LocalDateTime.now(), accountUtil.getUsername());
        return categoryRepository.findById(id).orElseGet(() -> new Category());
    }

    @Override
    public Category updateCategoryName(UUID id, UUID accountId, String name) {
        String validName = validateCategoryName(name, accountId);
        log.info("updateCategoryName validName before {}", validName);
        if (StringUtils.isBlank(validName)) {
            // throw invalid category name exception
            return null;
        }
//        log.info("updateCategoryName validName after {}", validName);

        int updateResult = categoryRepository.setCategoryNameById(id, accountId, validName, LocalDateTime.now(), accountUtil.getUsername());
//        log.info("updateCategoryName updateResult {}", updateResult);
        return categoryRepository.findById(id).orElseGet(() -> new Category());
    }

    @Override
    public Category updateCategoryStatus(UUID id, UUID accountId, Status status) {
        int updateResult = categoryRepository.setCategoryStatusById(id, accountId, status, LocalDateTime.now(), accountUtil.getUsername());
        return categoryRepository.findById(id).orElseGet(() -> new Category());
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

}
