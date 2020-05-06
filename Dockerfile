FROM gradle:jdk8
WORKDIR /app
COPY . .
RUN gradle assemble

FROM openjdk:8-alpine

COPY --from=0 /app/build/libs/services-1.0.0.jar /app/services.jar
RUN chmod a+rw /app && chmod a+x /app/services.jar
COPY entrypoint.sh /usr/local/bin/
WORKDIR /app

ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]
CMD ["java","-jar","services.jar"]