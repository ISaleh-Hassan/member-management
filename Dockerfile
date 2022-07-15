FROM maven:3-jdk-15
ADD . /member-management
WORKDIR /member-management

# Just echo so we can see, if everything is there :)
RUN ls -l

# Run Maven build
RUN mvn clean install -X

FROM openjdk:15
MAINTAINER Saleh and Carl
VOLUME /tmp

# Add Spring Boot app.jar to Container
COPY --from=0 "/member-management/target/member-management-*-SNAPSHOT.jar" app.jar

# Fire up our Spring Boot app by default
CMD [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]


#ADD target/member-management-0.0.1-SNAPSHOT.jar member-management.jar
#ENTRYPOINT ["java", "-jar", "member-management.jar"]
EXPOSE 8083