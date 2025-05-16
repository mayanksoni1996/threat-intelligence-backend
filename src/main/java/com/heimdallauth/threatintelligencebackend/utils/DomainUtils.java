package com.heimdallauth.threatintelligencebackend.utils;

public class DomainUtils {
    public static String getTldFromDomain(String domain) {
        if (domain == null || !domain.contains(".")) {
            throw new IllegalArgumentException("Invalid domain name");
        }
        String[] parts = domain.split("\\.");
        return parts[parts.length - 1];
    }
}