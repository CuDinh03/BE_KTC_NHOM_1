package ktc.nhom1ktc.repository.expense.management.category;

import ktc.nhom1ktc.entity.expense.Status;
import ktc.nhom1ktc.entity.expense.management.category.Category;
import ktc.nhom1ktc.entity.expense.management.category.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID>, JpaSpecificationExecutor<Category> {

    List<Category> findByAccountId(UUID accountId);

    List<Category> findByTypeOrAccountId(CategoryType categoryType, UUID accountId);

    Set<Category> findAllByTypeAndUpdatedBy(CategoryType categoryType, String nane);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(nativeQuery = true,
            value = "SELECT *" +
                    " FROM category c" +
                    " WHERE c.created_by = :username OR c.updated_by = :username OR c.type = :#{#type.name()}")
    List<Category> findByCreatedByOrUpdatedByOrType(@Param("username") String username, @Param("type") CategoryType type);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "UPDATE Category c" +
            " SET c.name = :name, c.updatedAt = :updated_at, c.updatedBy = :updated_by" +
            " WHERE c.id = :id AND c.type = ktc.nhom1ktc.entity.expense.management.category.CategoryType.COMMON")
    int setCategoryNameByIdWithRoleIsAdmin(@Param("id") UUID id,
                                           @Param("name") String name,
                                           @Param("updated_at") LocalDateTime updatedAt,
                                           @Param("updated_by") String updatedBy);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "UPDATE category c" +
                    " SET c.name = :name, c.updated_at = :updated_at, c.updated_by = :updated_by" +
                    " WHERE c.id = :id AND (c.account_id = :account_id OR c.updated_by = :updated_by)",
            nativeQuery = true)
    int setCategoryNameById(@Param("id") UUID id,
                                   @Param("account_id") UUID accountId,
                                   @Param("name") String name,
                                   @Param("updated_at") LocalDateTime updatedAt,
                                   @Param("updated_by") String updatedBy);

//    @Modifying(clearAutomatically = true, flushAutomatically = true)
//    @Query(value = "UPDATE category c" +
//            " SET c.status = :#{#status?.name()}, c.updated_at = :updated_at, c.updated_by = :updated_by" +
//            " WHERE c.id = :id AND c.account_id = :account_id",
//            nativeQuery = true)
//    int updateCategoryStatusById(@Param("id") UUID id,
//                                     @Param("account_id") UUID accountId,
//                                     @Param("status") Status status,
//                                     @Param("updated_at") LocalDateTime updatedAt,
//                                     @Param("updated_by") String updatedBy);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "UPDATE category c" +
            " SET c.status = :#{#status.name()}, c.updated_at = :updated_at, c.updated_by = :updated_by" +
            " WHERE c.id = :id AND (c.account_id = :account_id OR c.updated_by = :updated_by)",
            nativeQuery = true)
    int setCategoryStatusById(@Param("id") UUID id,
                                 @Param("account_id") UUID accountId,
                                 @Param("status") Status status,
                                 @Param("updated_at") LocalDateTime updatedAt,
                                 @Param("updated_by") String updatedBy);

    boolean existsByType(CategoryType categoryType);
}