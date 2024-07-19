package com.fpt.jpos.service;

import com.fpt.jpos.exception.AccountAlreadyExistsException;
import com.fpt.jpos.pojo.Account;
import com.fpt.jpos.pojo.Staff;
import com.fpt.jpos.repository.IAccountRepository;
import com.fpt.jpos.repository.IStaffRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffService implements IStaffService {

    private final IStaffRepository staffRepository;
    private final IAccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<Staff> findAll() {
        return staffRepository.findAll();
    }

    @Override
    public Staff getStaffByAccount(Account account) {
        return staffRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword());
    }

    @Override
    public List<Staff> getDesignStaff() {
        return staffRepository.findByStaffType("design");
    }

    @Override
    public List<Staff> getSaleStaff() {
        return staffRepository.findByStaffType("sale");
    }

    @Override
    public List<Staff> getProductionStaff() {
        return staffRepository.findByStaffType("produce");
    }

    @Override
    @Transactional
    public Staff createStaff(Staff staff) {
        return staffRepository.save(staff);
    }

    @Override
    @Transactional
    public Staff updateStaff(Staff staff) throws AccountAlreadyExistsException {
        Staff oldStaff = this.staffRepository.findStaffByUsername(staff.getAccount().getUsername());
        if(!oldStaff.getAccount().getEmail().trim().equalsIgnoreCase(staff.getAccount().getEmail().trim())) {
            System.out.println("Old email");
            System.out.println(oldStaff.getAccount().getEmail());
            System.out.println("New email");
            System.out.println(staff.getAccount().getEmail());
            if(this.accountRepository.findOneByEmail(staff.getAccount().getEmail()) != null) {
                throw new AccountAlreadyExistsException();
            }
        }
        if(!staff.getAccount().getPassword().equals(oldStaff.getAccount().getPassword())) {
            staff.getAccount().setPassword(passwordEncoder.encode(staff.getAccount().getPassword()));
        }
        return staffRepository.save(staff);
    }

    @Override
    @Transactional
    public void deleteStaff(Integer staffId) {
        staffRepository.deleteById(staffId);
    }
}
