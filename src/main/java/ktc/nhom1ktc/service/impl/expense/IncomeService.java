package ktc.nhom1ktc.service.impl.expense;

import ktc.nhom1ktc.dto.expense.income.IncomeRequest;
import ktc.nhom1ktc.entity.expense.management.income.Income;
import ktc.nhom1ktc.entity.expense.management.income.MonthlyIncome;
import ktc.nhom1ktc.exception.ErrorCode;
import ktc.nhom1ktc.exception.expense.IncomeDateNotExistedInMonthlyIncomeException;
import ktc.nhom1ktc.exception.expense.IncomeObjectNotFoundForAccountException;
import ktc.nhom1ktc.repository.expense.management.income.IncomeRepository;
import ktc.nhom1ktc.repository.expense.management.income.MonthlyIncomeRepository;
import ktc.nhom1ktc.service.expense.IIncomeService;
import ktc.nhom1ktc.service.impl.AccountUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.time.*;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Primary
@Service
@AllArgsConstructor
public class IncomeService implements IIncomeService<Income> {

    private IncomeRepository incomeRepository;
    private MonthlyIncomeRepository monthlyIncomeRepository;
    private AccountUtil accountUtil;

    @Override
    public Income addSingle(Income income) {
        return incomeRepository.save(income);
    }

    @Override
    public Income addSingle(IncomeRequest request) throws Exception {
        return incomeRepository.save(buildIncome(request.getAmount(), request.getDate()));
    }

    @Override
    public Income addSingle(BigDecimal amount, LocalDate date) throws Exception {
        return incomeRepository.save(buildIncome(amount, date));
    }

    @Override
    public Income update(IncomeRequest request) throws Exception {
        Income lastIncome = incomeRepository.findByIdAndCreatedBy(request.getId(), accountUtil.getUsername());
        log.info("findByIdAndCreatedBy {}", lastIncome);
        if (ObjectUtils.isEmpty(lastIncome)) {
            throw new IncomeObjectNotFoundForAccountException(ErrorCode.INCOME_OBJECT_NOT_FOUND_FOR_ACCOUNT, request.getId());
        }

        Year year = Year.of(request.getDate().getYear());
        Month month = request.getDate().getMonth();
//        MonthlyIncome requestedMI = monthlyIncomeRepository.findByIdAndCreatedByAndYearAndMonth(lastIncome.getMonthlyIncomeId(), accountUtil.getUsername(), year, month);
        MonthlyIncome requestedMI = monthlyIncomeRepository.findByCreatedByAndYearAndMonthIn(accountUtil.getUsername(), year, Collections.singleton(month))
                .stream().findFirst()
                .orElseThrow(() -> new IncomeDateNotExistedInMonthlyIncomeException(ErrorCode.INCOME_DATE_NOT_EXISTED_IN_MONTHLY_INCOME, YearMonth.of(year.getValue(), month)));
        log.info("findByIdAndCreatedByAndYearAndMonth {}", requestedMI);
//        if (ObjectUtils.isEmpty(requestedMI)) {
//            throw new IncomeDateNotExistedInMonthlyIncomeException(ErrorCode.INCOME_DATE_NOT_EXISTED_IN_MONTHLY_INCOME, YearMonth.of(year.getValue(), month));
//        }

        Year lastYearVal = Year.of(lastIncome.getDate().getYear());
        Month lastMonthVal = lastIncome.getDate().getMonth();
        YearMonth lastYearMonth = YearMonth.of(lastYearVal.getValue(), lastMonthVal);

        boolean sameYearMonth = lastYearMonth.equals(YearMonth.of(year.getValue(), month));
        boolean sameAmount = lastIncome.getAmount().equals(request.getAmount());

        MonthlyIncome lastMI = monthlyIncomeRepository.findById(lastIncome.getMonthlyIncomeId()).orElseThrow();
        if (sameYearMonth) {
            log.info("sameYearMonth");
            return sameAmount ? lastIncome : resetAmount(lastIncome, lastMI, request.getAmount());
        }

        lastMI.setIncomeSum(lastMI.getIncomeSum().subtract(lastIncome.getAmount()));
        requestedMI.setIncomeSum(requestedMI.getIncomeSum().add(request.getAmount()));
        monthlyIncomeRepository.saveAll(Set.of(lastMI, requestedMI));

        lastIncome.setAmount(request.getAmount());
        lastIncome.setDate(request.getDate());
        lastIncome.setMonthlyIncomeId(requestedMI.getId());
        return incomeRepository.save(lastIncome);
    }

    private Income resetAmount(Income income, MonthlyIncome monthlyIncome, BigDecimal addedAmount) {
//        MonthlyIncome monthlyIncome = monthlyIncomeRepository.findById(income.getId()).orElseThrow();
        monthlyIncome.setIncomeSum(monthlyIncome.getIncomeSum()
                .subtract(income.getAmount()).add(addedAmount));
        monthlyIncomeRepository.save(monthlyIncome);

        log.info("resetAmount {}", monthlyIncome);

        income.setAmount(addedAmount);
        incomeRepository.setAmount(income.getId(), addedAmount);
        return income;
    }

    @Override
    public Income update(Income income) {
        return null;
    }

    @Override
    public UUID deleteById(UUID id) throws IncomeObjectNotFoundForAccountException {
        Income lastIncome = incomeRepository.findByIdAndCreatedBy(id, accountUtil.getUsername());
        log.info("deleteById findByIdAndCreatedBy {}", lastIncome);
        if (ObjectUtils.isEmpty(lastIncome) || 0 == incomeRepository.deleteByIdAndCreatedBy(id, accountUtil.getUsername())) {
            throw new IncomeObjectNotFoundForAccountException(ErrorCode.INCOME_OBJECT_NOT_FOUND_FOR_ACCOUNT, id);
        }

        MonthlyIncome lastMI = monthlyIncomeRepository.findById(lastIncome.getMonthlyIncomeId())
                .orElseThrow(() -> new RuntimeException("MonthlyIncome not found"));
        log.info("deleteById findById {}", lastMI);
        lastMI.setIncomeSum(lastMI.getIncomeSum().subtract(lastIncome.getAmount()));
        monthlyIncomeRepository.save(lastMI);

        return id;
    }

    private Income buildIncome(BigDecimal amount, LocalDate date) throws Exception {
        final String username = accountUtil.getUsername();
        final List<MonthlyIncome> monthlyIncomes = monthlyIncomeRepository.findByCreatedByAndYearAndMonthIn(username,
                Year.of(date.getYear()), Collections.singleton(date.getMonth()));

        if (ObjectUtils.isEmpty(monthlyIncomes)) {
            throw new IncomeDateNotExistedInMonthlyIncomeException(
                    ErrorCode.INCOME_DATE_NOT_EXISTED_IN_MONTHLY_INCOME, YearMonth.of(date.getYear(), date.getMonth()));
        }

        final AccountUtil.AccountDetails accountDetails = accountUtil.loadUserByUsername(username);
        final LocalDateTime dateTime = LocalDateTime.now();

        return Income.builder()
                .accountId(accountDetails.getId())
                .monthlyIncomeId(monthlyIncomes.get(0).getId())
                .date(date)
                .amount(amount)
                .createdAt(dateTime)
                .updatedAt(dateTime)
                .createdBy(username)
                .updatedBy(username)
//                .status(Status.ACTIVATED)
                .build();
    }
}
