package ktc.nhom1ktc.exception.expense.outcome;

import ktc.nhom1ktc.exception.ErrorCode;

public abstract class ExpenseException extends Exception {

    public ExpenseException(String message) {
        super(message);
    }

    public abstract ErrorCode getErrorCode();
}
