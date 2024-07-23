package ktc.nhom1ktc.dto.expense.income;

import ktc.nhom1ktc.entity.expense.management.income.MonthlyIncome;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

//@Builder
@Data
public class MonthlyIncomeResponse {
    private int year;
    private Map<Integer, BigDecimal> monthlyIncomes;

    public MonthlyIncomeResponse(int year, List<MonthlyIncome> monthlyIncomes) {
        this.year = year;
        this.monthlyIncomes = new TreeMap<>();
        monthlyIncomes.forEach(e -> this.monthlyIncomes.put(e.getMonth().getValue(), e.getIncomeSum()));
    }

}
