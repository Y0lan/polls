FROM openjdk:8-alpine

# Required for starting application up.
RUN apk update && apk add /bin/sh
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
RUN mkdir -p /home/spring/app
ENV PROJECT_HOME /home/spring/app

COPY target/polls-0.0.1-SNAPSHOT.jar $PROJECT_HOME/polls-0.0.1-SNAPSHOT.jar

WORKDIR $PROJECT_HOME

CMD ["java" ,"-jar","./polls-0.0.1-SNAPSHOT.jar"]