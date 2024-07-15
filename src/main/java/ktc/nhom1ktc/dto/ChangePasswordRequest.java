package ktc.nhom1ktc.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ChangePasswordRequest {
    private UUID accountId;
    private String oldPassword;
    private String newPassword;
}
