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
    private static final String TRANCO_LIST_ID = "V9WWN"; // Replace with latest list ID
    private static final String TRANCO_URL = "https://tranco-list.eu/download/" + TRANCO_LIST_ID + "/csv";
    private final Path TRANCO_DEST;

    public TrancoListDownloader(ThreatIntelConfig threatIntelConfig) {
        this.TRANCO_DEST = Paths.get(threatIntelConfig.getTrancoListPath(), "tranco_top_1m.csv");
    }

    @PostConstruct
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
