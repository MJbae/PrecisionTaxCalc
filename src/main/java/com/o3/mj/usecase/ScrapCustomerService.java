package com.o3.mj.usecase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.o3.mj.adapter.out.CustomerRepository;
import com.o3.mj.adapter.out.TaxRepository;
import com.o3.mj.domain.Customer;
import com.o3.mj.domain.CustomerId;
import com.o3.mj.domain.Tax;
import com.o3.mj.domain.TaxMapper;
import com.o3.mj.usecase.dto.CustomerQuery;
import com.o3.mj.usecase.dto.ScrapedResponse;
import com.o3.mj.usecase.dto.ScrapingRequest;
import com.o3.mj.usecase.exception.NotRegisteredCustomerException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class ScrapCustomerService {
    private final CustomerRepository repository;
    private final TaxRepository taxRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final TaxMapper mapper = new TaxMapper();

    public ScrapCustomerService(CustomerRepository repository, TaxRepository taxRepository) {
        this.repository = repository;
        this.taxRepository = taxRepository;
    }

    public void scrap(CustomerQuery query) throws HttpClientErrorException {
        Optional<Customer> customer = repository.findById(new CustomerId(query.getCustomerId()));
        if (customer.isEmpty()) {
            throw new NotRegisteredCustomerException(query.getCustomerId());
        }

        RestTemplate restTemplate = new RestTemplate();
        String url = "https://codetest.3o3.co.kr/v2/scrap";

        ResponseEntity<String> response = restTemplate.postForEntity(url, new ScrapingRequest().from(customer.get()), String.class);
        mapAndSave(response, customer.get());
    }

    @Transactional
    private void mapAndSave(ResponseEntity<String> response, Customer customer){
        try {
            ScrapedResponse scrapedResponse = objectMapper.readValue(response.getBody(), ScrapedResponse.class);
            Tax tax = mapper.from(scrapedResponse, customer);
            taxRepository.save(tax);
        }catch (Exception e){
            throw new RuntimeException("Fail to mapping tax entity", e);
        }
    }

}
