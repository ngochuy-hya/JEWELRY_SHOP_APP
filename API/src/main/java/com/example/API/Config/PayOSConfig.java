package com.example.API.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import vn.payos.PayOS;


@Configuration
public class PayOSConfig {
    @Bean
    public PayOS payOS() {
        return new PayOS("49802f00-eb0b-46ee-89be-2ee0b1b511c5", "35e34d08-f254-41e8-870b-c62a516aeb34", "46332b90287383025aa8639351899c8a253e8656c8b31ca34f1bb38f617e96b1");
    }
}