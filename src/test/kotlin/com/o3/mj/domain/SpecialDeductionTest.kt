package com.o3.mj.domain

import com.o3.mj.domain.customer.Customer
import com.o3.mj.domain.tax.DeductionType
import com.o3.mj.domain.tax.IncomeDeduction
import com.o3.mj.domain.tax.SpecialDeductionService
import com.o3.mj.domain.tax.Tax
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.math.BigDecimal

class SpecialDeductionTest : FunSpec({

    test("의료비공제금액 계산 결과가 음수라면 0원으로 처리한다") {
        val incomeDeductions = setOf(IncomeDeduction(BigDecimal("10000"), DeductionType.MEDICAL))
        val tax = Tax(BigDecimal("2000000"), BigDecimal("40000000"), incomeDeductions, Customer())

        val deduction = SpecialDeductionService().calculate(tax)

        deduction shouldBe BigDecimal("0")
    }

    test("보험료공제금액은 보험료 납입금액의 12%이다") {
        val incomeDeductions = setOf(IncomeDeduction(BigDecimal("10000000"), DeductionType.INSURANCE))
        val tax = Tax(BigDecimal("2000000"), BigDecimal("40000000"), incomeDeductions, Customer())

        val deduction = SpecialDeductionService().calculate(tax)

        deduction shouldBe BigDecimal("1200000")
    }

    test("교육비공제금액은 교육비 납입금액의 15%이다") {
        val incomeDeductions = setOf(IncomeDeduction(BigDecimal("10000000"), DeductionType.EDUCATION))
        val tax = Tax(BigDecimal("2000000"), BigDecimal("40000000"), incomeDeductions, Customer())

        val deduction = SpecialDeductionService().calculate(tax)

        deduction shouldBe BigDecimal("1500000")
    }

    test("기부금공제금액은 기부금 납입금액의 15%이다") {
        val incomeDeductions = setOf(IncomeDeduction(BigDecimal("10000000"), DeductionType.DONATION))
        val tax = Tax(BigDecimal("2000000"), BigDecimal("40000000"), incomeDeductions, Customer())

        val deduction = SpecialDeductionService().calculate(tax)

        deduction shouldBe BigDecimal("1500000")
    }
})
