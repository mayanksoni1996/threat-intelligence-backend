package com.heimdallauth.threatintelligencebackend.services;

import com.heimdallauth.threatintelligencebackend.config.ThreatIntelConfig;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
public class TrancoListDownloader {
    private static final String TRANCO_URL = "https://minio-console-prod.ap-west-1.heimdallauth.com/api/v1/download-shared-object/aHR0cHM6Ly9taW5pby1wcm9kLmFwLXdlc3QtMS5oZWltZGFsbGF1dGguY29tL3RydXN0ZWQtZG9tYWluLXB1YmxpYy90cmFuY29fdG9wXzFtLmNzdj9YLUFtei1BbGdvcml0aG09QVdTNC1ITUFDLVNIQTI1NiZYLUFtei1DcmVkZW50aWFsPTQzNFAyQVZZSDNFV0tZRjc5R0M3JTJGMjAyNTA1MTYlMkZ1cy1lYXN0LTElMkZzMyUyRmF3czRfcmVxdWVzdCZYLUFtei1EYXRlPTIwMjUwNTE2VDA2MjAxMlomWC1BbXotRXhwaXJlcz00MzIwMCZYLUFtei1TZWN1cml0eS1Ub2tlbj1leUpoYkdjaU9pSklVelV4TWlJc0luUjVjQ0k2SWtwWFZDSjkuZXlKaFkyTmxjM05MWlhraU9pSTBNelJRTWtGV1dVZ3pSVmRMV1VZM09VZEROeUlzSW1WNGNDSTZNVGMwTnpReE9USXpNQ3dpY0dGeVpXNTBJam9pWjJWT1dUbEpjVWRvUjNwS2NHRklWQ0o5LnQ4M0hrRGJGUWZKanVKU3pNdE41M3pYalpUZjNsSmJkN0Q4SUxVSUZ0Y0xjSlJGemNSVlVjb2NXdERtMWhSUWNPakViZnU5RGU5Y2RhOWswNC1COUFBJlgtQW16LVNpZ25lZEhlYWRlcnM9aG9zdCZ2ZXJzaW9uSWQ9OWY3YTU0M2QtNTA0Ny00MjJjLTgxM2MtY2M3YmI4YTZkMTIzJlgtQW16LVNpZ25hdHVyZT02ZTY5ZDUyYWQ5NTM1ZDZkNTM5ZTc1NTI2NTU0OTljOTY1YjQzMTA1NzI4ZGI1NGMxNzgzZDdmNThhYzBhODc1";
    private final Path TRANCO_DEST;

    public TrancoListDownloader(ThreatIntelConfig threatIntelConfig) {
        this.TRANCO_DEST = Paths.get(threatIntelConfig.getTrancoListPath(), "tranco_top_1m.csv");
    }

    public void downloadTrancoList() {
        try (BufferedInputStream in = new BufferedInputStream(URI.create(TRANCO_URL).toURL().openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(TRANCO_DEST.toFile())) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
            log.info("✅ Tranco list downloaded successfully.");
        } catch (IOException e) {
            log.error("❌ Failed to download Tranco list: " + e.getMessage());
        }
    }
    public InputStream readFile() throws IOException {
        return Files.newInputStream(TRANCO_DEST);
    }
}
