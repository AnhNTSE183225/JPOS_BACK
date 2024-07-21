package com.fpt.jpos.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
// Binh
public class CloudinaryConfig {

    @Value("${cloudinary.cloud_name}")
    private String CLOUD_NAME;

    @Value("${cloudinary.api_key}")
    private String API_KEY;

    @Value("${cloudinary.cloud_secret}")
    private String CLOUD_SECRET;

    @Bean
    public Cloudinary cloudinary() {

        Map<String, String> config = new HashMap<String, String>();
        config.put("cloud_name", CLOUD_NAME);
        config.put("api_key", API_KEY);
        config.put("api_secret", CLOUD_SECRET);
        return new Cloudinary(config);
    }
}
