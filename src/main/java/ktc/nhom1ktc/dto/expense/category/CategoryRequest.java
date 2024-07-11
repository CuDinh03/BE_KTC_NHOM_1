package ktc.nhom1ktc.dto.expense.category;

import ktc.nhom1ktc.entity.expense.Status;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class CategoryRequest {

    private UUID id;
    private UUID accountId;
    private String name;
    private Status status;

}
