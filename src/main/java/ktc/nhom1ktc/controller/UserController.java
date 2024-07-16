package ktc.nhom1ktc.controller;

import ktc.nhom1ktc.dto.ApiResponse;
import ktc.nhom1ktc.entity.Users;
import ktc.nhom1ktc.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/{id}")
    public ApiResponse<Users> getUserById(@PathVariable UUID id) {
        Users user = userService.getByID(id);
        return ApiResponse.<Users>builder()
                .code(1000)
                .result(user)
                .build();
    }

    @PostMapping
    public ApiResponse<Users> createUser(@RequestBody Users user) {
        Users createdUser = userService.create(user);
        return ApiResponse.<Users>builder()
                .code(1000)
                .result(createdUser)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<Users> updateUser(@PathVariable UUID id, @RequestBody Users user) {
        Users updatedUser = userService.update(id, user);
        return ApiResponse.<Users>builder()
                .code(1000)
                .result(updatedUser)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable UUID id) {
        userService.delete(id);
        return ApiResponse.<Void>builder()
                .code(1000)
                .build();
    }

    @GetMapping
    public ApiResponse<List<Users>> getAllUsers() {
        List<Users> users = userService.getAll();
        return ApiResponse.<List<Users>>builder()
                .code(1000)
                .result(users)
                .build();
    }

    @GetMapping("/page")
    public ApiResponse<Page<Users>> getAllUsersPageable(Pageable pageable) {
        Page<Users> usersPage = userService.getAllPageable(pageable);
        return ApiResponse.<Page<Users>>builder()
                .code(1000)
                .result(usersPage)
                .build();
    }


    @PutMapping("/open/{id}")
    ApiResponse<Void> open(@PathVariable String id) {
        UUID idUsers = null;
        if (id != null) {
            idUsers = UUID.fromString(id);
            userService.open(idUsers);
        } return ApiResponse.<Void>builder().build();
    }
}
