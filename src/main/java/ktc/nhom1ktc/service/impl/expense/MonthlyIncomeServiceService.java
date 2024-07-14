package ktc.nhom1ktc.service.impl.expense;


import ktc.nhom1ktc.entity.expense.management.income.MonthlyIncome;
import ktc.nhom1ktc.event.UserLoginEvent;
import ktc.nhom1ktc.repository.expense.management.MonthlyIncomeRepository;
import ktc.nhom1ktc.service.expense.IMonthlyIncomeService;
import ktc.nhom1ktc.service.impl.AccountUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@Primary
public class MonthlyIncomeServiceService implements IMonthlyIncomeService<MonthlyIncome, UUID> {

    @Autowired
    private AccountUtil accountUtil;

    @Autowired
    private MonthlyIncomeRepository monthlyIncomeRepository;

    public List<MonthlyIncome> saveAll(Set<MonthlyIncome> mISet) {
        return monthlyIncomeRepository.saveAll(mISet);
    }

    public List<MonthlyIncome> findAllByYear(Year year) {
        return monthlyIncomeRepository.findAllByCreatedByAndYear(accountUtil.getUsername(), year);
    }

    public List<MonthlyIncome> findByYearMonths(Year year, Set<Integer> months) {
        Set<Month> monthSet = months.stream().map(Month::of).collect(Collectors.toSet());
        return monthlyIncomeRepository.findByCreatedByAndYearAndMonthIn(accountUtil.getUsername(), year, monthSet);
    }

    public boolean updateIncomeSum(Year year, Month month, BigDecimal sum) {
        return monthlyIncomeRepository.setIncomeSum(sum, accountUtil.getUsername(), year, month);
    }

    @EventListener
    public boolean initDefaultByYear(UserLoginEvent event) {
        log.info("initDefaultByYear UserLoginEvent {}", event);
        AccountUtil.AccountDetails accountDetails = accountUtil.loadUserByUsername((String) event.getSource());
        UUID accountId = accountDetails.getId();
        log.info("initDefaultByYear accountId {}", accountId);
        boolean existed = monthlyIncomeRepository.existsByAccountIdAndYear(accountId, Year.now());
        if (existed) {
            return true;
        }

        List<MonthlyIncome> monthlyIncomes = initDefaultMonthlyIncomes(accountId, accountDetails.getUsername());
        monthlyIncomeRepository.saveAll(monthlyIncomes);

        return true;
    }

    private List<MonthlyIncome> initDefaultMonthlyIncomes(UUID id, String name) {
        LocalDateTime dateTime = LocalDateTime.now();
        Year year = Year.now();
        List<MonthlyIncome> monthlyIncomes = new ArrayList<>();

        for (Month month : Month.values()) {
            monthlyIncomes.add(
                    MonthlyIncome.builder()
                            .accountId(id)
                            .year(year)
                            .month(month)
                            .createdAt(dateTime)
                            .updatedAt(dateTime)
                            .createdBy(name)
                            .updatedBy(name)
                            .incomeSum(BigDecimal.ZERO)
                            .build()
            );
        }

        return monthlyIncomes;
    }
}