FROM gradle:jdk8
WORKDIR /app
COPY . .
RUN gradle build

FROM openjdk:8-alpine

COPY --from=0 /app/build/libs/services-1.0.0.jar /app/services.jar
COPY entrypoint.sh /usr/local/bin/
WORKDIR /app

ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]
CMD ["java -jar services.jar"]