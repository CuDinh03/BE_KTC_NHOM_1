package ktc.nhom1ktc.service.expense;

import ktc.nhom1ktc.dto.expense.outcome.ExpenseRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface IExpenseService<T> {

    T addSingle(T e);

    @Transactional
    T addSingle(ExpenseRequest request);
}
