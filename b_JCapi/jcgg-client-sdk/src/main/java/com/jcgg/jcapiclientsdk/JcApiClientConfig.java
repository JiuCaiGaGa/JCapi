package com.jcgg.jcapiclientsdk;

import com.jcgg.jcapiclientsdk.client.JcApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties("jcgg.client")
@Data
@ComponentScan
public class JcApiClientConfig {

    private String accessKey;

    private String secretKey;

    @Bean
    public JcApiClient jcApiClient() {
        return new JcApiClient(accessKey, secretKey);
    }

}
