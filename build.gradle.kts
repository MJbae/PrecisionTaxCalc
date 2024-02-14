plugins {
	java
	id("org.springframework.boot") version "3.1.7"
	id("io.spring.dependency-management") version "1.1.4"
	id("com.adarshr.test-logger") version "4.0.0"
	kotlin("jvm") version "1.7.10"
}

group = "com.o3"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

val koTestVer = "5.5.4"
val koTestExtensionVersion = "1.1.2"
dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.security:spring-security-core")

	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2")

	runtimeOnly("com.h2database:h2")

	implementation("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.kotest:kotest-runner-junit5:$koTestVer")
	testImplementation("io.kotest:kotest-assertions-core:$koTestVer")
	testImplementation("io.kotest:kotest-property:$koTestVer")
	testImplementation("io.kotest.extensions:kotest-extensions-spring:$koTestExtensionVersion")
	testImplementation("org.springframework.boot:spring-boot-starter-webflux")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.bootBuildImage {
	builder.set("paketobuildpacks/builder-jammy-base:latest")
}
