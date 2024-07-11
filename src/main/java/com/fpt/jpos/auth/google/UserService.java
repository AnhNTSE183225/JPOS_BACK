package com.fpt.jpos.auth.google;

import com.fpt.jpos.pojo.Account;
import com.fpt.jpos.pojo.enums.Provider;
import com.fpt.jpos.pojo.enums.Role;
import com.fpt.jpos.repository.IAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final IAccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public Account processOAuthPostLogin(String email) {
        Account newUser = new Account();
        newUser.setUsername("GOOGLE_" + email.split("@")[0]);
        newUser.setPassword(passwordEncoder.encode("GOOGLE_" + email.split("@")[0]));
        newUser.setEmail(email);
        newUser.setProvider(Provider.GOOGLE);
        newUser.setRole(Role.customer);
        newUser.setStatus(true);
        return newUser;
    }

    public Account getAccount(String email) {
        return accountRepository.findOneByEmail(email);
    }

    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

}
