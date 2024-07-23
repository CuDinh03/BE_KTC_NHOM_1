package ktc.nhom1ktc.controller.expense.management;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import ktc.nhom1ktc.dto.expense.MonthlyLogRequest;
import ktc.nhom1ktc.dto.expense.MonthlyLogResponse;
import ktc.nhom1ktc.entity.expense.management.MonthlyLog;
import ktc.nhom1ktc.service.expense.IMonthlyLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Year;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/api")
public class MonthlyLogController {

    @Autowired
    private IMonthlyLogService<MonthlyLog> monthlyLogService;

    @GetMapping(value = {
            "/v1/monthly-log/"
    })
    public MonthlyLogResponse getByYear(@RequestParam("year") @Min(2024) int year) {
        List<MonthlyLog> logs = monthlyLogService.findAllByYear(Year.of(year));
        log.info("getByYear logs {}", logs);
        return new MonthlyLogResponse(Year.of(year), logs);
    }

    @GetMapping(value = {
            "/v1/monthly-log/{year}/"
    })
    public MonthlyLogResponse getByMonths(@PathVariable("year") @Min(2024) int year,
                                          @RequestParam(value = "months") @Size(min = 1, max = 12) Set<Integer> months) {
//        months = ObjectUtils.isEmpty(months)
//                ? Collections.emptySet()
//                : months.stream().filter(m -> 0 < m && m <= 12).collect(Collectors.toSet());
        months.stream().filter(m -> 0 < m && m <= 12);
        List<MonthlyLog> logs = monthlyLogService.findAllByYearMonths(Year.of(year), months);
        return new MonthlyLogResponse(Year.of(year), logs);
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
