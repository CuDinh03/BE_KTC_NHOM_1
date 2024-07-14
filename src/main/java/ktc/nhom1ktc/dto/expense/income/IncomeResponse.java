package ktc.nhom1ktc.dto.expense.income;

import ktc.nhom1ktc.entity.expense.management.income.Income;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Builder
@Data
public class IncomeResponse {
    UUID id;
    BigDecimal amount;
    LocalDate date;

    public IncomeResponse(Income income) {
        this.id = income.getId();
        this.amount = income.getAmount();
        this.date = income.getDate();
    }
}