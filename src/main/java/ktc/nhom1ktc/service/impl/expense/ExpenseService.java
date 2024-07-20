package ktc.nhom1ktc.service.impl.expense;

import ktc.nhom1ktc.dto.expense.outcome.ExpenseRequest;
import ktc.nhom1ktc.entity.expense.management.MonthlyLog;
import ktc.nhom1ktc.entity.expense.management.category.Category;
import ktc.nhom1ktc.entity.expense.management.category.CategoryType;
import ktc.nhom1ktc.entity.expense.management.income.MonthlyIncome;
import ktc.nhom1ktc.entity.expense.management.outcome.Expense;
import ktc.nhom1ktc.exception.expense.category.CategoryNotCreatedByAccountException;
import ktc.nhom1ktc.exception.expense.outcome.ExpenseNotCreatedByAccountException;
import ktc.nhom1ktc.exception.expense.outcome.ExpenseYearException;
import ktc.nhom1ktc.repository.expense.management.outcome.ExpenseRepository;
import ktc.nhom1ktc.service.expense.IExpenseService;
import ktc.nhom1ktc.service.impl.AccountUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.*;

@Slf4j
@Primary
@Service
@AllArgsConstructor
public class ExpenseService implements IExpenseService<Expense> {
    private final AccountUtil accountUtil;
    private ExpenseRepository expenseRepository;
    private CategoryService categoryService;
    private MonthlyLogService monthlyLogService;
    private MonthlyIncomeService monthlyIncomeService;

    @Override
    public Expense addSingle(Expense expense) {
        return expenseRepository.save(expense);
    }

//    @Transactional
    @Override
    public Expense addSingle(ExpenseRequest request) throws Exception {
        Year year = Year.of(request.getDate().getYear());
        if (!Year.now().equals(year)) {
            throw new ExpenseYearException();
//            throw new RuntimeException("Year of date has to be " + Year.now());
        }
        Month month = request.getDate().getMonth();
        UUID catId = request.getCategoryId();
        // -> check Category existed by IdAndCreatedBy
        // if not-existed {
        //      throw
        // }

        // -> find MonthlyLog (year, month)
        // if not-existed {
        //      query MonthlyIncome by (year, month)
        //      create new MonthlyLog (year, month)
        // }
        //

        //
        // monthlyLog setExpenseSum (mLog.getExpenseSum().add request.getAmount())
        // monthlyLogService.save(monthlyLog)
        //
        // build Expense without Id to save by expenseService

        Category category = categoryService.findOneByCategoryId(catId).orElseThrow(() -> new RuntimeException("Category not found by account"));
        log.info("expense addSingle findOneByCategoryId {}", catId);
        if (!category.getUpdatedBy().equals(accountUtil.getUsername()) && category.getType() != CategoryType.COMMON) {
            log.info("expense addSingle: category {} does not belong to account {}", catId, accountUtil.getUsername());
            throw new CategoryNotCreatedByAccountException();
//            throw new RuntimeException("Category not found by account");
        }

        AccountUtil.AccountDetails accountDetails = accountUtil.loadUserByUsername(accountUtil.getUsername());
        MonthlyLog monthlyLog = monthlyLogService.findByYearAndMonthAndCategoryIdAndUsername(year, month, catId);

        log.info("expense addSingle: monthlyLog {}", monthlyLog);

        LocalDateTime dateTime = LocalDateTime.now();
        if (ObjectUtils.isEmpty(monthlyLog)) {
            MonthlyIncome mIncome = monthlyIncomeService.findByYearMonths(year, Collections.singleton(month)).get(0);
            log.info("expense addSingle: monthlyIncome {}", mIncome);
            monthlyLog = MonthlyLog.builder()
                    .year(year)
                    .month(month)
                    .categoryId(catId)
                    .accountId(accountDetails.getId())
                    .createdAt(dateTime)
                    .updatedAt(dateTime)
                    .createdBy(accountDetails.getUsername())
                    .updatedBy(accountDetails.getUsername())
                    .monthlyIncome(mIncome)
                    .budget(BigDecimal.ZERO)
                    .expenseSum(BigDecimal.ZERO)
                    .build();
        }

        monthlyLog.setExpenseSum(monthlyLog.getExpenseSum().add(request.getAmount()));
        monthlyLog = monthlyLogService.saveMonthlyLog(monthlyLog);
        log.info("expense addSingle: saveMonthlyLog {}", monthlyLog);

        Expense expense = Expense.builder()
                .year(year)
                .month(month)
                .categoryId(catId)
                .accountId(accountDetails.getId())
                .monthlyLogId(monthlyLog.getId())
                .amount(request.getAmount())
                .date(request.getDate())
                .createdAt(dateTime)
                .updatedAt(dateTime)
                .createdBy(accountDetails.getUsername())
                .updatedBy(accountDetails.getUsername())
                .build();


        return expenseRepository.save(expense);
    }

    @Override
    public int deleteById(UUID id) throws Exception {
        Expense expense = expenseRepository.findByIdAndCreatedBy(id, accountUtil.getUsername());
        log.info("deleteById expense {}", expense);
        if (ObjectUtils.isEmpty(expense)) {
            throw new ExpenseNotCreatedByAccountException();
//            throw new RuntimeException("Expense not found by account");
        }

        MonthlyLog monthlyLog = monthlyLogService.findById(expense.getMonthlyLogId());
        log.info("deleteById monthlyLog {}", monthlyLog);
        monthlyLog.setExpenseSum(monthlyLog.getExpenseSum().subtract(expense.getAmount()));
        monthlyLog = monthlyLogService.saveMonthlyLog(monthlyLog);
        log.info("deleteById monthlyLog {}", monthlyLog);
        return expenseRepository.deleteByIdAndCreatedBy(id, accountUtil.getUsername());
    }

