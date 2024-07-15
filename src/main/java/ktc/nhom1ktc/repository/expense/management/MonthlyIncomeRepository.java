package ktc.nhom1ktc.repository.expense.management;

import ktc.nhom1ktc.entity.expense.management.income.MonthlyIncome;
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
public interface MonthlyIncomeRepository extends JpaRepository<MonthlyIncome, UUID> {

    boolean existsByAccountIdAndYear(UUID accountId, Year year);

//    MonthlyIncome findById(UUID id);

    List<MonthlyIncome> findAllByAccountIdAndYear(UUID accountId, Year year);

    List<MonthlyIncome> findAllByCreatedByAndYear(String name, Year year);

    MonthlyIncome findByAccountIdAndYearAndMonth(UUID accountId, Year year, Month month);

    List<MonthlyIncome> findByCreatedByAndYearAndMonthIn(String name, Year year, Set<Month> months);

    @Modifying
    @Query(nativeQuery = true,
            value = "UPDATE monthly_income mi" +
                    " SET income_sum = :incomeSum" +
                    " WHERE created_by = :createdBy AND year = :year AND month = :month")
    boolean setIncomeSum(@Param("incomeSum") BigDecimal incomeSum,
                         @Param("createdBy") String createdBy,
                         @Param("year") Year year, @Param("month") Month month);

    MonthlyIncome findByIdAndCreatedByAndYearAndMonth(UUID id, String name, Year year, Month month);
}