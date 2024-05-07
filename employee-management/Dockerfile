FROM openjdk:18-jdk-slim
WORKDIR /employee-management
COPY target/employee-management.jar employee-management.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","employee-management.jar","-web -webAllowOthers -tcp -tcpAllowOthers -browser"]
