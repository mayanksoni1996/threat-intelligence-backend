package com.heimdallauth.threatintelligencebackend.controller;

import com.heimdallauth.threatintelligencebackend.models.DomainCheckRequest;
import com.heimdallauth.threatintelligencebackend.services.PhishDetectionEngine;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/threat-intelligence")
public class ThreatIntelligenceController {
    private final PhishDetectionEngine phishDetectionEngine;

    public ThreatIntelligenceController(PhishDetectionEngine phishDetectionEngine) {
        this.phishDetectionEngine = phishDetectionEngine;
    }

    @PostMapping("/phish-detection")
    public ResponseEntity<Void> phishDetection(@RequestBody DomainCheckRequest domainCheckRequest) {
        this.phishDetectionEngine.calculateEditDistance(domainCheckRequest);
        return ResponseEntity.ok().build();
    }
}
