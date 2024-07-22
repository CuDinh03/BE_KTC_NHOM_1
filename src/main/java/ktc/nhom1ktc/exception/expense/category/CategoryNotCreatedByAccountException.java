package ktc.nhom1ktc.exception.expense.category;

import ktc.nhom1ktc.exception.ErrorCode;

public class CategoryNotCreatedByAccountException extends CategoryException {
    private static final ErrorCode errorCode = ErrorCode.CATEGORY_ID_NOT_CREATED_BY_ACCOUNT_EXCEPTION;

    public CategoryNotCreatedByAccountException() {
        super(errorCode.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
