package ktc.nhom1ktc.controller.expense;

import jakarta.validation.constraints.Min;
import ktc.nhom1ktc.dto.expense.stats.StatsResponse;
import ktc.nhom1ktc.entity.expense.management.MonthlyLog;
import ktc.nhom1ktc.entity.expense.management.category.Category;
import ktc.nhom1ktc.service.expense.ICategoryService;
import ktc.nhom1ktc.service.expense.IMonthlyLogService;
import ktc.nhom1ktc.service.impl.AccountUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api")
public class StatisticsController {

    @Autowired
    private IMonthlyLogService<MonthlyLog> monthlyLogService;

    @Autowired
    private ICategoryService<Category, UUID> categoryService;

    @Autowired
    private AccountUtil accountUtil;

    @GetMapping(value = {
            "/v1/stats/{year}"
    })
    public StatsResponse makeStats(@PathVariable("year") @Min(2024) int year) {

        List<Category> categoryList = categoryService.findAllByUsername(accountUtil.getUsername());
//        log.info("categoryList {}", categoryList);
        var catMap = categoryList.stream().collect(Collectors.toMap(Category::getId, Category::getName));
//        log.info("catMap {}", catMap);
        List<MonthlyLog> monthlyLogList = monthlyLogService.findAllByYear(Year.of(year));
//        log.info("monthlyLogList {}", monthlyLogList);

//        var monthsByCategory = monthlyLogList.parallelStream()
//                .collect((Collectors.groupingByConcurrent(MonthlyLog::getCategoryId,
//                        Collectors.groupingByConcurrent(l -> l.getMonth().getValue()))));
//        log.info("monthsByCategory {}", monthsByCategory);

        var monthsByCategory = monthlyLogList.parallelStream()
                .collect(Collectors.groupingByConcurrent(MonthlyLog::getCategoryId,
                        Collectors.collectingAndThen(Collectors.toSet(), set -> {
                            Map<Integer, MonthlyLog> logs = new TreeMap<>();
                            AtomicReference<String> name = new AtomicReference<>();
                            set.stream().findFirst().ifPresentOrElse(l -> name.set(catMap.get(l.getCategoryId())), null);
                            for (var log : set) {
                                logs.put(log.getMonth().getValue(), log);
                            }
                            return new StatsResponse.LogByMonthResponse(name.get(), logs);
                        })));


//        var categoriesByMonth = monthlyLogList.parallelStream()
//                .collect(Collectors.groupingByConcurrent(l -> l.getMonth().getValue(),
//                        Collectors.groupingByConcurrent(MonthlyLog::getCategoryId)));
//        log.info("categoriesByMonth {}", categoriesByMonth);

        var categoriesByMonth = monthlyLogList.parallelStream()
                .collect(Collectors.groupingByConcurrent(l -> l.getMonth().getValue(),
                        Collectors.collectingAndThen(Collectors.toMap(MonthlyLog::getCategoryId, l -> new StatsResponse.LogByCategoryResponse(catMap.get(l.getCategoryId()), l)), TreeMap::new)));
//        log.info("categoriesByMonth2 {}", categoriesByMonth2);

        return StatsResponse.builder()
//                .monthsByCategory(monthsByCategory)
                .monthsByCategory(monthsByCategory)
//                .categoriesByMonth(categoriesByMonth)
                .categoriesByMonth(categoriesByMonth)
                .build();
    }
}
