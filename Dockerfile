FROM maven:3-jdk-11
ADD . /member-management
WORKDIR /member-management

# Just echo so we can see, if everything is there :)
RUN ls -l

# Run Maven build
RUN mvn clean install

FROM openjdk:11
MAINTAINER Saleh and Carl
VOLUME /tmp

# Add Spring Boot app.jar to Container
COPY --from=0 "/member-management/target/member-management-*-SNAPSHOT.jar" app.jar

# Fire up our Spring Boot app by default
CMD [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]

EXPOSE 8083