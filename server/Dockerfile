FROM openjdk:8-jdk-alpine
LABEL maintainer="aaron.ebnoether@gmail.com"
COPY build/libs/* /app/server.jar
EXPOSE 41414
CMD ["java", "-jar", "/app/server.jar"]
