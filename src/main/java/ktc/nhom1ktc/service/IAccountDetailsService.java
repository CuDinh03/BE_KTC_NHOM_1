package ktc.nhom1ktc.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface IAccountDetailsService<T extends UserDetails, U extends T> extends UserDetailsService {

    T loadUserByUsername(String username) throws UsernameNotFoundException;

}
