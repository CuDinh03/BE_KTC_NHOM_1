package ktc.nhom1ktc.TranferData;

import ktc.nhom1ktc.dto.AccountDto;
import ktc.nhom1ktc.entity.Account;

import java.util.ArrayList;
import java.util.List;

public class TranferData {

    public static AccountDto convertToDto(Account entity) {
        AccountDto dto = new AccountDto();
        if (entity.getId() != null) dto.setId(entity.getId());

        if (entity.getCode() != null) dto.setCode(entity.getCode());
        if (entity.getUsername() != null) dto.setUsername(entity.getUsername());

        if (entity.getPassword() != null) dto.setPassword(entity.getPassword());

        if (entity.getRole() != null) dto.setRole(entity.getRole());

        if (entity.getCreatedAt() != null) dto.setCreatedAt(entity.getCreatedAt());

        if (entity.getUpdateAt() != null) dto.setUpdateAt(entity.getUpdateAt());

        if (entity.getCreatedBy() != null) dto.setCreatedBy(entity.getCreatedBy());
        if (entity.getUpdatedBy() != null) dto.setUpdatedBy(entity.getUpdatedBy());

        if (entity.getStatus() != null) dto.setStatus(entity.getStatus());

        return dto;
    }

    public static Account convertToEntity(AccountDto dto) {
        Account entity = new Account();
        if (dto.getId() != null) entity.setId(dto.getId());

        if (dto.getCode() != null) entity.setCode(dto.getCode());

        if (dto.getUsername() != null) entity.setUsername(dto.getUsername());

        if (dto.getPassword() != null) entity.setPassword(dto.getPassword());

        if (dto.getRole() != null) entity.setRole(dto.getRole());

        if (dto.getCreatedAt() != null) entity.setCreatedAt(dto.getCreatedAt());

        if (dto.getUpdateAt() != null) entity.setUpdateAt(dto.getUpdateAt());
        if (dto.getCreatedBy() != null) entity.setCreatedBy(dto.getCreatedBy());
        if (dto.getUpdatedBy() != null) entity.setUpdatedBy(dto.getUpdatedBy());

        if (dto.getStatus() != null) entity.setStatus(dto.getStatus());
        return entity;
    }

    public static List<AccountDto> convertListAccountToDto(List<Account> entityList) {
        List<AccountDto> dtoList = new ArrayList<>();
        for (Account entity : entityList) {
            dtoList.add(convertToDto(entity));
        }
        return dtoList;
    }
    public static List<Account> convertListAccountEntity(List<AccountDto> dtoList) {
        List<Account> entityList = new ArrayList<>();
        for (AccountDto dto : dtoList) {
            entityList.add(convertToEntity(dto));
        }
        return entityList;
    }
}
