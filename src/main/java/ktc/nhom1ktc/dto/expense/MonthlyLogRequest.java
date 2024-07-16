package ktc.nhom1ktc.dto.expense;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.time.Year;
import java.util.Set;

@Data
public class MonthlyLogRequest {
    private Year year;
    @Range(min = 1, max = 12)
    private Set<Integer> months;
}
