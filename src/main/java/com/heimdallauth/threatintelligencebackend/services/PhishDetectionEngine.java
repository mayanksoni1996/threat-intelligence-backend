package com.heimdallauth.threatintelligencebackend.services;

import com.heimdallauth.threatintelligencebackend.algo.EditDistance;
import com.heimdallauth.threatintelligencebackend.models.DomainCheckRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PhishDetectionEngine {
    //TODO Replace with cache
    private static final List<String> KNOWN_DOMAINS = List.of(
            "google.com",
            "facebook.com",
            "amazon.com"
    );
    public void calculateEditDistance(DomainCheckRequest domainCheckRequest){
        List<Integer> editDistanceLists = new ArrayList<>();
        String domainUnderTest = domainCheckRequest.domainUnderCheck();
        for(String domain : KNOWN_DOMAINS){
            editDistanceLists.add(EditDistance.calculateEditDistance(domainUnderTest, domain));
        }
        log.info("EditDistance calculated: " + editDistanceLists);
    }
}
