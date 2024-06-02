package com.fpt.jpos.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

//    private final String CLOUD_NAME = "jpos";
//    private final String API_KEY = "857811913941645";
//    private final String CLOUD_SECRET = "XbYP9Bq9kL22ct5fN26hSRKLG0Q";

    private final String CLOUD_NAME = "dbfbigo0e";
    private final String API_KEY = "688665833111795";
    private final String CLOUD_SECRET = "vQk0bpDr6dcvaaKFB9hPYMwjhcc";

    @Bean
    public Cloudinary cloudinary() {

        Map<String, String> config = new HashMap<String, String>();
        config.put("cloud_name", CLOUD_NAME);
        config.put("api_key", API_KEY);
        config.put("api_secret", CLOUD_SECRET);
        return new Cloudinary(config);
    }
}
