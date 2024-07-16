package ktc.nhom1ktc.dto.expense.outcome;

import ktc.nhom1ktc.entity.expense.management.outcome.Expense;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

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
}
