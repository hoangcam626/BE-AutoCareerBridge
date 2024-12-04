FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy tệp JAR vào container
COPY target/AutoCarrerBridge-0.0.1-SNAPSHOT.jar /app/AutoCareerBridge.jar


# Expose cổng mà ứng dụng Spring Boot sẽ chạy
EXPOSE 2222

# Khởi động ứng dụng Spring Boot, không phải JShell
CMD ["java", "-jar", "AutoCareerBridge.jar"]
