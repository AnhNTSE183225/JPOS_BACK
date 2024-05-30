package com.fpt.jpos.repository;

import com.fpt.jpos.pojo.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAccountRepository extends JpaRepository<Account, String> {
    public Account findOneByUsernameAndPassword(String username, String password);
}
