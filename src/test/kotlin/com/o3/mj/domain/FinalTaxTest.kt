package com.o3.mj.domain

import com.o3.mj.domain.tax.*
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.math.BigDecimal

class FinalTaxTest : FunSpec({

    test("지구 사는 사이언인 손오공의 결정세액은 675800원이다") {
        val tax = Tax().apply {
            calculatedTaxAmount = BigDecimal("3000000")
            totalSalary = BigDecimal("50000000")
            incomeDeductions = setOf(
                IncomeDeduction(
                    BigDecimal("3000000"),
                    DeductionType.RETIREMENT_PENSION
                ),
                IncomeDeduction(
                    BigDecimal("160000"),
                    DeductionType.INSURANCE
                ),
                IncomeDeduction(
                    BigDecimal("500000"),
                    DeductionType.MEDICAL
                ),
                IncomeDeduction(
                    BigDecimal("300000"),
                    DeductionType.EDUCATION
                ),
                IncomeDeduction(
                    BigDecimal("200000"),
                    DeductionType.DONATION
                )
            )
        }

        val deduction = RetirementPensionDeductionService().calculate(tax)
        val finalTaxAmount = FinalTaxService().calculate(tax, deduction)

        finalTaxAmount shouldBe BigDecimal("675800")
    }

    test("결정세액 계산 결과가 음수라면 0으로 처리한다") {
        val tax = Tax().apply {
            calculatedTaxAmount = BigDecimal("3000000")
            totalSalary = BigDecimal("60000000")
            incomeDeductions = setOf(
                IncomeDeduction(
                    BigDecimal("6000000"),
                    DeductionType.RETIREMENT_PENSION
                ),
                IncomeDeduction(
                    BigDecimal("100000"),
                    DeductionType.INSURANCE
                ),
                IncomeDeduction(
                    BigDecimal("4400000"),
                    DeductionType.MEDICAL
                ),
                IncomeDeduction(
                    BigDecimal("200000"),
                    DeductionType.EDUCATION
                ),
                IncomeDeduction(
                    BigDecimal("150000"),
                    DeductionType.DONATION
                )
            )
        }

        val deduction = RetirementPensionDeductionService().calculate(tax)
        val finalTaxAmount = FinalTaxService().calculate(tax, deduction)

        finalTaxAmount shouldBe BigDecimal("0")
    }

    test("특별세액공제금액 합계가 130,000원 미만이라면, 결정세액 계산에서 표준세액공제금액을 추가로 감산한다") {
        val tax = Tax().apply {
            calculatedTaxAmount = BigDecimal("2000000")
            totalSalary = BigDecimal("40000000")
            incomeDeductions = setOf(
                IncomeDeduction(
                    BigDecimal("2000000"),
                    DeductionType.RETIREMENT_PENSION
                ),
                IncomeDeduction(
                    BigDecimal("0"),
                    DeductionType.MEDICAL
                ),
                IncomeDeduction(
                    BigDecimal("0"),
                    DeductionType.INSURANCE
                ),
                IncomeDeduction(
                    BigDecimal("100000"),
                    DeductionType.EDUCATION
                ),
                IncomeDeduction(
                    BigDecimal("100000"),
                    DeductionType.DONATION
                )
            )
        }

        val deduction = RetirementPensionDeductionService().calculate(tax)
        val finalTaxAmount = FinalTaxService().calculate(tax, deduction)

        finalTaxAmount shouldBe BigDecimal("440000")
    }

    test("특별세액공제금액 합계가 130,000원이라면, 결정세액 계산에서 표준세액공제금액을 추가하지 않는다") {
        val tax = Tax().apply {
            calculatedTaxAmount = BigDecimal("2000000")
            totalSalary = BigDecimal("40000000")
            incomeDeductions = setOf(
                IncomeDeduction(
                    BigDecimal("2000000"),
                    DeductionType.RETIREMENT_PENSION
                ),
                IncomeDeduction(
                    BigDecimal("0"),
                    DeductionType.MEDICAL
                ),
                IncomeDeduction(
                    BigDecimal("0"),
                    DeductionType.INSURANCE
                ),
                IncomeDeduction(
                    BigDecimal("433333.5"),
                    DeductionType.EDUCATION
                ),
                IncomeDeduction(
                    BigDecimal("433333.5"),
                    DeductionType.DONATION
                )
            )
        }

        val deduction = RetirementPensionDeductionService().calculate(tax)
        val finalTaxAmount = FinalTaxService().calculate(tax, deduction)

        finalTaxAmount shouldBe BigDecimal("470000")
    }

    test("특별세액공제금액 합계가 130,000원 초과라면, 결정세액 계산에서 표준세액공제금액을 추가하지 않는다") {
        val tax = Tax().apply {
            calculatedTaxAmount = BigDecimal("2000000")
            totalSalary = BigDecimal("40000000")
            incomeDeductions = setOf(
                IncomeDeduction(
                    BigDecimal("2000000"),
                    DeductionType.RETIREMENT_PENSION
                ),
                IncomeDeduction(
                    BigDecimal("0"),
                    DeductionType.MEDICAL
                ),
                IncomeDeduction(
                    BigDecimal("0"),
                    DeductionType.INSURANCE
                ),
                IncomeDeduction(
                    BigDecimal("440000"),
                    DeductionType.EDUCATION
                ),
                IncomeDeduction(
                    BigDecimal("440000"),
                    DeductionType.DONATION
                )
            )
        }

        val deduction = RetirementPensionDeductionService().calculate(tax)
        val finalTaxAmount = FinalTaxService().calculate(tax, deduction)

        finalTaxAmount shouldBe BigDecimal("468000")
    }
})