package com.heimdallauth.threatintelligencebackend;

import com.heimdallauth.threatintelligencebackend.config.ThreatIntelConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({ThreatIntelConfig.class})
public class ThreatIntelligenceBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThreatIntelligenceBackendApplication.class, args);
    }

}
