package com.fpt.jpos.repository;

import com.fpt.jpos.pojo.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICustomerRepository extends CrudRepository<Customer, Integer> {

    @Query(value = """
            SELECT * FROM Customer
            WHERE username = ?1
            """, nativeQuery = true)
    Customer findByUsername(String username);
}
