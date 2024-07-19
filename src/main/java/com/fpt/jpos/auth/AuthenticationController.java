package com.fpt.jpos.auth;

import com.fpt.jpos.dto.AdminRegistrationDTO;
import com.fpt.jpos.dto.CustomerRegistrationDTO;
import com.fpt.jpos.dto.StaffRegistrationDTO;
import com.fpt.jpos.exception.AccountAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody CustomerRegistrationDTO request
    ) {
        ResponseEntity<AuthenticationResponse> response;
        try {
            response = ResponseEntity.ok(authenticationService.register(request));
        } catch (AccountAlreadyExistsException ex) {
            response = ResponseEntity.status(409).build();
        }

        return response;
    }

    @PostMapping("/staff-register")
    public ResponseEntity<AuthenticationResponse> staffRegister(@RequestBody StaffRegistrationDTO request) {
        ResponseEntity<AuthenticationResponse> response;

        try {
            response = ResponseEntity.ok(authenticationService.registerStaff(request));
        } catch (AccountAlreadyExistsException ex) {
            response = ResponseEntity.status(409).build();
        }

        return response;
    }

    @PostMapping("/admin-register")
    public ResponseEntity<AuthenticationResponse> adminRegister(@RequestBody AdminRegistrationDTO request) {
        ResponseEntity<AuthenticationResponse> response;

        try {
            response = ResponseEntity.ok(authenticationService.registerAdmin(request));
        } catch (AccountAlreadyExistsException ex) {
            response = ResponseEntity.status(409).build();
        }

        return response;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        ResponseEntity<?> response;
        try {
            response = ResponseEntity.ok(authenticationService.authenticate(request));
        } catch (Exception ex) {
            response = ResponseEntity.status(402).build();
        }
        return response;
    }

}
