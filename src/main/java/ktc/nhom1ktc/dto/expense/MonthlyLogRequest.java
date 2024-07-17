package ktc.nhom1ktc.dto.expense;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;
import java.time.Year;
import java.util.Set;
import java.util.UUID;

@Data
public class MonthlyLogRequest {
    private Year year;
    @Range(min = 1, max = 12)
    private Set<Integer> months;

    @Data
    public static class UpdateRequest {
        @NotNull
        private UUID id;
        @PositiveOrZero
        private BigDecimal amount;
    }
}
