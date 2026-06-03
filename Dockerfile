FROM maven:3.9.5-eclipse-temurin-21 AS builder
WORKDIR /app

# Copy Maven files first to leverage Docker layer caching
COPY pom.xml mvnw .mvn/ ./
COPY src ./src

# Build the application (skip tests for faster build on render)
RUN mvn -DskipTests package -B

FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy jar from the builder stage
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENV PORT=8080

ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT} -jar /app/app.jar"]
