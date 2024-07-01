package com.fpt.jpos.controller;

import com.fpt.jpos.pojo.Staff;
import com.fpt.jpos.service.IStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class StaffController {

    private final IStaffService staffService;

    @Autowired
    public StaffController(IStaffService staffService) {
        this.staffService = staffService;
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

    @GetMapping("/staff/find-all")
    public ResponseEntity<?> findAllStaff() {
        ResponseEntity<?> response = ResponseEntity.noContent().build();

        try {
            response = ResponseEntity.ok(staffService.findAll());
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }

        return response;
    }
}
