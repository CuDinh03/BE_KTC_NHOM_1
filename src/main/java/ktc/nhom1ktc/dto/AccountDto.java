package ktc.nhom1ktc.dto;

import ktc.nhom1ktc.entity.Role;
import lombok.Data;

import java.util.Date;
import java.util.UUID;
@Data
public class AccountDto {
    private UUID id;
    private String code;
    private String username;
    private String password;
    private Role role;
    private String createdBy;
    private String updatedBy;
    private Date createdAt;
    private Date updateAt;
    private Integer status;
    private String mail;
}
