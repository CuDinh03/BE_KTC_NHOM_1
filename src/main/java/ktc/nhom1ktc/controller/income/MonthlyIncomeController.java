package ktc.nhom1ktc.controller.income;

import ktc.nhom1ktc.dto.expense.income.MonthlyIncomeRequest;
import ktc.nhom1ktc.dto.expense.income.MonthlyIncomeResponse;
import ktc.nhom1ktc.entity.expense.management.income.MonthlyIncome;
import ktc.nhom1ktc.service.impl.expense.MonthlyIncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MonthlyIncomeController {

    @Autowired
    private MonthlyIncomeService monthlyIncomeService;

    @GetMapping(value = {
            "/v1/monthly-income/get-by-year"
    })
    public MonthlyIncomeResponse getByYear(@RequestBody MonthlyIncomeRequest request) {
        List<MonthlyIncome> monthlyIncomes = monthlyIncomeService.findAllByYear(request.getYear());
        return new MonthlyIncomeResponse(request.getYear(), monthlyIncomes);
    }

    @GetMapping(value = {
            "/v1/monthly-income/get-by-year-months"
    })
    public MonthlyIncomeResponse getByMonths(@RequestBody MonthlyIncomeRequest request) {
        List<MonthlyIncome> monthlyIncomes = monthlyIncomeService.findByYearMonths(request.getYear(), request.getMonths());
        return new MonthlyIncomeResponse(request.getYear(), monthlyIncomes);
    }
}
