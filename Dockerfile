# ===== Builder stage =====
FROM eclipse-temurin:21-jdk-jammy AS builder
WORKDIR /application
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY src ./src
RUN ./mvnw clean package -DskipTests
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

# ===== Final (runtime) stage =====
FROM eclipse-temurin:21-jre-jammy
WORKDIR /application
COPY --from=builder /application/dependencies/ ./
COPY --from=builder /application/snapshot-dependencies/ ./
COPY --from=builder /application/spring-boot-loader/ ./
COPY --from=builder /application/application/ ./
ENV JAVA_TOOL_OPTIONS="${JAVA_TOOL_OPTIONS} -XX:+ExitOnOutOfMemoryError -XX:MaxRAMPercentage=75.0"
EXPOSE 8080
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]