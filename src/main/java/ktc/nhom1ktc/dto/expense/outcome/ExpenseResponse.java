package ktc.nhom1ktc.dto.expense.outcome;

import ktc.nhom1ktc.dto.expense.income.IncomeResponse;
import ktc.nhom1ktc.entity.expense.management.income.Income;
import ktc.nhom1ktc.entity.expense.management.outcome.Expense;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExpenseResponse {
    private UUID id;
    private UUID categoryId;
    private UUID monthlyLogId;

    private BigDecimal amount;
    private LocalDate date;

    public ExpenseResponse(Expense expense) {
        this.id = expense.getId();
        this.categoryId = expense.getCategoryId();
        this.monthlyLogId = expense.getMonthlyLogId();
        this.amount = expense.getAmount();
        this.date = expense.getDate();
    }

    public static class ExpenseListResponse {
        private Map<Integer, List<ExpenseResponse>> expenses;

        public ExpenseListResponse(List<Expense> expenseList) {
            this.expenses = new TreeMap<>();
            int monthVal;
            List<ExpenseResponse> exList;
            ExpenseResponse exResponse;
            for (Expense expense : expenseList) {
                exResponse = new ExpenseResponse(expense);
                monthVal = expense.getDate().getMonthValue();

                if (expenses.containsKey(monthVal)) {
                    exList = expenses.get(monthVal);
                    exList.add(exResponse);
                    continue;
                }
                exList = new ArrayList<>();
                exList.add(exResponse);
                expenses.put(monthVal, exList);
            }
        }
    }

    @Data
    @AllArgsConstructor
    public static class DeleteResponse {
        private UUID id;
        private boolean deleted;
    }
}
