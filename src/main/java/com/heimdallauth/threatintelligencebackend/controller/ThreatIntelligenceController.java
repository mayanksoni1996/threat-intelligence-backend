package com.heimdallauth.threatintelligencebackend.controller;

import com.heimdallauth.threatintelligencebackend.constants.PhishingDetectionResult;
import com.heimdallauth.threatintelligencebackend.models.DomainCheckRequest;
import com.heimdallauth.threatintelligencebackend.services.PhishDetectionEngine;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/threat-intelligence")
public class ThreatIntelligenceController {
    private final PhishDetectionEngine phishDetectionEngine;

    public ThreatIntelligenceController(PhishDetectionEngine phishDetectionEngine) {
        this.phishDetectionEngine = phishDetectionEngine;
    }

    @PostMapping("/phish-detection")
    public ResponseEntity<PhishingDetectionResult> phishDetection(@RequestBody DomainCheckRequest domainCheckRequest) {
        return ResponseEntity.ok(this.phishDetectionEngine.calculateEditDistance(domainCheckRequest));
    }
    @GetMapping("/phish-detection")
    public ResponseEntity<PhishingDetectionResult> getPhishDetection(@RequestParam("state") UUID state, @RequestParam("domain") String domain) {
        return ResponseEntity.ok(this.phishDetectionEngine.calculateEditDistance(new DomainCheckRequest(domain, state)));
    }
}
