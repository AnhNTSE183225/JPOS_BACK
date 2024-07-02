package com.fpt.jpos.controller;

import com.fpt.jpos.rollbar.RollbarConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DemoController {

    public final RollbarConfig rollbarConfig;

    @GetMapping("/")
    public String sayHello() {
        // Sends a debug message to your Spring project on Rollbar
        rollbarConfig.rollbar().debug("Here is some debug message");
        return "Hello World!";
    }
}

