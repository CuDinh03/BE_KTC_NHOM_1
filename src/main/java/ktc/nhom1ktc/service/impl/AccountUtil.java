package ktc.nhom1ktc.service.impl;

import ktc.nhom1ktc.entity.Role;
import ktc.nhom1ktc.service.IAccountDetailsService;
import ktc.nhom1ktc.service.IAccountService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Component
public class AccountUtil implements IAccountDetailsService<AccountUtil.AccountDetails, AccountUtil.Account> {

    @Autowired
    private IAccountService<ktc.nhom1ktc.entity.Account> accountService;

    public String getUsername() {
        String name = "UNKNOWN_USER";
        try {
            name = SecurityContextHolder.getContext().getAuthentication().getName();
        } catch (Exception e) {

        }
        return name;
    }

    @Override
    public AccountDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        ktc.nhom1ktc.entity.Account accountEntity = accountService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

//        username, password from accountEntity
//        -> buildAuthorities(): Collection<GrantedAuthority>
//        SimpleGrantedAuthority impl GrantedAuthority -> Simple String role
//        new Account(username, password, Collection<GrantedAuthority>);
//        return Account;

        Collection<GrantedAuthority> authorities = buildAccountAuthorities(Set.of(accountEntity.getRole()));

        return new Account(accountEntity.getId(), accountEntity.getUsername(), accountEntity.getPassword(), authorities);
    }

    private Collection<GrantedAuthority> buildAccountAuthorities(Set<Role> accountRoles) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        accountRoles.forEach(r -> authorities.add(new SimpleGrantedAuthority(r.getName())));
        return authorities;

//        return accountRoles.stream()
//                .map(r -> new SimpleGrantedAuthority(r.getName()))
//                .collect(Collectors.toSet());
    }

    public interface AccountDetails extends UserDetails {
        UUID getId();
    }

    @Getter
    public static class Account extends User implements AccountDetails {
        private final UUID id;

        public Account(UUID id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
            super(username, password, authorities);
            this.id = id;
        }
    }
}
