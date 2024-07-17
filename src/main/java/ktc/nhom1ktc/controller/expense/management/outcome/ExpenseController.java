package ktc.nhom1ktc.controller.expense.management.outcome;

import ktc.nhom1ktc.dto.expense.income.IncomeRequest;
import ktc.nhom1ktc.dto.expense.income.IncomeResponse;
import ktc.nhom1ktc.dto.expense.outcome.ExpenseRequest;
import ktc.nhom1ktc.dto.expense.outcome.ExpenseResponse;
import ktc.nhom1ktc.entity.expense.management.income.Income;
import ktc.nhom1ktc.entity.expense.management.outcome.Expense;
import ktc.nhom1ktc.service.expense.IExpenseService;
import ktc.nhom1ktc.service.impl.AccountUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ExpenseController {

    @Autowired
    private IExpenseService<Expense> expenseService;
    @Autowired
    private AccountUtil accountUtil;

    @GetMapping(value = {
            "/v1/expense/get-by-year/{year}"
    })
    public ExpenseResponse.ExpenseListResponse getByYear(@PathVariable("year") int year) {
        List<Expense> expenses = expenseService.findByYear(year);
        return new ExpenseResponse.ExpenseListResponse(expenses);
    }

    @PostMapping(value = {
            "/v1/expense/add-single",
    })
    public ExpenseResponse addSingle(@RequestBody ExpenseRequest expenseRequest) {
        Expense expense = expenseService.addSingle(expenseRequest);
        return new ExpenseResponse(expense);
    }

    @PutMapping(value = {
            "/v1/expense/update"
    })
    public ExpenseResponse update(@RequestBody IncomeRequest.UpdateRequest updateRequest) throws Exception {
        Expense expense = expenseService.update(IncomeRequest.UpdateRequest.toExpenseRequest(updateRequest));
        return new ExpenseResponse(expense);
    }

    @DeleteMapping(value = {
            "/v1/expense/delete",
    })
    public ExpenseResponse.DeleteResponse delete(@RequestBody ExpenseRequest.DeleteRequest deleteRequest) {
        return new ExpenseResponse.DeleteResponse(expenseService.deleteById(deleteRequest.getId()), true);
    }
}
