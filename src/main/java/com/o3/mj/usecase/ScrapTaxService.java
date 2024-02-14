package com.o3.mj.usecase;

import com.o3.mj.repository.CustomerRepository;
import com.o3.mj.repository.TaxRepository;
import com.o3.mj.domain.customer.Customer;
import com.o3.mj.domain.customer.CustomerId;
import com.o3.mj.domain.tax.Tax;
import com.o3.mj.domain.tax.TaxMapper;
import com.o3.mj.usecase.dto.ScrapCommand;
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

    public void scrap(ScrapCommand command) {
        Customer customer = repository.findById(new CustomerId(command.getCustomerId()))
                .orElseThrow(() -> new NotRegisteredCustomerException(command.getCustomerId()));

        ScrapingResponse response = scrapingService.scrapTaxData(customer.getName(), customer.getOriginResidentId());

        mapAndSave(response, customer);
    }

    @Transactional
    private void mapAndSave(ScrapingResponse response, Customer customer) {
        Tax newTax = mapper.from(response, customer);
        Optional<Tax> prevTax = taxRepository.findByCustomer(customer);

        if (prevTax.isPresent() && newTax.hasSameValue(prevTax.get())){
            return;
        }

        if (prevTax.isEmpty()){
            taxRepository.save(newTax);
            return;
        }

        prevTax.get().replace(newTax);
        taxRepository.save(prevTax.get());
    }
}
