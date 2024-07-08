package ktc.nhom1ktc.service;

import ktc.nhom1ktc.entity.Account;

public interface IAccountService {
    Account create(Account request, String mail);
}
