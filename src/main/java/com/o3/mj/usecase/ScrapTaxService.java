package com.o3.mj.usecase;

import com.o3.mj.adapter.out.CustomerRepository;
import com.o3.mj.adapter.out.TaxRepository;
import com.o3.mj.domain.Customer;
import com.o3.mj.domain.CustomerId;
import com.o3.mj.domain.Tax;
import com.o3.mj.domain.TaxMapper;
import com.o3.mj.usecase.dto.CustomerQuery;
import com.o3.mj.usecase.dto.ScrapingResponse;
import com.o3.mj.usecase.exception.NotRegisteredCustomerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ScrapTaxService {
    private final CustomerRepository repository;
    private final TaxRepository taxRepository;
    private final ScrapingService scrapingService;
    private final TaxMapper mapper = new TaxMapper();


    public ScrapTaxService(CustomerRepository repository, TaxRepository taxRepository, ScrapingService scrapingService) {
        this.repository = repository;
        this.taxRepository = taxRepository;
        this.scrapingService = scrapingService;
    }

    public void scrap(CustomerQuery query) {
        Optional<Customer> customer = repository.findById(new CustomerId(query.getCustomerId()));
        if (customer.isEmpty()) {
            throw new NotRegisteredCustomerException(query.getCustomerId());
        }

        ScrapingResponse response = scrapingService.scrapTaxData(customer.get().getName(), customer.get().getOriginResidentId());

        mapAndSave(response, customer.get());
    }

    @Transactional
    private void mapAndSave(ScrapingResponse response, Customer customer) {
        Tax tax = mapper.from(response, customer);
        taxRepository.save(tax);
    }

}
