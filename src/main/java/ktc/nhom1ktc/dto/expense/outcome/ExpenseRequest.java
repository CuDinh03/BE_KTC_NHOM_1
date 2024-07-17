package ktc.nhom1ktc.dto.expense.outcome;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import ktc.nhom1ktc.dto.expense.income.IncomeRequest;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Builder
@Data
public class ExpenseRequest {
    private UUID id;
    private UUID categoryId;

    @PositiveOrZero
    private BigDecimal amount;
    private LocalDate date;

    @Data
    public static class UpdateRequest {
        @NotNull
        private UUID id;
        @NotNull
        private UUID categoryId;
        @PositiveOrZero
        private BigDecimal amount;
        private LocalDate date;

        public static ExpenseRequest toExpenseRequest(ExpenseRequest.UpdateRequest request) {
            return new ExpenseRequest(request.getId(), request.getCategoryId(), request.getAmount(), request.getDate());
        }
    }

    @Data
    public static class DeleteRequest {
        @NotNull
        private UUID id;
    }
}
