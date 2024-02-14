package com.o3.mj.domain.tax;

import com.o3.mj.domain.customer.Customer;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@NoArgsConstructor
public class Tax {
    @Id
    @TableGenerator(name = "TaxIdGenerator", table = "sequence", allocationSize = 10)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TaxIdGenerator")
    private Long id;

    @OneToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    private BigDecimal calculatedTaxAmount;
    private BigDecimal totalSalary;

    @ElementCollection
    @CollectionTable(name = "income_deductions", joinColumns = @JoinColumn(name = "tax_id"))
    private Set<IncomeDeduction> incomeDeductions = new HashSet<>();

    public Tax(BigDecimal calculatedTaxAmount, BigDecimal totalSalary, Set<IncomeDeduction> incomeDeductions, Customer customer) {
        this.calculatedTaxAmount = calculatedTaxAmount;
        this.totalSalary = totalSalary;
        this.incomeDeductions = incomeDeductions;
        this.customer = customer;
    }

    public BigDecimal getCalculatedTaxAmount() {
        return this.calculatedTaxAmount;
    }

    public BigDecimal getTotalSalary() {
        return this.totalSalary;
    }

    public BigDecimal getDeductionAmount(DeductionType type) {
        return this.incomeDeductions.stream()
                .filter(d -> d.getDeductionType() == type)
                .map(IncomeDeduction::getAmount)
                .findFirst()
                .orElse(BigDecimal.ZERO);
    }

    public boolean hasSameValue(Tax tax) {
        if (this == tax) return true;
        if (tax == null) return false;

        return Objects.equals(calculatedTaxAmount, tax.calculatedTaxAmount)
                && Objects.equals(totalSalary, tax.totalSalary)
                && Objects.equals(customer, tax.customer)
                && incomeDeductions.equals(tax.incomeDeductions);
    }

    public void replace(Tax newTax) {
        this.calculatedTaxAmount = newTax.getCalculatedTaxAmount();
        this.totalSalary = newTax.getTotalSalary();
        this.incomeDeductions.clear();
        this.incomeDeductions.addAll(newTax.incomeDeductions);
    }
}
