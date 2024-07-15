package ktc.nhom1ktc.dto.expense.income;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class IncomeRequest {
    private UUID id;
    @PositiveOrZero
    private BigDecimal amount;

//    @JsonSerialize(using = LocalDateSerializer.class)
//    @JsonDeserialize(using = LocalDateDeserializer.class)
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    public IncomeRequest(UpdateRequest request) {
        this.id = request.getId();
        this.amount = request.getAmount();
        this.date = request.getDate();
    }

    @Data
    public static class UpdateRequest {
        @NotNull
        private UUID id;
        @PositiveOrZero
        private BigDecimal amount;
        private LocalDate date;
    }

    @Data
    public static class DeleteRequest {
        @NotNull
        private UUID id;
    }
}
