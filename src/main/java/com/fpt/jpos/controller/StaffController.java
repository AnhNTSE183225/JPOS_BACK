package com.fpt.jpos.controller;

import com.fpt.jpos.pojo.Account;
import com.fpt.jpos.pojo.Staff;
import com.fpt.jpos.service.IStaffService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class StaffController {

    private final IStaffService staffService;

    @Autowired
    public StaffController(IStaffService staffService) {
        this.staffService = staffService;
    }

    @CrossOrigin
    @PostMapping("/staff-login")
    public ResponseEntity<?> staffLogin(@RequestBody Account account) {
        try {
            Staff staff = staffService.getStaffByAccount(account);
            return ResponseEntity.ok(staff);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error " + ex.getMessage());
        }
    }
    @GetMapping("/staff-design")
    public ResponseEntity<List<Staff>> getDesignStaff() {
        List<Staff> staffs = staffService.getDesignStaff();
        return ResponseEntity.ok(staffs);
    }

    @GetMapping("/staff-sale")
    public ResponseEntity<List<Staff>> getSaleStaff() {
        List<Staff> staffs = staffService.getSaleStaff();
        return ResponseEntity.ok(staffs);
    }

    @GetMapping("/staff-production")
    public ResponseEntity<List<Staff>> getProductionStaff() {
        List<Staff> staffs = staffService.getProductionStaff();
        return ResponseEntity.ok(staffs);
    }
}
