package ktc.nhom1ktc.service.expense;

import org.springframework.stereotype.Service;

import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Set;

@Service
public interface IMonthlyIncomeService<T> {
    List<T> findAllByYear(Year year);

    List<T> findByYearMonths(Year year, Set<Month> months);
}
