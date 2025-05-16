package com.heimdallauth.threatintelligencebackend.repository;

import com.heimdallauth.threatintelligencebackend.entity.TrustedDomain;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrustedDomainRepository extends JpaRepository<TrustedDomain, Long> {
    List<TrustedDomain> findByTldEqualsIgnoreCase(String tld);
    @Query(value = "TRUNCATE TABLE trusted_domain", nativeQuery = true)
    @Modifying
    @Transactional
    void truncateTable();

}
