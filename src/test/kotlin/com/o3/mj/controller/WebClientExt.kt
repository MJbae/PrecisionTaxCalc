package com.o3.mj.controller

import com.o3.mj.controller.dto.LogInRequest
import com.o3.mj.controller.dto.SignUpRequest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

fun WebTestClient.signup(regNo: String, name: String, userId: String, password: String) {
    val req = SignUpRequest(userId, password, name, regNo)

    this.post().uri("/szs/signup")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(req)
        .exchange()
        .expectStatus().isOk
}

fun WebTestClient.login(userId: String, password: String) {
    val req = LogInRequest(userId, password)

    this.post().uri("/szs/login")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(req)
        .exchange()
        .expectStatus().isOk
}