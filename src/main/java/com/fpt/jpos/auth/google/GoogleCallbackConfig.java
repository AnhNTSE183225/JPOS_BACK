package com.fpt.jpos.auth.google;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class GoogleCallbackConfig {

    @Value("${google.callback.url}")
    private String googleCallbackUrl;

}
