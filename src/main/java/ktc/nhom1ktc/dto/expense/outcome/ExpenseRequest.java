package ktc.nhom1ktc.dto.expense.outcome;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class ExpenseRequest {
    private UUID id;
    private UUID categoryId;

    @PositiveOrZero
    private BigDecimal amount;
    private LocalDate date;

}
