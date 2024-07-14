package ktc.nhom1ktc.exception.expense;

import ktc.nhom1ktc.exception.ErrorCode;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class IncomeObjectNotFoundForAccountException extends Exception {
    private ErrorCode errorCode;
    private UUID incomeId;

    public IncomeObjectNotFoundForAccountException(ErrorCode errorCode, UUID incomeId) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.incomeId = incomeId;
    }
}
