package com.o3.mj.domain

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class ResidentIdTest : FunSpec({
    test("ResidentId 생성 시 원본값을 암호화한다") {
        val original = "MyResidentId"

        ResidentId(original) shouldNotBe original
    }


    test("변경한 원본값은 복호화할 수 있다") {
        val original = "MyResidentId"

        val residentId = ResidentId(original)

        residentId.toOriginal() shouldBe original
    }


})
