package com.fpt.jpos.rollbar;

import com.rollbar.notifier.Rollbar;
import com.rollbar.notifier.config.Config;
import com.rollbar.notifier.config.ConfigBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration()
@EnableWebMvc
@ComponentScan({
        // ADD YOUR PROJECT PACKAGE HERE
        "com.fpt.jpos.rollbar"
})
public class RollbarConfig {

    /**
     * Register a Rollbar bean to configure App with Rollbar.
     */
    @Bean
    public Rollbar rollbar() {

        return new Rollbar(getRollbarConfigs("d5d3cf3e238d41589cac0811e5106129"));
    }

    private Config getRollbarConfigs(String accessToken) {

        // Reference ConfigBuilder.java for all the properties you can set for Rollbar
        return ConfigBuilder.withAccessToken(accessToken)
                .environment("development")
                .codeVersion("1.0.0")
                .build();
    }



}