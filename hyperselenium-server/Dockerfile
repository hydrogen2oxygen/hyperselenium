FROM openjdk:8

LABEL maintainer="luxuspeter@googlemail.com"

COPY target/hyperselenium-server*.jar /hyperselenium-server.jar
ADD staticData staticData
EXPOSE 8080

RUN dpkg-reconfigure -f noninteractive tzdata

CMD ["java", "-jar", "hyperselenium-server.jar"]
