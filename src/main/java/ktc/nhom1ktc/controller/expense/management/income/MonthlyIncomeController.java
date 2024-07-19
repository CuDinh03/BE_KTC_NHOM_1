package ktc.nhom1ktc.controller.expense.management.income;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import ktc.nhom1ktc.dto.expense.income.MonthlyIncomeResponse;
import ktc.nhom1ktc.entity.expense.management.income.MonthlyIncome;
import ktc.nhom1ktc.service.expense.IMonthlyIncomeService;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.time.Month;
import java.time.Year;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class MonthlyIncomeController {

    @Autowired
    private IMonthlyIncomeService<MonthlyIncome> monthlyIncomeService;

    @GetMapping(value = {
            "/v1/monthly-income/"
    })
    public MonthlyIncomeResponse getByYear(@RequestParam("year") int year) {
        List<MonthlyIncome> monthlyIncomes = monthlyIncomeService.findAllByYear(Year.of(year));
        return new MonthlyIncomeResponse(year, monthlyIncomes);
    }

    @GetMapping(value = {
            "/v1/monthly-income/{year}/"
    })
    public MonthlyIncomeResponse getByMonths(@PathVariable("year") int year,
                                             @RequestParam(value = "months", required = false)  @Size(max = 12) Set<Integer> months) {
//        Set<Month> monthSet = ObjectUtils.isEmpty(months)
//                ? Collections.emptySet()
//                : months.stream().map(Month::of).collect(Collectors.toSet());
        Set<Month> monthSet = months.stream().map(Month::of).collect(Collectors.toSet());
        List<MonthlyIncome> monthlyIncomes = monthlyIncomeService.findByYearMonths(Year.of(year), monthSet);
        return new MonthlyIncomeResponse(year, monthlyIncomes);
    }
}