    @Override
    public List<Expense> findByYear(int year) {
        LocalDate start = Year.of(year).atDay(1);
        LocalDate end = Year.of(year).plusYears(1).atDay(1).minusDays(1);
        return expenseRepository.findAllByDateBetweenAndCreatedBy(start, end, accountUtil.getUsername());
    }

    @Override
    public Expense findById(UUID id) {
        return expenseRepository.findById(id).orElseThrow();
    }

    @Override
    public Expense update(ExpenseRequest expenseRequest) throws Exception {
        Expense lastExpense = expenseRepository.findByIdAndCreatedBy(expenseRequest.getId(), accountUtil.getUsername());
        if (ObjectUtils.isEmpty(lastExpense)) {
            throw new ExpenseNotCreatedByAccountException();
//            throw new RuntimeException("Expense not found by account");
        }
        int lastYear = lastExpense.getYear().getValue();
        Month lastMonth = lastExpense.getMonth();

        LocalDate newDate = expenseRequest.getDate();
        int newYear = newDate.getYear();
        Month newMonth = newDate.getMonth();

        boolean sameCatId = lastExpense.getCategoryId() == expenseRequest.getCategoryId();
        boolean sameYearMonth = lastYear == newYear && lastMonth == newMonth;
        boolean sameAmount = Objects.equals(lastExpense.getAmount(), expenseRequest.getAmount());

        MonthlyLog monthlyLog = monthlyLogService.findById(lastExpense.getMonthlyLogId());
        log.info("expense update - last monthlyLog {}", monthlyLog);
        if (sameCatId && sameYearMonth) {

            if (!sameAmount) {
                monthlyLog.setExpenseSum(monthlyLog.getExpenseSum()
                        .subtract(lastExpense.getAmount())
                        .add(expenseRequest.getAmount()));
                lastExpense.setAmount(expenseRequest.getAmount());

                monthlyLog = monthlyLogService.saveMonthlyLog(monthlyLog);
                log.info("expense update - !sameAmount last monthlyLog {}", monthlyLog);
                lastExpense = expenseRepository.save(lastExpense);
                log.info("expense update - !sameAmount lastExpense {}", lastExpense);
            }
            return lastExpense;
        }

        MonthlyLog newMonthlyLog = monthlyLogService.findByYearAndMonthAndCategoryIdAndUsername(Year.of(newYear), newMonth, lastExpense.getCategoryId());
        log.info("expense update - newMonthlyLog {}", newMonthlyLog);
        List<MonthlyIncome> monthlyIncomeList = monthlyIncomeService.findByYearMonths(Year.of(newYear), Set.of(newMonth));
        if (CollectionUtils.isEmpty(monthlyIncomeList)) {
            monthlyIncomeList = monthlyIncomeService.initDefaultByYear(Year.of(newYear));
            log.info("expense update - monthlyIncomeList initDefaultByYear {}", monthlyIncomeList);
        }

        Map<Month, MonthlyIncome> miMap = new HashMap<>();
        monthlyIncomeList.forEach(mi -> miMap.put(mi.getMonth(), mi));
        MonthlyIncome newMonthlyIncome = miMap.get(newMonth);
        log.info("expense update - newMonthlyIncome {}", newMonthlyIncome);
        LocalDateTime dateTime = LocalDateTime.now();

        if (ObjectUtils.isEmpty(newMonthlyLog)) {
            newMonthlyLog = MonthlyLog.builder()
                    .year(Year.of(newYear))
                    .month(newMonth)
                    .accountId(newMonthlyIncome.getAccountId())
                    .categoryId(expenseRequest.getCategoryId())
                    .monthlyIncome(newMonthlyIncome)
                    .createdBy(accountUtil.getUsername())
                    .updatedBy(accountUtil.getUsername())
                    .createdAt(dateTime)
                    .updatedAt(dateTime)
                    .expenseSum(BigDecimal.ZERO)
                    .budget(BigDecimal.ZERO)
                    .build();
            log.info("expense update - newMonthlyLog {}", newMonthlyLog);
            newMonthlyLog = monthlyLogService.saveMonthlyLog(newMonthlyLog);
            log.info("expense update - newMonthlyLog {}", newMonthlyLog);
        }

        monthlyLog.setExpenseSum(monthlyLog.getExpenseSum().subtract(lastExpense.getAmount()));
        monthlyLog.setUpdatedAt(dateTime);
        monthlyLog = monthlyLogService.saveMonthlyLog(monthlyLog);
        log.info("expense update - last monthlyLog subtracted {}", monthlyLog);
        newMonthlyLog.setExpenseSum(newMonthlyLog.getExpenseSum().add(expenseRequest.getAmount()));
        newMonthlyLog.setUpdatedAt(dateTime);
        newMonthlyLog = monthlyLogService.saveMonthlyLog(newMonthlyLog);
        log.info("expense update - new monthlyLog added {}", newMonthlyLog);
//        List<MonthlyLog> monthlyLogs = monthlyLogService.saveMonthlyLogs(Set.of(monthlyLog, newMonthlyLog));
//        log.info("expense update - saved last and new MonthlyLogs {}", monthlyLogs);

        lastExpense.setCategoryId(expenseRequest.getCategoryId());
        lastExpense.setYear(Year.of(newYear));
        lastExpense.setMonth(newMonth);
        lastExpense.setDate(newDate);
        lastExpense.setAmount(expenseRequest.getAmount());
        lastExpense.setMonthlyLogId(newMonthlyLog.getId());
        lastExpense.setUpdatedAt(dateTime);

        return expenseRepository.save(lastExpense);
    }
}
