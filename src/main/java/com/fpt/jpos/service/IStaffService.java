package com.fpt.jpos.service;

import com.fpt.jpos.pojo.Account;
import com.fpt.jpos.pojo.Staff;

public interface IStaffService {
    public Staff getStaffByAccount(Account account);
}
