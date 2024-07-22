package ktc.nhom1ktc.dto.expense.income;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.time.Year;
import java.util.Set;

@Data
public class MonthlyIncomeRequest {
    Year year;
    @Range(min = 1, max = 12)
    Set<Integer> months;
}
