package ktc.nhom1ktc.repository.expense.management;

import ktc.nhom1ktc.entity.expense.management.MonthlyLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface MonthlyLogRepository extends JpaRepository<MonthlyLog, UUID> {


    List<MonthlyLog> findAllByYearAndCreatedBy(Year year, String name);

    List<MonthlyLog> findAllByYearAndCreatedByAndMonthIn(Year year, String name, Set<Month> months);

//    List<MonthlyLog> findAllByYearAndMonthInAndCreatedBy(Year year, Set<Integer> months, String name);

    MonthlyLog findByYearAndMonthAndCategoryIdAndAccountId(Year year, Month month, UUID categoryId, UUID accountId);

    MonthlyLog findByYearAndMonthAndCategoryIdAndCreatedBy(Year year, Month month, UUID id, String name);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "UPDATE MonthlyLog ml SET ml.budget = :budget WHERE ml.id = :id AND ml.createdBy = :createdBy")
    MonthlyLog setBudget(@Param("id") UUID id,
                         @Param("createdBy") String name,
                         @Param("budget") BigDecimal budget);

}