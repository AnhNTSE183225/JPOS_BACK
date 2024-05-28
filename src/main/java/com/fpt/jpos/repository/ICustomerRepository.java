package com.fpt.jpos.repository;

import com.fpt.jpos.pojo.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICustomerRepository extends CrudRepository<Customer, Integer> {
    Customer findByUsername(String username);
}
