package ktc.nhom1ktc.exception;

import ktc.nhom1ktc.dto.ApiResponse;
import ktc.nhom1ktc.exception.expense.income.IncomeDateNotExistedInMonthlyIncomeException;
import ktc.nhom1ktc.exception.expense.category.CategoryException;
import ktc.nhom1ktc.exception.expense.income.IncomeException;
import ktc.nhom1ktc.exception.expense.outcome.ExpenseException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.YearMonth;
import java.util.UUID;

@ControllerAdvice
public class GlobalExceptionHandler {
    // quan ly toan bo exception


    //Exception
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException exception){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }
    //     tu bat exception
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException exception){
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(apiResponse);
    }
    // exception author
    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse> handlingAccessDeniedException(AccessDeniedException exception){
    ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

    return ResponseEntity.status(errorCode.getStatusCode()).body(
            ApiResponse.builder()
                    .code(errorCode.getCode())
                    .message(errorCode.getMessage())
                    .build()
    );
    }

    //validException
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handlingValidation(MethodArgumentNotValidException exception) {
        String enumKey = exception.getFieldError().getDefaultMessage();

        ErrorCode errorCode = ErrorCode.INVALID_KEY;
        try{
            errorCode = ErrorCode.valueOf(enumKey);

        }catch (IllegalArgumentException e){

        }

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());

        return ResponseEntity.badRequest().body(apiResponse);
    }

//    @ExceptionHandler(value = IncomeDateNotExistedInMonthlyIncomeException.class)
//    ResponseEntity<ApiResponse> handleIncomeDateMapping(IncomeDateNotExistedInMonthlyIncomeException exception){
//        YearMonth yearMonth = exception.getYearMonth();
//        String msg = exception.getMessage();
//        ErrorCode errorCode = ErrorCode.INCOME_DATE_NOT_EXISTED_IN_MONTHLY_INCOME;
//
//        return ResponseEntity
//                .status(errorCode.getStatusCode())
//                .body(ApiResponse.builder()
//                        .code(errorCode.getCode())
//                        .result(yearMonth)
//                        .message(msg)
//                        .build());
//    }

    @ExceptionHandler(value = IncomeException.class)
    ResponseEntity<ApiResponse> handleIncomeException(IncomeException exception){
        YearMonth yearMonth = exception.getYearMonth();
        UUID incomeId = exception.getIncomeId();
        StringBuilder msg = new StringBuilder(exception.getMessage());
        String castMsg = ObjectUtils.isEmpty(yearMonth)
                                    ? yearMonth.toString()
                                    : incomeId.toString();
        ErrorCode errorCode = exception.getErrorCode();

        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(ApiResponse.builder()
                        .code(errorCode.getCode())
                        .result(yearMonth)
                        .message(msg.append(" ").append(castMsg).toString())
                        .build());
    }

    @ExceptionHandler(value = CategoryException.class)
    ResponseEntity<ApiResponse> handleCategoryException(CategoryException exception){
        String msg = exception.getMessage();
        ErrorCode errorCode = exception.getErrorCode();

        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(msg)
                        .build());
    }

    @ExceptionHandler(value = ExpenseException.class)
    ResponseEntity<ApiResponse> handleExpenseException(ExpenseException exception){
        String msg = exception.getMessage();
        ErrorCode errorCode = exception.getErrorCode();

        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(msg)
                        .build());
    }
}