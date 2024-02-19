FROM maven:3.8.4-openjdk-17

WORKDIR /SpringBootExample
COPY . .

EXPOSE 8080

RUN mvn clean install

CMD mvn spring-boot:run