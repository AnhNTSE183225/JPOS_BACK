package com.fpt.jpos.auth;

import com.fpt.jpos.dto.AdminRegistrationDTO;
import com.fpt.jpos.dto.CustomerRegistrationDTO;
import com.fpt.jpos.dto.StaffRegistrationDTO;
import com.fpt.jpos.exception.AccountAlreadyExistsException;
import com.fpt.jpos.pojo.Account;
import com.fpt.jpos.pojo.Customer;
import com.fpt.jpos.pojo.Staff;
import com.fpt.jpos.pojo.enums.Provider;
import com.fpt.jpos.pojo.enums.Role;
import com.fpt.jpos.repository.IAccountRepository;
import com.fpt.jpos.repository.ICustomerRepository;
import com.fpt.jpos.repository.IStaffRepository;
import com.fpt.jpos.service.ICustomerService;
import com.fpt.jpos.service.IStaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final IAccountRepository accountRepository;
    private final ICustomerRepository customerRepository;
    private final IStaffRepository staffRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final IStaffService staffService;
    private final ICustomerService customerService;

    //Binh
    public AuthenticationResponse register(CustomerRegistrationDTO request) throws AccountAlreadyExistsException {
        var user = Account.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .status(true)
                .role(Role.customer)
                .provider(Provider.LOCAL)
                .build();
        if (accountRepository.findAccountByUsername(user.getUsername()).isPresent()) {
            throw new AccountAlreadyExistsException();
        }
        user = accountRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        Customer customer = new Customer();
        customer.setAccount(user);
        customer.setName(request.getName());
        customer.setAddress(request.getAddress());
        customer = customerRepository.save(customer);
        return AuthenticationResponse.builder()
                .account(customer)
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse registerStaff(StaffRegistrationDTO staffRegistrationDTO) throws AccountAlreadyExistsException {
        Account user = Account.builder()
                .username(staffRegistrationDTO.getUsername())
                .password(passwordEncoder.encode(staffRegistrationDTO.getPassword()))
                .email(staffRegistrationDTO.getEmail())
                .status(true)
                .role(Role.staff)
                .provider(Provider.LOCAL)
                .build();
        if (accountRepository.findAccountByUsername(user.getUsername()).isPresent()) {
            throw new AccountAlreadyExistsException();
        }
        user = accountRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        Staff staff = Staff.builder()
                .account(user)
                .name(staffRegistrationDTO.getName())
                .phone(staffRegistrationDTO.getPhone())
                .staffType(staffRegistrationDTO.getStaffType())
                .build();
        staff = staffRepository.save(staff);
        return AuthenticationResponse.builder()
                .account(staff)
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse registerAdmin(AdminRegistrationDTO adminRegistrationDTO) throws AccountAlreadyExistsException {
        Account account = Account.builder()
                .username(adminRegistrationDTO.getUsername())
                .password(passwordEncoder.encode(adminRegistrationDTO.getPassword()))
                .email(adminRegistrationDTO.getEmail())
                .status(true)
                .role(Role.admin)
                .provider(Provider.LOCAL)
                .build();
        if (accountRepository.findAccountByUsername(account.getUsername()).isPresent()) {
            throw new AccountAlreadyExistsException();
        }
        account = accountRepository.save(account);
        var jwtToken = jwtService.generateToken(account);
        return AuthenticationResponse.builder()
                .account(account)
                .token(jwtToken)
                .build();
    }

    //Binh
    public AuthenticationResponse authenticate(AuthenticationRequest request) throws AccountNotFoundException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        var user = accountRepository.findAccountByUsername(request.getUsername())
                .orElseThrow();
        if(!user.getStatus()) {
            throw new AccountNotFoundException();
        }
        var jwtToken = jwtService.generateToken(user);
        Object authenticatedUser = null;
        if (user.getRole() == Role.customer) {
            authenticatedUser = customerService.loginCustomer(user);
        } else if (user.getRole() == Role.staff) {
            authenticatedUser = staffService.getStaffByAccount(user);
        } else if (user.getRole() == Role.admin) {
            authenticatedUser = user;
        }
        return AuthenticationResponse.builder()
                .account(authenticatedUser)
                .token(jwtToken)
                .build();
    }


}
