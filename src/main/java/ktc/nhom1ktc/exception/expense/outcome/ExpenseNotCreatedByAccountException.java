package ktc.nhom1ktc.exception.expense.outcome;

import ktc.nhom1ktc.exception.ErrorCode;

public class ExpenseNotCreatedByAccountException extends ExpenseException {
    private static final ErrorCode errorCode = ErrorCode.EXPENSE_ID_NOT_CREATED_BY_ACCOUNT_EXCEPTION;

    public ExpenseNotCreatedByAccountException() {
        super(errorCode.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
