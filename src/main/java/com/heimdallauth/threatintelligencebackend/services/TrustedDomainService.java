package com.heimdallauth.threatintelligencebackend.services;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.heimdallauth.threatintelligencebackend.entity.TrustedDomain;
import com.heimdallauth.threatintelligencebackend.models.TrancoCsvFileFormat;
import com.heimdallauth.threatintelligencebackend.repository.TrustedDomainRepository;
import com.heimdallauth.threatintelligencebackend.utils.DomainUtils;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class TrustedDomainService {
    private final TrustedDomainRepository trustedDomainRepository;
    private static final CsvMapper CSV_MAPPER = new CsvMapper();
    private static final File CSV_FILE;

    static {
        try {
            CSV_FILE = ResourceUtils.getFile("classpath:tranco.csv");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Getter
    private boolean isDataLoading = false;

    public TrustedDomainService(TrustedDomainRepository trustedDomainRepository) {
        this.trustedDomainRepository = trustedDomainRepository;
    }

    @Scheduled(cron = "0 0 0 * * *")
    private void runMidnightMaintenance(){
        long countOfRecords = trustedDomainRepository.count();
        if(countOfRecords > 0){
            log.info("Trusted domain data already loaded, skipping midnight maintenance");
            return;
        }
        log.info("Running midnight maintenance");
        isDataLoading = true;
        //TODO perform Data load
        processTrustedDomainRefresh();
        isDataLoading = false;
    }
     List<TrustedDomain> getTrustedDomainsByTld(String tld){
        return trustedDomainRepository.findByTldEqualsIgnoreCase(tld);
    }

    private void processTrustedDomainRefresh() {
        log.info("Processing trusted domain refresh");
        try{
            File csvFile = CSV_FILE;
            List<TrancoCsvFileFormat> csvFormat = readCsvFile(TrancoCsvFileFormat.class, new BufferedInputStream(new FileInputStream( csvFile )));
            if (csvFormat == null || csvFormat.isEmpty()) {
                log.error("No data found in CSV file");
                return;
            }
            List<TrustedDomain> trustedDomains = csvFormat.stream().map(s -> {
                TrustedDomain trustedDomain = new TrustedDomain();
                trustedDomain.setFullyQualifiedDomainName(s.getDomain());
                trustedDomain.setTld(DomainUtils.getTldFromDomain(s.getDomain()));
                return trustedDomain;
            }).toList();
            this.trustedDomainRepository.saveAll(trustedDomains);
        }catch (Exception e){
            log.error("Error processing trusted domain refresh: {}", e.getMessage());
        }
    }
    private static <T>List<T> readCsvFile(Class<T> clazz, InputStream inputStream) {
        try {
            CsvSchema schema = CsvSchema.emptySchema().withHeader();
            ObjectReader reader = CSV_MAPPER.readerFor(clazz).with(schema);
            return reader.<T>readValues(inputStream).readAll();
        } catch (Exception e) {
            log.error("Error reading CSV file: {}", e.getMessage());
            return null;
        }
    }
}
