package ktc.nhom1ktc.dto.expense.stats;

import ktc.nhom1ktc.entity.expense.management.MonthlyLog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StatsResponse {
//    private Map<UUID, ConcurrentMap<Integer, List<MonthlyLog>>> monthsByCategory;
//    private Map<Integer, ConcurrentMap<UUID, List<MonthlyLog>>> categoriesByMonth;
    private Map<UUID, LogByMonthResponse> monthsByCategory2;
    private Map<Integer, TreeMap<UUID, LogByCategoryResponse>> categoriesByMonth2;

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LogByCategoryResponse {
        private String name;
        private MonthlyLog log;
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LogByMonthResponse {
        private String name;
        private Map<Integer, MonthlyLog> logs;
    }
}
