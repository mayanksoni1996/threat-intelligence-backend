package com.heimdallauth.threatintelligencebackend.services;

import com.heimdallauth.threatintelligencebackend.algo.EditDistance;
import com.heimdallauth.threatintelligencebackend.constants.PhishingDetectionResult;
import com.heimdallauth.threatintelligencebackend.entity.TrustedDomain;
import com.heimdallauth.threatintelligencebackend.models.DomainCheckRequest;
import com.heimdallauth.threatintelligencebackend.utils.DomainUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PhishDetectionEngine {
    //TODO Replace with cache
    private final TrustedDomainService trustedDomainService;

    public PhishDetectionEngine(TrustedDomainService trustedDomainService) {
        this.trustedDomainService = trustedDomainService;
    }

    public PhishingDetectionResult calculateEditDistance(DomainCheckRequest domainCheckRequest){
        List<TrustedDomain> trustedDomainsByMatchingTld = trustedDomainService.getTrustedDomainsByTld(DomainUtils.getTldFromDomain(domainCheckRequest.domainUnderCheck()));
        List<Integer> editDistanceLists = new ArrayList<>();
        String domainUnderTest = domainCheckRequest.domainUnderCheck().toLowerCase();
        for(TrustedDomain domain : trustedDomainsByMatchingTld){
            editDistanceLists.add(EditDistance.calculateEditDistance(domainUnderTest, domain.getFullyQualifiedDomainName()));
        }
        editDistanceLists.sort(Integer::compareTo);
        int minEditDistance = editDistanceLists.getFirst();
        if(minEditDistance == 0){
            return PhishingDetectionResult.NOT_PHISHING;
        }else if(minEditDistance <= 2){
            return PhishingDetectionResult.POSSIBLE_PHISHING;
        }else{
            return PhishingDetectionResult.NOT_PHISHING;
        }
    }
}
