# 세액 계산 시스템

### 목차
[I. 테스트 환경 정보](#i-테스트-환경-정보)

[II. 요구사항 구현 여부](#ii-요구사항-구현-여부)

[III. 요구사항 검증 결과](#iii-요구사항-검증-결과)

[IV. 요구사항 구현 방법](#iv-요구사항-구현-방법)

[V. 프로젝트 구조](#v-프로젝트-구조)

### I. 테스트 환경 정보
- Swagger URL: http://localhost:8080/swagger-ui/index.html
- H2 DB Console URL: http://localhost:8080/h2-console/login.jsp 
- H2 JDBC URL: jdbc:h2:mem:testdb
- H2 User Name: sa
- H2 User 비밀번호는 없습니다.

### II. 요구사항 구현 여부
- **회원가입 API 구현**: 완료
- **로그인 API 구현**: 완료
- **고객 정보 조회 API 구현**: 완료
- **고객 세액 정보 스크랩 API 구현**: 완료
- **고객 환급액 계산 API 구현**: 완료

### III. 요구사항 검증 결과

- **회원가입 API 요구사항 검증 결과**
    : 본 API를 호출한 결과, Request Body로 전달한 정보가 DB에 정상적으로 저장됩니다. 주민등록번호는 암호화하고, 비밀번호는 해싱하여 저장됩니다. 요구사항에 명시된 5명만 회원가입이 가능합니다.

- **로그인 API 요구사항 검증 결과**
    : 회원가입 API에 전달한 userId와 password로 본 API를 호출한 결과, 성공 응답을 받습니다. 본 API의 Response Body에서 인증정보가 암호화된 토큰을 얻을 수 있습니다.

- **고객 정보 조회 API 요구사항 검증 결과**
    : 인증 토큰으로 본 API를 호출한 결과, 사용자의 정보를 조회할 수 있습니다.

- **고객 세액 정보 스크랩 API 요구사항 검증 결과**
    : 인증 토큰으로 본 API를 호출한 결과, 본 사용자의 세액 정보를 스크랩핑하고 필요한 값만 DB에 저장됩니다.  

- **고객 환급액 계산 API 요구사항 검증 결과**
    : 인증 토큰으로 본 API를 호출한 결과, 결정세액과 퇴직연금세액공제금이 계산되어 API 응답으로 확인할 수 있습니다. 결정세액 계산과 관련된 다양한 예외 케이스를 단위테스트로 검증합니다.

### IV. 요구사항 구현 방법

- **회원가입 API 구현 방법**
    : 

- **로그인 API 구현 방법**


- **고객 정보 조회 API 구현 방법**


- **고객 세액 정보 스크랩 API 구현 방법**


- **고객 환급액 계산 API 구현 방법**



### V. 프로젝트 구조
본 프로젝트는 4개의 주요 패키지로 구성되어 있습니다.
- **domain**: 핵심 비지니스 로직과 상태를 모델링합니다.
- **usecase**: 도메인 모델을 조합하여 비지니스 기능을 구현합니다.
- **repository**: 데이터 영속화 기능을 구현합니다.
- **controller**: 클라이언트와의 통신을 위한 기능을 구현합니다.

