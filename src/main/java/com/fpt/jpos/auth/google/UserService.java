package com.fpt.jpos.auth.google;

import com.fpt.jpos.pojo.Account;
import com.fpt.jpos.pojo.enums.Provider;
import com.fpt.jpos.repository.IAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final IAccountRepository accountRepository;

    public void processOAuthPostLogin(String email) {

        Account newUser = new Account();
        newUser.setEmail(email);
        newUser.setProvider(Provider.GOOGLE);
        newUser.setStatus(true);
        accountRepository.save(newUser);

    }

    public Account getAccount(String email) {
        return accountRepository.findOneByEmail(email);
    }

    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

}
