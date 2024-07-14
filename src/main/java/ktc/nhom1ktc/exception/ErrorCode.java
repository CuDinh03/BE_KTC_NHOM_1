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
    INCOME_DATE_NOT_EXISTED_IN_MONTHLY_INCOME(1008, "Income date was not recorded by monthly income", HttpStatus.BAD_REQUEST),
    INCOME_OBJECT_NOT_FOUND_FOR_ACCOUNT(1009, "Income object was not found for account", HttpStatus.NOT_FOUND),
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