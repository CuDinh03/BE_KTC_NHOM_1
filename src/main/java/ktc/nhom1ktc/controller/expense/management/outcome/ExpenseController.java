package ktc.nhom1ktc.controller.expense.management.outcome;

import ktc.nhom1ktc.dto.expense.outcome.ExpenseRequest;
import ktc.nhom1ktc.dto.expense.outcome.ExpenseResponse;
import ktc.nhom1ktc.entity.expense.management.outcome.Expense;
import ktc.nhom1ktc.service.expense.IExpenseService;
import ktc.nhom1ktc.service.impl.AccountUtil;
import ktc.nhom1ktc.service.impl.expense.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ExpenseController {

    @Autowired
    private IExpenseService<Expense> expenseService;
    @Autowired
    private AccountUtil accountUtil;

    @PostMapping(value = {
            "/v1/expense/add-single",
    })
    public ExpenseResponse addSingle(@RequestBody ExpenseRequest expenseRequest) {
        Expense expense = expenseService.addSingle(expenseRequest);
        return new ExpenseResponse(expense);
    }
}
