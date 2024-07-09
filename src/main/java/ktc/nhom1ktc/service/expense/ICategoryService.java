package ktc.nhom1ktc.service.expense;

import ktc.nhom1ktc.entity.expense.Status;
import ktc.nhom1ktc.entity.expense.management.category.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ICategoryService<T, U> {

    Optional<T> findOne(U id);

    List<T> findAll(U id);

    Optional<T> createOne(String name, U accountId);

    Category updateCategoryNameWithRoleAdmin(UUID id, String name);

    Category updateCategoryName(UUID id, UUID accountId, String name);

    Category updateCategoryStatus(UUID id, UUID accountId, Status status);
}
