package com.fpt.jpos.repository;

import com.fpt.jpos.pojo.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountRepository extends JpaRepository<Account, String> {
    public Account findOneByUsernameAndPassword(String username, String password);
}
