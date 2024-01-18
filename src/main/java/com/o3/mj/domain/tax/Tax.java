package com.o3.mj.domain.tax;

import com.o3.mj.domain.customer.Customer;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Tax {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    private BigDecimal calculatedTaxAmount;
    private BigDecimal totalSalary;

    @ElementCollection
    @CollectionTable(name = "income_deductions", joinColumns = @JoinColumn(name = "tax_id"))
    private Set<IncomeDeduction> incomeDeductions = new HashSet<>();

    public void setTotalSalary(BigDecimal totalSalary) {
        this.totalSalary = totalSalary;
    }

    public void setCalculatedTaxAmount(BigDecimal calculatedTaxAmount) {
        this.calculatedTaxAmount = calculatedTaxAmount;
    }

    public void setIncomeDeductions(Set<IncomeDeduction> incomeDeductions) {
        this.incomeDeductions.clear();
        this.incomeDeductions = incomeDeductions;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public BigDecimal getCalculatedTaxAmount() {
        return this.calculatedTaxAmount;
    }

    public BigDecimal getTotalSalary() {
        return this.totalSalary;
    }

    public Set<IncomeDeduction> getIncomeDeductions() {
        return this.incomeDeductions;
    }
}
