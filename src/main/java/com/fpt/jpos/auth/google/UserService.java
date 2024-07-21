package com.fpt.jpos.auth.google;

import com.fpt.jpos.pojo.Account;
import com.fpt.jpos.repository.IAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final IAccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;


    public Account getAccount(String email) {
        return accountRepository.findOneByEmail(email);
    }

    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

}
