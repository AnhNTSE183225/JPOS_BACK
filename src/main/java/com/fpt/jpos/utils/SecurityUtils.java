package com.fpt.jpos.utils;

import com.fpt.jpos.pojo.Account;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    public static Account getPrincipal() {
        return (Account) (SecurityContextHolder.getContext()).getAuthentication().getPrincipal();
    }
}
