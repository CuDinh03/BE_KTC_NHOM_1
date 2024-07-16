package ktc.nhom1ktc.service.impl.expense;

import ktc.nhom1ktc.dto.expense.outcome.ExpenseRequest;
import ktc.nhom1ktc.entity.expense.management.MonthlyLog;
import ktc.nhom1ktc.entity.expense.management.category.Category;
import ktc.nhom1ktc.entity.expense.management.category.CategoryType;
import ktc.nhom1ktc.entity.expense.management.income.MonthlyIncome;
import ktc.nhom1ktc.entity.expense.management.outcome.Expense;
import ktc.nhom1ktc.repository.expense.management.outcome.ExpenseRepository;
import ktc.nhom1ktc.service.expense.IExpenseService;
import ktc.nhom1ktc.service.impl.AccountUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.Collections;
import java.util.UUID;

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

    @Transactional
    @Override
    public Expense addSingle(ExpenseRequest request) {
        Year year = Year.of(request.getDate().getYear());
        if (!Year.now().equals(year)) {
            throw new RuntimeException("Year of date has to be " + Year.now());
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
            throw new RuntimeException("Category not found by account");
        }

        AccountUtil.AccountDetails accountDetails = accountUtil.loadUserByUsername(accountUtil.getUsername());
        MonthlyLog monthlyLog = monthlyLogService.findByYearAndMonthAndCategoryIdAndUsername(year, month, catId);

        log.info("expense addSingle: monthlyLog {}", monthlyLog);

        LocalDateTime dateTime = LocalDateTime.now();
        if (ObjectUtils.isEmpty(monthlyLog)) {
            MonthlyIncome mIncome = monthlyIncomeService.findByYearMonths(year, Collections.singleton(month.getValue())).get(0);
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

        monthlyLogService.saveMonthlyLog(monthlyLog);
        log.info("expense addSingle: saveMonthlyLog {}", monthlyLog);
        return expenseRepository.save(expense);
    }
}
