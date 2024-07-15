package ktc.nhom1ktc.service.impl.expense;

import ktc.nhom1ktc.entity.expense.management.category.Category;
import ktc.nhom1ktc.service.impl.AccountService;
import ktc.nhom1ktc.service.impl.AccountUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class CategoryServiceTest {
    @MockBean
    private AccountUtil accountUtil;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AccountService accountService;

    @Test
    void handleAdminInit() {
        when(accountUtil.getUsername()).thenReturn("admin");

    }

    @Test
    void findOne() {
    }

    @Test
    void findAll() {
    }

    @Test
    void createOne() {
    }

    @Test
    void updateCategoryNameWithRoleAdmin() {
    }

    @Test
    void updateCategoryName() {
/*      String username = "use_category";
        when(accountUtil.getUsername()).thenReturn(username);
        UUID id = UUID.fromString( "5c565447-f161-4d6e-86a7-111579d06453");
        UUID accountId = UUID.fromString("57db0b21-9303-4eec-9fae-b06fa0f82bfd");
        String name = "TEST Updated PERSONAL";
        Category category = categoryService.updateCategoryName(id, accountId, name);

        Assertions.assertNotNull(category);
 */
    }

    @Test
    void updateCategoryStatus() {
    }
}