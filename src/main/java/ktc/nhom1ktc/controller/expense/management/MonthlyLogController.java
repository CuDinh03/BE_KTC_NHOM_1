package ktc.nhom1ktc.controller.expense.management;

import ktc.nhom1ktc.dto.expense.MonthlyLogRequest;
import ktc.nhom1ktc.dto.expense.MonthlyLogResponse;
import ktc.nhom1ktc.entity.expense.management.MonthlyLog;
import ktc.nhom1ktc.service.expense.IMonthlyLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class MonthlyLogController {

    @Autowired
    private IMonthlyLogService<MonthlyLog> monthlyLogService;

    @GetMapping(value = {
            "/v1/monthly-log/get-by-year"
    })
    public MonthlyLogResponse getByYear(@RequestBody MonthlyLogRequest request) {
        List<MonthlyLog> logs = monthlyLogService.findAllByYear(request.getYear());
        log.info("getByYear logs {}", logs);
        return new MonthlyLogResponse(request.getYear(), logs);
    }

    @GetMapping(value = {
            "/v1/monthly-log/get-by-year-months"
    })
    public MonthlyLogResponse getByYearMonths(@RequestBody MonthlyLogRequest request) {
        List<MonthlyLog> logs = monthlyLogService.findAllByYearMonths(request.getYear(), request.getMonths());
        return new MonthlyLogResponse(request.getYear(), logs);
    }

    @PutMapping(value = {
            "/v1/monthly-log/set-budget"
    })
    public MonthlyLogResponse setBudget(@RequestBody MonthlyLogRequest.UpdateRequest request) {
        MonthlyLog monthlyLog = monthlyLogService.setBudget(request.getId(), request.getAmount());
        log.info("setBudget monthlyLog {}", monthlyLog);
        return new MonthlyLogResponse(monthlyLog.getYear(), List.of(monthlyLog));
    }
}
