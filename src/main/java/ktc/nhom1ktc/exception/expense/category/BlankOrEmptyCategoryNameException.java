package ktc.nhom1ktc.exception.expense.category;

import ktc.nhom1ktc.exception.ErrorCode;

public class BlankOrEmptyCategoryNameException extends CategoryException {

    private static final ErrorCode errorCode = ErrorCode.BLANK_OR_EMPTY_CATEGORY_NAME;

    public BlankOrEmptyCategoryNameException() {
        super(errorCode.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
