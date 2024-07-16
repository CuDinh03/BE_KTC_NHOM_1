package ktc.nhom1ktc.service.impl.expense;

import ktc.nhom1ktc.entity.expense.management.MonthlyLog;
import ktc.nhom1ktc.repository.expense.management.MonthlyLogRepository;
import ktc.nhom1ktc.service.expense.IMonthlyLogService;
import ktc.nhom1ktc.service.impl.AccountUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Month;
import java.time.Year;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Primary
@Service
@AllArgsConstructor
public class MonthlyLogService implements IMonthlyLogService<MonthlyLog> {

    private MonthlyLogRepository monthlyLogRepository;
    private AccountUtil accountUtil;

    @Override
    public List<MonthlyLog> saveMonthlyLogs(Collection<MonthlyLog> logs) {
        return monthlyLogRepository.saveAll(logs);
    }

//    @Transactional
    @Override
    public List<MonthlyLog> findAllByYear(Year year) {
        return monthlyLogRepository.findAllByYearAndCreatedBy(year, accountUtil.getUsername());
    }

//    @Transactional
    @Override
    public List<MonthlyLog> findAllByYearMonths(Year year, Set<Integer> months) {
        Set<Month> monthSet = months.stream().map(Month::of).collect(Collectors.toSet());
        return monthlyLogRepository.findAllByYearAndCreatedByAndMonthIn(year, accountUtil.getUsername(), monthSet);
    }

    @Transactional
    @Override
    public MonthlyLog saveMonthlyLog(MonthlyLog monthlyLog) {
        return monthlyLogRepository.save(monthlyLog);
    }

    @Override
    public MonthlyLog findByYearAndMonthAndCategoryIdAndAccountId(Year year, Month month, UUID categoryId) {
        AccountUtil.AccountDetails accountDetails = accountUtil.loadUserByUsername(accountUtil.getUsername());
        return monthlyLogRepository.findByYearAndMonthAndCategoryIdAndAccountId(year, month, categoryId, accountDetails.getId());
    }

    @Override
    public MonthlyLog findByYearAndMonthAndCategoryIdAndUsername(Year year, Month month, UUID id) {
        return monthlyLogRepository.findByYearAndMonthAndCategoryIdAndCreatedBy(year, month, id, accountUtil.getUsername());
    }

}
