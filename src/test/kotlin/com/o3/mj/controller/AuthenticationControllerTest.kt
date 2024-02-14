package com.o3.mj.controller

import com.o3.mj.controller.dto.LogInRequest
import com.o3.mj.controller.dto.SignUpRequest
import com.o3.mj.repository.CustomerRepository
import io.kotest.core.spec.style.FunSpec
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthenticationControllerTest(
    private val testClient: WebTestClient,
    private val repository: CustomerRepository
) : FunSpec({

    beforeTest {
        repository.deleteAll()
    }

    test("화이트 리스트에 포함된 정보로 회원가입 할 수 있다") {
        val req = SignUpRequest("tester", "password", "손오공", "820326-2715702")

        testClient.post().uri("/szs/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(req)
            .exchange()
            .expectStatus().is2xxSuccessful
    }

    test("화이트 리스트에 포함되지 않은 정보로 회원가입 할 수 없다") {
        val req = SignUpRequest("tester", "password", "실패자", "123456-1234567")

        testClient.post().uri("/szs/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(req)
            .exchange()
            .expectStatus().is4xxClientError
    }

    test("이미 존재하는 userId로 회원가입 할 수 없다") {
        testClient.signup(userId = "tester", regNo = "820326-2715702", name = "손오공", password = "password")
        val req = SignUpRequest("tester", "password", "실패자", "123456-1234567")

        testClient.post().uri("/szs/signup")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(req)
            .exchange()
            .expectStatus().is4xxClientError
    }

    test("회원가입된 고객을 대상으로 로그인을 하면 토큰을 발급 받는다") {
        testClient.signup(regNo = "820326-2715702", name = "손오공", userId = "tester", password = "password")
        val req = LogInRequest("tester", "password")

        testClient.post().uri("/szs/login")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(req)
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.token").isNotEmpty
    }

    test("회원가입하지 않은 고객을 대상으로 로그인할 수 없다") {
        val req = LogInRequest("tester", "password")

        testClient.post().uri("/szs/login")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(req)
            .exchange()
            .expectStatus().is4xxClientError
    }

    test("회원가입의 비밀번호와 로그인의 비밀번호가 다르면 로그인에 실패한다") {
        testClient.signup(regNo = "820326-2715702", name = "손오공", userId = "tester", password = "password")
        val req = LogInRequest("tester", "differentPassword")

        testClient.post().uri("/szs/login")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(req)
            .exchange()
            .expectStatus().is4xxClientError
    }
})
