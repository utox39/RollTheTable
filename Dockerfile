# ---- Build stage: package the WAR with Maven ----
FROM maven:3.9-eclipse-temurin-22 AS build

WORKDIR /app

# Resolve dependencies first so they are cached until pom.xml changes
COPY pom.xml .
RUN mvn -B dependency:go-offline

COPY src ./src
RUN mvn -B package

# ---- Runtime stage: Java 22 JRE + Tomcat 9 ----
FROM eclipse-temurin:22-jre

ENV CATALINA_HOME=/usr/local/tomcat
ENV PATH=$CATALINA_HOME/bin:$PATH

# Reuse the official Tomcat 9 distribution, running on the Java 22 JRE
COPY --from=tomcat:9.0 /usr/local/tomcat $CATALINA_HOME

RUN rm -rf $CATALINA_HOME/webapps/*
COPY --from=build /app/target/RollTheTable.war $CATALINA_HOME/webapps/RollTheTable.war

EXPOSE 8080
CMD ["catalina.sh", "run"]
