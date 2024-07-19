package ktc.nhom1ktc.service.expense;

import ktc.nhom1ktc.dto.expense.income.IncomeRequest;
import ktc.nhom1ktc.exception.expense.IncomeObjectNotFoundForAccountException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public interface IIncomeService<T> {

    List<T> findByYear(int year);

    @Transactional
    T addSingle(T t);

    @Transactional
    T addSingle(IncomeRequest request) throws Exception;

    @Transactional
    T addSingle(BigDecimal amount, LocalDate date) throws Exception;

    @Transactional
    T update(IncomeRequest request) throws Exception;

    @Transactional
    T update(T t);

    @Transactional
    int deleteById(UUID request) throws Exception;
}
