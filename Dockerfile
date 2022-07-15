FROM maven:3-jdk-11
# Run Maven build
RUN mvn clean install -X
# Just echo so we can see, if everything is there :)
RUN ls -l

FROM openjdk:11
ADD target/member-management-0.0.1-SNAPSHOT.jar member-management.jar
ENTRYPOINT ["java", "-jar", "member-management.jar"]
EXPOSE 8083