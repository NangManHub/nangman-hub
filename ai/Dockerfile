# 1. JDK 설치된 기본 이미지 사용
FROM openjdk:17-jdk-slim AS build

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. 프로젝트 코드 복사
COPY ./ ./

# 4. Gradle 빌드 (Maven 사용 시 변경)
RUN ./gradlew clean build -x test

# 5. 빌드 결과물을 최종 이미지에 복사
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

# 6. 실행 환경변수 설정 (docker-compose.yml에서 설정될 예정)
ENTRYPOINT ["java", "-jar", "app.jar"]