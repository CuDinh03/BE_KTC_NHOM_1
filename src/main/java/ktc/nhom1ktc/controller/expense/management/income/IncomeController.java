package ktc.nhom1ktc.controller.expense.management.income;

import ktc.nhom1ktc.dto.expense.income.IncomeRequest;
import ktc.nhom1ktc.dto.expense.income.IncomeResponse;
import ktc.nhom1ktc.entity.expense.management.income.Income;
import ktc.nhom1ktc.exception.expense.IncomeObjectNotFoundForAccountException;
import ktc.nhom1ktc.service.expense.IIncomeService;
import lombok.extern.slf4j.Slf4j;
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
    public IncomeResponse update(@RequestBody IncomeRequest.UpdateRequest updateRequest) throws Exception {
        Income income = incomeService.update(new IncomeRequest(updateRequest));
        return new IncomeResponse(income);
    }

    @DeleteMapping(value = {
            "/v1/income/delete"
    })
    public IncomeResponse delete(@RequestBody IncomeRequest.DeleteRequest deleteRequest) throws Exception {
        return new IncomeResponse(incomeService.deleteById(deleteRequest.getId()));
    }
}
