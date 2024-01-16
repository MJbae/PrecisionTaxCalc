package com.o3.mj.domain

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class PasswordTest : FunSpec({
    test("비밀번호 일치여부 확인 시, 비밀번호 평문과 동일한 평문이라면 일치한다") {
        val original = "TestPassword123"

        val password = Password(original)

        password.isMatched(original) shouldBe true
    }

    test("비밀번호 일치여부 확인 시, 비밀번호 평문과 동일하지 않은 평문이라면 불일치한다") {
        val original = "TestPassword123"
        val notOriginal = "DifferentPassword"

        val password = Password(original)

        password.isMatched(notOriginal) shouldBe false
    }
})
