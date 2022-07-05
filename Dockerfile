FROM openjdk:11
ADD target/member-management-0.0.1-SNAPSHOT.jar member-management.jar
ENTRYPOINT ["java", "-jar", "member-management.jar"]
EXPOSE 8083