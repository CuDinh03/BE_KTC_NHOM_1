package ktc.nhom1ktc.controller.income;

import ktc.nhom1ktc.dto.expense.income.IncomeRequest;
import ktc.nhom1ktc.dto.expense.income.IncomeResponse;
import ktc.nhom1ktc.entity.expense.management.income.Income;
import ktc.nhom1ktc.service.expense.IIncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class IncomeController {
    @Autowired
    private IIncomeService<Income> incomeService;

    @PostMapping(value = {
            "/v1/income/add-single"
    })
    public IncomeResponse addSingle(@RequestBody IncomeRequest incomeRequest) throws Exception {
        Income income = incomeService.addSingle(incomeRequest);
        return new IncomeResponse(income);
    }

    @PutMapping(value = {
            "/v1/income/update"
    })
    public IncomeResponse update(@RequestBody IncomeRequest incomeRequest) throws Exception {
        Income income = incomeService.update(incomeRequest);
        return new IncomeResponse(income);
    }
}
