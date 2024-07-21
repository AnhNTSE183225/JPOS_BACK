package com.fpt.jpos.auth.google;

import com.fpt.jpos.repository.IAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final IAccountRepository accountRepository;


}
