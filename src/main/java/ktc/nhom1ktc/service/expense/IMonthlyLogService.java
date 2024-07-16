package ktc.nhom1ktc.service.expense;

import org.springframework.stereotype.Service;

import java.time.Month;
import java.time.Year;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public interface IMonthlyLogService<T> {

    List<T> saveMonthlyLogs(Collection<T> logs);

    List<T> findAllByYear(Year year);

    List<T> findAllByYearMonths(Year year, Set<Integer> months);

    T saveMonthlyLog(T log);

    T findByYearAndMonthAndCategoryIdAndAccountId(Year year, Month month, UUID categoryId);

    T findByYearAndMonthAndCategoryIdAndUsername(Year year, Month month, UUID id);

}
