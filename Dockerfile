FROM openjdk:11-jdk-alpine
LABEL responsable="lucasvannier@gmail.com"
EXPOSE 8082:8082
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} notesapi.jar
ENTRYPOINT ["java","-jar","/notesapi.jar"]

