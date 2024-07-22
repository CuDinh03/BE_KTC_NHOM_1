package ktc.nhom1ktc.exception.expense.income;

import ktc.nhom1ktc.exception.ErrorCode;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class IncomeObjectNotFoundForAccountException extends IncomeException {
    private static final ErrorCode errorCode = ErrorCode.INCOME_OBJECT_NOT_FOUND_FOR_ACCOUNT;
    private UUID incomeId;

    public IncomeObjectNotFoundForAccountException(ErrorCode errorCode, UUID incomeId) {
        super(errorCode.getMessage());
//        this.errorCode = errorCode;
        super.incomeId = incomeId;
    }

    @Override
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
