package com.fpt.jpos.repository;

import com.fpt.jpos.pojo.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAccountRepository extends JpaRepository<Account, String> {
    Account findOneByUsernameAndPassword(String username, String password);

    @Query(value = "SELECT * FROM Account WHERE username = ?1 and status = 1",nativeQuery = true)
    Optional<Account> findByUsername(String username);
}
