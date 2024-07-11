package ktc.nhom1ktc.controller.expense;

import ktc.nhom1ktc.dto.expense.category.CategoryRequest;
import ktc.nhom1ktc.dto.expense.category.CategoryResponse;
import ktc.nhom1ktc.entity.expense.management.category.Category;
import ktc.nhom1ktc.service.impl.AccountUtil;
import ktc.nhom1ktc.service.impl.expense.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AccountUtil accountUtil;

    @GetMapping(value = {
            "/v1/category/get-all",
            "/v1/admin/category/get-all"
    })
    public CategoryResponse getAllCategories(Principal principal) {
//        List<CategoryResponse.Category> categories = categoryService.findAllByUsername(principal.getName()).stream()
        List<CategoryResponse.Category> categories = categoryService.findAllByUsername(accountUtil.getUsername()).stream()

                .map(c -> CategoryResponse.Category.builder()
                        .id(c.getId())
                        .name(c.getName())
                        .status(c.getStatus())
                        .type(c.getType())
                        .build())
                .toList();

        return CategoryResponse.builder()
                .categories(categories)
                .build();

    }

    @PostMapping(value = {
            "/v1/category/add-single",
            "/v1/admin/category/add-single"
    })
    public CategoryResponse addSingleCategory(@RequestBody CategoryRequest request) {
        Category createdCategory = categoryService.createOne(request.getName(), request.getAccountId()).orElse(null);
        return buildResponse(createdCategory);
    }

    @PutMapping("/v1/category/set-name")
    public CategoryResponse setCategoryName(@RequestBody CategoryRequest request) {
//        log.info("setCategoryName request {}", request);
        Category updatedCategory = categoryService.updateCategoryName(request.getId(), request.getAccountId(), request.getName());
//        log.info("setCategoryName response {}", updatedCategory);
        return buildResponse(updatedCategory);
    }

    @PutMapping("/v1/admin/category/set-name")
    public CategoryResponse setCategoryNameByAdmin(@RequestBody CategoryRequest request) {
        Category updatedCategory = categoryService.updateCategoryNameWithRoleAdmin(request.getId(), request.getAccountId(), request.getName());
        return buildResponse(updatedCategory);
    }

    @PutMapping(value = {
            "/v1/category/set-status",
            "/v1/admin/category/set-status"
    })
    public CategoryResponse setCategoryStatus(@RequestBody CategoryRequest request) {
        Category updatedCategory = categoryService.updateCategoryStatus(request.getId(), request.getAccountId(), request.getStatus());
        return buildResponse(updatedCategory);
    }


    private CategoryResponse buildResponse(Category category) {
        CategoryResponse response = CategoryResponse.builder().build();

        if (ObjectUtils.isNotEmpty(category)) {
            response.setSingleCategory(
                    CategoryResponse.Category.builder()
                            .id(category.getId())
                            .name(category.getName())
                            .status(category.getStatus())
                            .type(category.getType())
                            .build()
            );
        }

        return response;
    }
}
