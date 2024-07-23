package ktc.nhom1ktc.exception.expense.income;

import ktc.nhom1ktc.exception.ErrorCode;
import lombok.Getter;
import lombok.Setter;

import java.time.YearMonth;

@Getter
@Setter
public class IncomeDateNotExistedInMonthlyIncomeException extends IncomeException {

    private static final ErrorCode errorCode = ErrorCode.INCOME_DATE_NOT_EXISTED_IN_MONTHLY_INCOME;
//    private YearMonth yearMonth;

    public IncomeDateNotExistedInMonthlyIncomeException(YearMonth yearMonth) {
        super(errorCode.getMessage());
        super.yearMonth = yearMonth;
    }


    @Override
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
