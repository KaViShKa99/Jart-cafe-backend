package com.example.jart_cafe.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.stripe.Stripe;

@Configuration
public class StripeConfig {
    @Value("${stripe.apiKey}")
    private String apiKey;

    @PostConstruct
    public void configureStripe() {
        Stripe.apiKey = apiKey;
    }

}