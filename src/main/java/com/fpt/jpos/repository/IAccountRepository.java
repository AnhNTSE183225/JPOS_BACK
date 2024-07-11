package com.fpt.jpos.repository;

import com.fpt.jpos.pojo.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAccountRepository extends JpaRepository<Account, String> {

    @Query(value = "SELECT * FROM Account WHERE username = ?1 and status = 1 and provider = 'LOCAL'", nativeQuery = true)
    Optional<Account> findByUsername(String username);

    Account findOneByEmail(String email);
}
