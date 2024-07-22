package ktc.nhom1ktc.service.impl.expense;

//import org.junit.jupiter.api.Assertions;

import ktc.nhom1ktc.entity.expense.management.income.MonthlyIncome;
import ktc.nhom1ktc.service.impl.AccountUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

//@ExtendWith(SpringExtension.class)
class MonthlyIncomeServiceTest {
    String username = "user_test";
    Year year = Year.now();

    @Test
    void test1() {

        LocalDateTime dateTime = LocalDateTime.now();
        Assertions.assertEquals(Year.of(dateTime.getYear()), year);

        List<MonthlyIncome> monthlyIncomes = buildMonthlyIncomes(UUID.randomUUID());
        System.out.println(monthlyIncomes);
    }

    private List<MonthlyIncome> buildMonthlyIncomes(UUID id) {
//        Mockito.when(accountUtil.getUsername()).thenReturn(username);
        LocalDateTime dateTime = LocalDateTime.now();
        Year year = Year.now();
//        String username = accountUtil.getUsername();
        List<MonthlyIncome> monthlyIncomes = new ArrayList<>();

        for (Month month : Month.values()) {
            monthlyIncomes.add(
                    MonthlyIncome.builder()
                            .accountId(id)
                            .year(year)
                            .month(month)
                            .createdAt(dateTime)
                            .updatedAt(dateTime)
                            .createdBy(username)
                            .updatedBy(username)
                            .incomeSum(BigDecimal.ZERO)
                            .build()
            );
        }

        return monthlyIncomes;
    }
}