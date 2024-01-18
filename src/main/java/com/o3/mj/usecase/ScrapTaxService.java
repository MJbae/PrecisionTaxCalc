package com.o3.mj.usecase;

import com.o3.mj.adapter.out.CustomerRepository;
import com.o3.mj.adapter.out.TaxRepository;
import com.o3.mj.domain.Customer;
import com.o3.mj.domain.CustomerId;
import com.o3.mj.domain.Tax;
import com.o3.mj.domain.TaxMapper;
import com.o3.mj.usecase.dto.ScrapCommand;
import com.o3.mj.usecase.dto.ScrapingResponse;
import com.o3.mj.usecase.exception.NotRegisteredCustomerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public void scrap(ScrapCommand command) {
        Customer customer = repository.findById(new CustomerId(command.getCustomerId()))
                .orElseThrow(() -> new NotRegisteredCustomerException(command.getCustomerId()));

        ScrapingResponse response = scrapingService.scrapTaxData(customer.getName(), customer.getOriginResidentId());

        mapAndSave(response, customer);
    }

    @Transactional
    private void mapAndSave(ScrapingResponse response, Customer customer) {
        Tax tax = mapper.from(response, customer);
        taxRepository.save(tax);
    }
}
