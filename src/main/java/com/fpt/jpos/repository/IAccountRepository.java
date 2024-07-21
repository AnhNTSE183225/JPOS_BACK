package com.fpt.jpos.repository;

import com.fpt.jpos.pojo.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAccountRepository extends JpaRepository<Account, String> {

    Optional<Account> findAccountByUsername(String username);

    Account findOneByEmail(String email);
}
