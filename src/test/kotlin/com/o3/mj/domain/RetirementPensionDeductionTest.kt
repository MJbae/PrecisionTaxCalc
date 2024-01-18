package com.o3.mj.domain

import com.o3.mj.domain.tax.DeductionType
import com.o3.mj.domain.tax.IncomeDeduction
import com.o3.mj.domain.tax.RetirementPensionDeductionService
import com.o3.mj.domain.tax.Tax
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.math.BigDecimal

class RetirementPensionDeductionTest : FunSpec({

    test("퇴직연금세액공제금은 퇴직연금 납입금액의 15%이다") {
        val tax = Tax().apply {
            incomeDeductions = setOf(
                IncomeDeduction(
                    BigDecimal("3000000"),
                    DeductionType.RETIREMENT_PENSION
                )
            )
        }

        val deduction = RetirementPensionDeductionService().calculate(tax)
        deduction shouldBe BigDecimal("450000")
    }

    test("퇴직연금 납입금액이 없다면 퇴직연금세액공제금액은 0원이다") {
        val tax = Tax().apply {
            incomeDeductions = setOf()
        }

        val deduction = RetirementPensionDeductionService().calculate(tax)
        deduction shouldBe BigDecimal("0")
    }
})