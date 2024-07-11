package ktc.nhom1ktc.service.impl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AccountUtil {


    public String getUsername() {
        String name = "UNKNOWN_USER";
        try {
            name = SecurityContextHolder.getContext().getAuthentication().getName();
        } catch (Exception e) {

        }
        return name;
    }
}
