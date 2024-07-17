package ktc.nhom1ktc.service.expense;

import ktc.nhom1ktc.dto.expense.outcome.ExpenseRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public interface IExpenseService<T> {

    T addSingle(T e);

    @Transactional
    T addSingle(ExpenseRequest request);

    @Transactional
    int deleteById(UUID id);

    List<T> findByYear(int year);

    T findById(UUID id);

    @Transactional
    T update(ExpenseRequest expenseRequest);
}
