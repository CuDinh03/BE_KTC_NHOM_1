package ktc.nhom1ktc.dto.expense.income;

import ktc.nhom1ktc.entity.expense.management.income.Income;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IncomeResponse {
    UUID id;
    BigDecimal amount;
    LocalDate date;

    public IncomeResponse(Income income) {
        this.id = income.getId();
        this.amount = income.getAmount();
        this.date = income.getDate();
    }

    public IncomeResponse(UUID id) {
        this.id = id;
    }

    @Data
    @NoArgsConstructor
    public static class IncomeListResponse {
        private Map<Integer, List<IncomeResponse>> incomes;

        public IncomeListResponse(List<Income> incomeList) {
            this.incomes = new TreeMap<>();
            int monthVal;
            List<IncomeResponse> miList;
            IncomeResponse miResponse;
            for (Income income : incomeList) {
                miResponse = new IncomeResponse(income);
                monthVal = income.getDate().getMonthValue();

                if (incomes.containsKey(monthVal)) {
                    miList = incomes.get(monthVal);
                    miList.add(miResponse);
                    continue;
                }
                miList = new ArrayList<>();
                miList.add(miResponse);
                incomes.put(monthVal, miList);
            }
        }
    }

    @Data
    @AllArgsConstructor
    public static class DeleteResponse {
        private UUID id;
        private boolean deleted;
    }
}
