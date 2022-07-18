FROM openjdk:11.0.14.1-jdk-oraclelinux8
COPY target/weddingApp-0.0.1-SNAPSHOT.jar weddingApp-0.0.1-SNAPSHOT.jar
ENV SPRING_PROFILES_ACTIVE=docker
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=docker", "/weddingApp-0.0.1-SNAPSHOT.jar"]
