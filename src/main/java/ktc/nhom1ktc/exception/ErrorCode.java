package ktc.nhom1ktc.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "UNCATEGORIZED ERROR", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "INVALID MESSAGE", HttpStatus.BAD_REQUEST),
    ACCOUNT_EXISTED(1002, "Tai khoan da ton tai", HttpStatus.BAD_REQUEST),
    ACCOUNT_NOT_EXISTED(1003, "Khong tim thay tai khoan", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1004, "UNAUTHENTICATED", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1005, "Khong co quyen truy cap", HttpStatus.FORBIDDEN),
    NO_ACCOUNTS_FOUND(1006, "Không tìm thấy danh sách tài khoản nào",HttpStatus.NOT_FOUND),
    ROLES_NOT_EXISTED(1007, "Không tìm thấy roles nào", HttpStatus.NOT_FOUND),
    EMAIL_EXISTED(1008,"Da ton tai mail nay" ,HttpStatus.BAD_REQUEST ),
    INVALID_TOKEN(1009, "Khong tim thay token",HttpStatus.NOT_FOUND ),
    EXPIRED_TOKEN(1010,"Token da het han",HttpStatus.BAD_REQUEST),
    EMAIL_NOT_FOUND(1011,"Khong tim thay mail" ,HttpStatus.NOT_FOUND ),
    INCOME_DATE_NOT_EXISTED_IN_MONTHLY_INCOME(2012, "Income date was not recorded by monthly income", HttpStatus.BAD_REQUEST),
    INCOME_OBJECT_NOT_FOUND_FOR_ACCOUNT(2013, "Income object was not found for account", HttpStatus.NOT_FOUND),
    BLANK_OR_EMPTY_CATEGORY_NAME(2014, "Black or empty category name", HttpStatus.BAD_REQUEST),
    DUPLICATE_CATEGORY_NAME(2015, "Duplicate category name", HttpStatus.BAD_REQUEST),
    EXPENSE_YEAR_EXCEPTION(2016, "Expense year to set must be current year", HttpStatus.BAD_REQUEST),
    CATEGORY_ID_NOT_CREATED_BY_ACCOUNT_EXCEPTION(2017, "Category does not belong to current account", HttpStatus.BAD_REQUEST),
    EXPENSE_ID_NOT_CREATED_BY_ACCOUNT_EXCEPTION(2018, "Expense does not belong to current account", HttpStatus.BAD_REQUEST),
    MONTHLY_INCOME_LIST_EXISTED_BY_ACCOUNT_AND_YEAR(2019, "List of monthly income already existed in current account and year", HttpStatus.BAD_REQUEST),
    ;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = httpStatusCode;
    }

    private int code;
    private String message;
    private HttpStatusCode statusCode;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}