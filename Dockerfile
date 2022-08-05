FROM openjdk:17.0.1-jdk-slim

RUN apt-get update && \
    apt-get install -y wget

ENV DOCKERIZE_VERSION v0.6.1
RUN wget https://github.com/jwilder/dockerize/releases/download/$DOCKERIZE_VERSION/dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && tar -C /usr/local/bin -xzvf dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz \
    && rm dockerize-linux-amd64-$DOCKERIZE_VERSION.tar.gz

RUN mkdir app
ARG JAR_FILE
ADD /target/${JAR_FILE} /app/credit-analysis.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "credit-analysis.jar"]
EXPOSE 8080