package ktc.nhom1ktc.repository.expense.management;

import ktc.nhom1ktc.entity.expense.management.income.MonthlyIncome;
import ktc.nhom1ktc.repository.RepositoryTests;
import ktc.nhom1ktc.repository.expense.management.income.MonthlyIncomeRepository;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.*;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class MonthlyIncomeRepositoryTest extends RepositoryTests {

    @Autowired
    private MonthlyIncomeRepository monthlyIncomeRepository;

    @Test
    public void simple() {
        Year year = Year.now();
        Month month = Month.JULY;
        Temporal temporal = YearMonth.of(year.getValue(), month.getValue());
        LocalDate firstDayOfMonth = LocalDate.of(year.getValue(), month.getValue(), 1);
        LocalDate firstDay = YearMonth.of(year.getValue(), month.getValue()).atDay(1);
        LocalDate lastDay = YearMonth.of(year.getValue(), month.getValue()).atEndOfMonth();
//        System.out.println(firstDay);
//        System.out.println(firstDay.atStartOfDay());
//        System.out.println(lastDay);
    }

    @Test
    public void testExistsByAccountIdAndYear_NoMonthlyIncome() {
        assertFalse(monthlyIncomeRepository.existsByAccountIdAndYear(userAcc.getId(), Year.now()));
    }

    @Test
    public void testExistsByAccountIdAndYear_WithMonthlyIncome() {
        when(accountUtil.getUsername()).thenReturn(userAcc.getUsername());
        List<MonthlyIncome> monthlyIncomes = initDefaultMonthlyIncomes(userAcc.getId());
        monthlyIncomeRepository.saveAll(monthlyIncomes);

        assertTrue(monthlyIncomeRepository.existsByAccountIdAndYear(userAcc.getId(), Year.now()));
    }

    @Test
    public void testFindAllByCreatedByAndYear() {
        when(accountUtil.getUsername()).thenReturn(userAcc.getUsername());
        List<MonthlyIncome> monthlyIncomes = initDefaultMonthlyIncomes(userAcc.getId());
        List<MonthlyIncome> expectedResult = monthlyIncomeRepository.saveAll(monthlyIncomes);

        List<MonthlyIncome> actualResult = monthlyIncomeRepository.findAllByCreatedByAndYear(userAcc.getUsername(), Year.now());
        Collections.sort(actualResult, Comparator.comparingInt(x -> x.getMonth().getValue()));
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testFindByCreatedByAndYearAndMonthIn() {
        when(accountUtil.getUsername()).thenReturn(userAcc.getUsername());
        List<MonthlyIncome> monthlyIncomes = initDefaultMonthlyIncomes(userAcc.getId());
        List<MonthlyIncome> expectedResult = monthlyIncomeRepository.saveAll(monthlyIncomes);

        List<MonthlyIncome> actualResult = monthlyIncomeRepository.findByCreatedByAndYearAndMonthIn(userAcc.getUsername(), Year.now(), Set.of(Month.JULY));
        assertTrue(expectedResult.containsAll(expectedResult));
    }

    private List<MonthlyIncome> initDefaultMonthlyIncomes(UUID id) {
        LocalDateTime dateTime = LocalDateTime.now();
        Year year = Year.now();
        String username = accountUtil.getUsername();
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