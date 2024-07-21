package com.fpt.jpos.controller;

import com.fpt.jpos.auth.AuthenticationResponse;
import com.fpt.jpos.auth.AuthenticationService;
import com.fpt.jpos.dto.StaffRegistrationDTO;
import com.fpt.jpos.exception.AccountAlreadyExistsException;
import com.fpt.jpos.pojo.Staff;
import com.fpt.jpos.pojo.enums.StaffType;
import com.fpt.jpos.service.IStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class StaffController {

    private final IStaffService staffService;
    private final AuthenticationService authenticationService;

    @Autowired
    public StaffController(IStaffService staffService, AuthenticationService authenticationService) {
        this.staffService = staffService;
        this.authenticationService = authenticationService;
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
    public ResponseEntity<AuthenticationResponse> createStaff(@RequestBody StaffRegistrationDTO request) {
        ResponseEntity<AuthenticationResponse> response;

        try {
            response = ResponseEntity.ok(authenticationService.registerStaff(request));
        } catch (AccountAlreadyExistsException ex) {
            response = ResponseEntity.status(409).build();
        }

        return response;
    }

    @PreAuthorize("hasAuthority('admin')")
    @PutMapping("/staff/update")
    public ResponseEntity<?> updateStaff(@RequestBody Staff staff) {
        ResponseEntity<?> response;

        try {
            response = ResponseEntity.ok(staffService.updateStaff(staff));
        } catch (AccountAlreadyExistsException ex) {
            response = ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            response = ResponseEntity.status(403).build();
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

    @GetMapping("/staff/get-staff-types")
    public ResponseEntity<?> getStaffTypes() {
        ResponseEntity<?> response;

        try {
            response = ResponseEntity.ok(StaffType.sale.getDeclaringClass().getEnumConstants());
        } catch (Exception e) {
            e.printStackTrace();
            response = ResponseEntity.status(400).build();
        }
        return response;
    }
}
