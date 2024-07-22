package ktc.nhom1ktc.exception.expense.category;

import ktc.nhom1ktc.exception.ErrorCode;

public abstract class CategoryException extends Exception {

    public CategoryException(String message) {
        super(message);
    }

    public abstract ErrorCode getErrorCode();
}
