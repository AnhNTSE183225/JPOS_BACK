package com.fpt.jpos.auth.google;

import com.fpt.jpos.pojo.Account;
import com.fpt.jpos.pojo.enums.Provider;
import com.fpt.jpos.repository.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private IAccountRepository accountRepository;

    public void processOAuthPostLogin(String username) {
        Optional<Account> existUser = accountRepository.findByUsername(username);

        if (existUser.isEmpty()) {
            Account newUser = new Account();
            newUser.setUsername(username);
            newUser.setProvider(Provider.GOOGLE);
            newUser.setStatus(true);
            accountRepository.save(newUser);
        }

    }

}
