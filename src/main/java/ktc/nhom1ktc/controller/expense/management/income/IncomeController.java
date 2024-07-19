package ktc.nhom1ktc.controller.expense.management.income;

import ktc.nhom1ktc.dto.expense.income.IncomeRequest;
import ktc.nhom1ktc.dto.expense.income.IncomeResponse;
import ktc.nhom1ktc.entity.expense.management.income.Income;
import ktc.nhom1ktc.service.expense.IIncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class IncomeController {
    @Autowired
    private IIncomeService<Income> incomeService;

    @GetMapping(value = {
            "/v1/income/get-by-year/{year}"
    })
    public IncomeResponse.IncomeListResponse getByYear(@PathVariable("year") int year) {
        List<Income> incomes = incomeService.findByYear(year);
        return new IncomeResponse.IncomeListResponse(incomes);
    }

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
        Income income = incomeService.update(IncomeRequest.UpdateRequest.toIncomeRequest(updateRequest));
        return new IncomeResponse(income);
    }

    @DeleteMapping(value = {
            "/v1/income/delete"
    })
    public IncomeResponse.DeleteResponse delete(@RequestBody IncomeRequest.DeleteRequest deleteRequest) throws Exception {
        int result = incomeService.deleteById(deleteRequest.getId());
        return new IncomeResponse.DeleteResponse(deleteRequest.getId(), result == 1);
    }
}
