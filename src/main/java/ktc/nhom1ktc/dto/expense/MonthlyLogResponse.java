package ktc.nhom1ktc.dto.expense;

import ktc.nhom1ktc.entity.expense.management.MonthlyLog;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

//@Slf4j
@Data
public class MonthlyLogResponse {
    private Year year;
    private Map<Integer, MonthlyLogDetail> monthlyLogDetails;

    public MonthlyLogResponse(Year year, List<MonthlyLog> logs) {
        this.year = year;
        this.monthlyLogDetails = new TreeMap<>();

        MonthlyLogDetail logDetail;
        for (MonthlyLog monthlyLog : logs) {
            logDetail = MonthlyLogDetail.builder()
                    .monthlyIncomeId(monthlyLog.getMonthlyIncome().getId())
                    .incomeSum(monthlyLog.getMonthlyIncome().getIncomeSum())
                    .budget(monthlyLog.getBudget())
                    .expenseSum(monthlyLog.getExpenseSum())
                    .build();
//            log.info("monthlyLog {}", monthlyLog);
            monthlyLogDetails.put(monthlyLog.getMonth().getValue(), logDetail);
        }
    }

    @Builder
    @Data
    public static class MonthlyLogDetail {
        private UUID monthlyIncomeId;
        private BigDecimal incomeSum;
        private BigDecimal budget;
        private BigDecimal expenseSum;
    }
}
