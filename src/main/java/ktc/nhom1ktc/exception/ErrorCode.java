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
    EMAIL_NOT_FOUND(1011,"Khong tim thay mail" ,HttpStatus.NOT_FOUND );

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