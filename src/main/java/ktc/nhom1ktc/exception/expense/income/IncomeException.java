package ktc.nhom1ktc.exception.expense.income;


import ktc.nhom1ktc.exception.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.YearMonth;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public abstract class IncomeException extends Exception {
    protected UUID incomeId;
    protected YearMonth yearMonth;

    public IncomeException(String msg) {
        super(msg);
    }

    public abstract ErrorCode getErrorCode();
}
