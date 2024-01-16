package com.o3.mj.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(info = @Info(title = "고객 관리 및 세액 계산 API", version = "v1"))
@Configuration
public class SwaggerConfig { }