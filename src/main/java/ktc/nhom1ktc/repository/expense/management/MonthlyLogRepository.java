package ktc.nhom1ktc.repository.expense.management;

import ktc.nhom1ktc.entity.expense.management.MonthlyLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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



}