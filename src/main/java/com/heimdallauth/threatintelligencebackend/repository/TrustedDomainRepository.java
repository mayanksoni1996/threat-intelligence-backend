package com.heimdallauth.threatintelligencebackend.repository;

import com.heimdallauth.threatintelligencebackend.entity.TrustedDomain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrustedDomainRepository extends JpaRepository<TrustedDomain, Long> {
    List<TrustedDomain> findByTldEqualsIgnoreCase(String tld);
}
