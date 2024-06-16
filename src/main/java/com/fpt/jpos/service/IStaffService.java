package com.fpt.jpos.service;

import java.util.List;

import com.fpt.jpos.pojo.Account;
import com.fpt.jpos.pojo.Staff;

public interface IStaffService {
    public Staff getStaffByAccount(Account account);
    List<Staff> getDesignStaff();

    List<Staff> getSaleStaff();

    List<Staff> getProductionStaff();
}
