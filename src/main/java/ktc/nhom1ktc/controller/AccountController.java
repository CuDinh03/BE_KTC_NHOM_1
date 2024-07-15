package ktc.nhom1ktc.controller;

import ktc.nhom1ktc.TranferData.TranferData;
import ktc.nhom1ktc.dto.AccountDto;
import ktc.nhom1ktc.dto.ChangePasswordRequest;
import ktc.nhom1ktc.entity.Account;
import ktc.nhom1ktc.dto.ApiResponse;
import ktc.nhom1ktc.service.impl.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/{id}")
    public ApiResponse<Account> getById(@PathVariable UUID id) {
        Account account = accountService.getByID(id);
        return ApiResponse.<Account>builder()
                .code(1000)
                .result(account)
                .build();
    }

    @PostMapping
    public ApiResponse<Account> create(@RequestBody AccountDto request) {
        Account createdAccount = accountService.create(TranferData.convertToEntity(request), request.getMail());
        return ApiResponse.<Account>builder()
                .code(1000)
                .result(createdAccount)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<Account> update(@PathVariable UUID id, @RequestBody Account account) {
        Account updatedAccount = accountService.update(id, account);
        return ApiResponse.<Account>builder()
                .code(1000)
                .result(updatedAccount)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable UUID id) {
        accountService.delete(id);
        return ApiResponse.<Void>builder()
                .code(1000)
                .build();
    }

    @GetMapping("/all")
    public ApiResponse<List<Account>> getAll() {
        List<Account> accounts = accountService.getAll();
        return ApiResponse.<List<Account>>builder()
                .code(1000)
                .result(accounts)
                .build();
    }

    @GetMapping("/page")
    public ApiResponse<Page<Account>> getAllPageable(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Account> accountsPage = accountService.getAllPageable(pageable);
        return ApiResponse.<Page<Account>>builder()
                .code(1000)
                .result(accountsPage)
                .build();
    }

    @PostMapping("/change-password")
    public ApiResponse<String> changePassword(@RequestBody ChangePasswordRequest request) {
        boolean result = accountService.changePassword(request);
        if (result) {
            return ApiResponse.<String>builder()
                    .code(1000)
                    .message("Password changed successfully")
                    .result("Success")
                    .build();
        } else {
            return ApiResponse.<String>builder()
                    .code(1001)
                    .message("Invalid old password or account not found")
                    .result("Failure")
                    .build();
        }
    }
}
