## 📦 낭만HUB : MSA 기반 물류 관리 및 배송 시스템


## 📄 목차
1. [프로젝트 목표](#📌-프로젝트-목표)   
2. [서비스 구성 및 실행 방법](#⚙️-서비스-구성-및-실행-방법)   
3. [ERD](#📐-ERD )   
4. [기술 스택](#⚒️-기술-스택)   
5. [트러블 슈팅](#💣-트러블-슈팅)

## 🎯 프로젝트 목표 
- Microservices Architecture 기반의 B2B 물류 관리 및 배송 시스템을 개발합니다.
- `Spring Cloud`를 활용하여 MSA 애플리케이션을 구성합니다.
- 서비스 간 통신의 신뢰성을 확보하고, 독립적인 데이터베이스 환경에서 데이터 무결성을 유지합니다.
- `Kafka`를 활용하여 Event Driven 방식의 주문-배송-알림 시스템을 구성합니다.

## ⚙️ 서비스 구성 및 실행 방법
### 인프라 설계서
![낭만HUB_와이어프레임](https://github.com/user-attachments/assets/33d6d343-53f8-4b00-ac46-dc82e1ef5402)
### 이벤트 Flow
![이벤트 Flow](https://github.com/user-attachments/assets/4e8f44b9-73f7-4701-aa07-0346bf13e3c5)
### 실행 방법
```bash
# Clone the Git repository
git clone https://github.com/NangManHub/nangman-hub.git

# Go to the project directory
cd nangman-hub

# Build and run containers using Docker Compose
docker compose up -d --build
```

## 📐 ERD 
![ERD](https://github.com/user-attachments/assets/2d5ca910-2475-4092-89c2-42e4291f7eb7)

## ⚒️ 기술 스택 

### Server
![Static Badge](https://img.shields.io/badge/Java-17-blue)
![Static Badge](https://img.shields.io/badge/SpringBoot-3.4.0-green)

![SpringDataJPA](https://img.shields.io/badge/Spring%20Data%20JPA-6DB33F?style=for-the-badge)
![QueryDSL](https://img.shields.io/badge/QueryDSL-00B9F1?style=for-the-badge)
![SpringSecurity](https://img.shields.io/badge/spring%20security-6DB33F?style=for-the-badge)
![Swagger](https://img.shields.io/badge/-Swagger-6DB33F?style=for-the-badge)  
![SpringCloud](https://img.shields.io/badge/Spring%20Cloud-6DB33F?style=for-the-badge)
![Resilience4j](https://img.shields.io/badge/Resilience4j-00A2A2?style=for-the-badge)
![Openfeign](https://img.shields.io/badge/Openfeign-00A9FF?style=for-the-badge)
![JWT](https://img.shields.io/badge/JWT-000000?style=for-the-badge)

### DB  
![Postgresql](https://img.shields.io/badge/postgresql-4169E1?style=for-the-badge)
![Redis](https://img.shields.io/badge/redis-FF4438?style=for-the-badge)  

### Message Broker  
![Kafka](https://img.shields.io/badge/kafka-231F20?style=for-the-badge)

### Monitoring
![Zipkin](https://img.shields.io/badge/Zipkin-F7B83B?style=for-the-badge)  

### Containerization
![Docker](https://img.shields.io/badge/docker-2496ED?style=for-the-badge)
![DockerCompose](https://img.shields.io/badge/docker_compose-0A79D0?style=for-the-badge)

### Version Control  
![Git](https://img.shields.io/badge/git-F05032?style=for-the-badge)
![Github](https://img.shields.io/badge/github-181717?style=for-the-badge)  



## 💣 트러블 슈팅 
<details>
  <summary>비동기 주문-배송 생성 과정에서의 데이터 불일치 이슈</summary>
  
  ### 문제 정의

  비동기적으로 주문과 배송을 처리하는 구조에서 다음과 같은 문제가 발생했습니다:
  
  1. 주문 생성 → Kafka를 통해 메시지 전송 → 배송 생성.
  2. 배송 생성 실패 시(예: 배송기사 없음, 허브 정보 부족 등) **이미 생성된 주문 데이터가 삭제되지 않는 문제** 발생.
  3. 이를 해결하기 위해 **보상 트랜잭션(Compensating Transaction)** 로직이 필요했습니다.

  ---

  ### 기술 선정

  Kafka의 에러 처리 패턴 중 서비스 요구 사항에 따라 Dead Letter Queue(DLQ) 방식을 도입했습니다:
  
  - **조건**:
    - 단순한 데이터 생성/삭제 중심의 로직.
    - 메시지 처리 순서 보장이 불필요.
  - **선택**:
    - DLQ를 사용해 에러 발생 시 메시지를 별도의 에러 토픽으로 전송 및 관리.

  ---

  ### 해결 과정

  1. **배송 생성 실패 이벤트 처리**  
     - 배송 생성 실패 시 **`delivery.create-fail`** 이벤트를 DLQ로 발송.
  
  2. **주문 데이터 롤백 처리**  
     - 주문 서비스에서 **`delivery.create-fail`** 이벤트 수신 후 관련 주문 데이터를 **Soft Delete** 방식으로 롤백.
  
  3. **확장 가능성**  
     - 슬랙(Slack) 알림 기능 추가로 배송 생성 실패를 실시간으로 관리자에게 알림.

  ---

  ### 결과

  - 분산 트랜잭션 환경에서 **데이터 일관성**을 유지하는 롤백 로직 구현.
  - Kafka DLQ 활용으로 비동기 데이터 불일치 문제를 효과적으로 해결.
  - 확장 가능성을 고려한 설계로 관리 효율성 및 안정성 향상.
  
</details>



## 🔗 Link   
[👉🏻 시스템 기획 명세서 정리 Blog](https://zxxhe.tistory.com/22)


## 👥 팀원 소개   

| 이주희 | 권현준 | 박준형 | 연제민 |
| --- | --- | --- | --- |
| ✴︎ 팀장 <br> ✴︎ Company <br> ✴︎ Order | ✴︎ User <br> ✴︎ Slack | ✴︎ Delivery <br> ✴︎ Track | ✴︎ Hub <br> ✴︎ AI |