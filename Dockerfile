FROM java:8
VOLUME /tmp
ADD target/spring-sync-ms.jar spring-sync-ms.jar
EXPOSE 8080
RUN bash -c "touch /spring-sync-ms.jar"
ENTRYPOINT ["java","-Dspring.data.mongodb.uri=mongodb://mongo/test", "-Djava.security.egd=file:/dev/./urandom","-jar","/spring-sync-ms.jar"]