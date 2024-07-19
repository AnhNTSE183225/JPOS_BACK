package com.fpt.jpos.service;

import com.fpt.jpos.exception.AccountAlreadyExistsException;
import com.fpt.jpos.pojo.Account;
import com.fpt.jpos.pojo.Staff;

import java.util.List;

public interface IStaffService {

    List<Staff> findAll();

    Staff getStaffByAccount(Account account);

    List<Staff> getDesignStaff();

    List<Staff> getSaleStaff();

    List<Staff> getProductionStaff();

    Staff createStaff(Staff staff);

    Staff updateStaff(Staff staff) throws AccountAlreadyExistsException;

    void deleteStaff(Integer staffId);
}
