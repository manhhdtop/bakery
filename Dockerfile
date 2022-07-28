FROM openjdk:11 AS MAVEN_BUILD

MAINTAINER manhhd<hoangmanh1505@gmail.com>

#Define parameter
ARG APP_DIR=/home/workspace/bakery/
ARG SRC_DIR=/target/bakery-0.0.1.jar
ARG LOGPATH=/home/workspace/logs/bakery
ARG JAR_FILE=bakery.jar

#Create direction Application folder
RUN mkdir -p ${LOGPATH}

#Copy source to deploy direction
COPY ${SRC_DIR} ${APP_DIR}

WORKDIR ${APP_DIR}

ENV APP_FILE=$JAR_FILE
ENV LOG_PATH=${LOGPATH}

RUN chmod a+x ${APP_DIR}/*

EXPOSE 6005

ENTRYPOINT ["java","-Dlog_path=/home/ubuntu/logs/bakery/", "-jar", "bakery.jar"]