package ktc.nhom1ktc.dto.expense.income;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncomeRequest {
    private UUID id;
    @PositiveOrZero
    private BigDecimal amount;

//    @JsonSerialize(using = LocalDateSerializer.class)
//    @JsonDeserialize(using = LocalDateDeserializer.class)
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

//    public IncomeRequest(UpdateRequest request) {
//        this.id = request.getId();
//        this.amount = request.getAmount();
//        this.date = request.getDate();
//    }

    @Data
    public static class UpdateRequest {
        @NotNull
        private UUID id;
        @PositiveOrZero
        private BigDecimal amount;
        private LocalDate date;

        public static IncomeRequest toIncomeRequest(IncomeRequest.UpdateRequest request) {
            return new IncomeRequest(request.getId(), request.getAmount(), request.getDate());
        }
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeleteRequest {
        @NotNull
        private UUID id;
    }
}
