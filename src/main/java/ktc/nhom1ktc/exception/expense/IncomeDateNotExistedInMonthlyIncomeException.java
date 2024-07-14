package ktc.nhom1ktc.exception.expense;

import ktc.nhom1ktc.exception.ErrorCode;
import lombok.Getter;
import lombok.Setter;

import java.time.YearMonth;

@Getter
@Setter
public class IncomeDateNotExistedInMonthlyIncomeException extends Exception {

    private ErrorCode errorCode;
    private YearMonth yearMonth;

    public IncomeDateNotExistedInMonthlyIncomeException(ErrorCode errorCode, YearMonth yearMonth) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.yearMonth = yearMonth;
    }
}
