package com.heimdallauth.threatintelligencebackend.services;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

@Service
@Slf4j
public class TrancoListDownloader {
    private static final String TRANCO_LIST_ID = "V9WWN"; // Replace with latest list ID
    private static final String TRANCO_URL = "https://tranco-list.eu/download/" + TRANCO_LIST_ID + "/csv";
    static final Path TRANCO_DEST = Paths.get(System.getProperty("user.dir"), "tranco_top_1m.csv");

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
}
