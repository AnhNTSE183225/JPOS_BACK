package com.fpt.jpos.controller;

import com.fpt.jpos.pojo.Staff;
import com.fpt.jpos.service.IStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    @PreAuthorize("hasAuthority('staff') or hasAuthority('admin')")
    public ResponseEntity<List<Staff>> getDesignStaff() {
        List<Staff> staffs = staffService.getDesignStaff();
        return ResponseEntity.ok(staffs);
    }

    @GetMapping("/staff-sale")
    @PreAuthorize("hasAuthority('staff') or hasAuthority('admin')")
    public ResponseEntity<List<Staff>> getSaleStaff() {
        List<Staff> staffs = staffService.getSaleStaff();
        return ResponseEntity.ok(staffs);
    }

    @GetMapping("/staff-production")
    @PreAuthorize("hasAuthority('staff') or hasAuthority('admin')")
    public ResponseEntity<List<Staff>> getProductionStaff() {
        List<Staff> staffs = staffService.getProductionStaff();
        return ResponseEntity.ok(staffs);
    }

    @GetMapping("/staff/find-all")
    @PreAuthorize("hasAuthority('staff') or hasAuthority('admin')")
    public ResponseEntity<?> findAllStaff() {
        ResponseEntity<?> response = ResponseEntity.noContent().build();

        try {
            response = ResponseEntity.ok(staffService.findAll());
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }

        return response;
    }

    @PostMapping("/staff/create")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> createStaff(@RequestBody Staff staff) {
        ResponseEntity<?> response = ResponseEntity.noContent().build();

        try {
            response = ResponseEntity.ok(staffService.createStaff(staff));
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }

        return response;
    }

    @PutMapping("/staff/update")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> updateStaff(@RequestBody Staff staff) {
        System.out.println(staff);
        ResponseEntity<?> response = ResponseEntity.noContent().build();

        try {
            response = ResponseEntity.ok(staffService.updateStaff(staff));
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }

        return response;
    }

    @DeleteMapping("/staff/delete/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> deleteStaff(@PathVariable int id) {
        ResponseEntity<?> response = ResponseEntity.noContent().build();

        try {
            staffService.deleteStaff(id);
            response = ResponseEntity.ok().build();
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }

        return response;
    }
}
