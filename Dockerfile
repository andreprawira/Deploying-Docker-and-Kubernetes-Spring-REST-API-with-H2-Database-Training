FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAVA_OPTS
ENV JAVA_OPTS=$JAVA_OPTS
ADD target/spring-sync-ms.jar spring-sync-ms.jar
EXPOSE 8081
ENTRYPOINT exec java $JAVA_OPTS -jar spring-sync-ms.jar
# For Spring-Boot project, use the entrypoint below to reduce Tomcat startup time.
#ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar spring-sync.jar
