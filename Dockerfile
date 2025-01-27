FROM amazoncorretto:21
LABEL authors="a.romanov"

WORKDIR /exchange

COPY build/libs/exchange-0.0.1-SNAPSHOT.jar .

EXPOSE 8080

CMD ["java", "-jar", "exchange-0.0.1-SNAPSHOT.jar"]