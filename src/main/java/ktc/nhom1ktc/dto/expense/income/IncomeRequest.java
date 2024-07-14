package ktc.nhom1ktc.dto.expense.income;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class IncomeRequest {
    UUID id;
    BigDecimal amount;
    LocalDate date;
}
