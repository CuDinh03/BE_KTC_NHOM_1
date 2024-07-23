package ktc.nhom1ktc.exception.expense.category;

import ktc.nhom1ktc.exception.ErrorCode;
import lombok.Getter;

public class DuplicateCategoryNameException extends CategoryException {

    private static final ErrorCode errorCode = ErrorCode.DUPLICATE_CATEGORY_NAME;

    public DuplicateCategoryNameException() {
        super(errorCode.getMessage());
    }

    @Override
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
