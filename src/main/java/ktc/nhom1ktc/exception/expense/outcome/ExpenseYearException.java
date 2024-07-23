package ktc.nhom1ktc.exception.expense.outcome;

import ktc.nhom1ktc.exception.ErrorCode;

public class ExpenseYearException extends ExpenseException {
    private static final ErrorCode errorCode = ErrorCode.EXPENSE_YEAR_EXCEPTION;

    public ExpenseYearException() {
        super(errorCode.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return null;
    }
}
