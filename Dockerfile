FROM oraclelinux:8-slim
RUN dnf install -y java-17-openjdk && dnf clean all
WORKDIR /app
COPY target/moitovary-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]