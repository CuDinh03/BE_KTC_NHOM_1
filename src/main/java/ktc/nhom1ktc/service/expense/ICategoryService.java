package ktc.nhom1ktc.service.expense;

import ktc.nhom1ktc.entity.expense.Status;
import ktc.nhom1ktc.entity.expense.management.category.Category;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ICategoryService<T, U> {

    Optional<T> findOneByCategoryId(U id);

    List<T> findAllByAccountId(U id);

    List<T> findAllByUsername(String username);

    @Transactional
    Optional<T> createOne(String name, U accountId) throws Exception;

    @Transactional
    Category updateCategoryNameWithRoleAdmin(UUID id, UUID accountId,String name) throws Exception;

    @Transactional
    Category updateCategoryName(UUID id, UUID accountId, String name) throws Exception;

    @Transactional
    Category updateCategoryStatus(UUID id, UUID accountId, Status status);
}
