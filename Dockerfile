FROM maven:3.8.1-adoptopenjdk-11 as builder
COPY ./ /build/
WORKDIR /build
RUN mvn clean install

FROM adoptopenjdk/openjdk11:alpine-jre

ENV DB_NAME=dama_db
ENV DB_HOST=mongo
ENV DB_USER_NAME=dama
ENV DB_USER_PASSWORD=dama2021
ENV JWT_SECRET=testJWTforJavaDama2021secretEarringApplicationMilkaBelarussecretDeutschDublikateaDama2021secretEarringAppl
ENV LOG_DIR=./logs

ARG JAR_FILE=earring-details-core/target/earring-details-core-*.jar
WORKDIR /opt/app
COPY --from=builder /build/${JAR_FILE} dama-api.jar
ENTRYPOINT ["java","-Dspring.profiles.active=dev", "-jar","dama-api.jar"]
EXPOSE 8080
