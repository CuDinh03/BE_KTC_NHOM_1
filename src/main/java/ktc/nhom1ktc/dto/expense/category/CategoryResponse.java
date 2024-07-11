package ktc.nhom1ktc.dto.expense.category;

import ktc.nhom1ktc.entity.expense.Status;
import ktc.nhom1ktc.entity.expense.management.category.CategoryType;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Builder
@Data
public class CategoryResponse {

    private List<Category> categories;
    private Category singleCategory;

    @Builder
    @Data
    public static class Category {
        private UUID id;
        private String name;
        private Status status;
        private CategoryType type;
    }
}
