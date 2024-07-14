package ktc.nhom1ktc.service.expense;

import ktc.nhom1ktc.dto.expense.income.IncomeRequest;
import ktc.nhom1ktc.exception.expense.IncomeObjectNotFoundForAccountException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public interface IIncomeService<T> {

    T addSingle(T t);

    T addSingle(IncomeRequest request) throws Exception;

    T addSingle(BigDecimal amount, LocalDate date) throws Exception;

    @Transactional
    T update(IncomeRequest request) throws IncomeObjectNotFoundForAccountException;

    @Transactional
    T update(T t);
}