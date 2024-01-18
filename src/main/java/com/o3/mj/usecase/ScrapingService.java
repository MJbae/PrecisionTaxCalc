package com.o3.mj.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.o3.mj.adapter.in.ExceptionControllerAdvice;
import com.o3.mj.domain.Customer;
import com.o3.mj.domain.ResidentId;
import com.o3.mj.usecase.dto.ScrapingResponse;
import com.o3.mj.usecase.dto.ScrapingRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ScrapingService {

    private final RestTemplate restTemplate;
    private final String scrapingUrl = "https://codetest.3o3.co.kr/v2/scrap";
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final Logger logger = LoggerFactory.getLogger(ScrapingService.class);

    public ScrapingService() {
        this.restTemplate = new RestTemplate();
    }

    public ScrapingResponse scrapTaxData(String name, String residentId) {
        logger.info("scrap tax data, customer name: " + name);

        try {
            ScrapingRequest request = new ScrapingRequest(name, residentId);
            ResponseEntity<String> response = restTemplate.postForEntity(scrapingUrl, request, String.class);
            return objectMapper.readValue(response.getBody(), ScrapingResponse.class);
        } catch (Exception e) {
            throw new RuntimeException("Fail to scraping tax data", e);
        }
    }
}
