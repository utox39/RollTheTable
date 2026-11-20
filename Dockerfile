# ---- Build stage: compile sources and package the WAR (mirrors deploy.sh) ----
FROM eclipse-temurin:22-jdk AS build

WORKDIR /app
COPY lib ./lib
COPY src ./src

RUN mkdir -p build/classes build/war/WEB-INF/classes build/war/WEB-INF/lib \
    && javac -d build/classes -cp "lib/*" $(find src -name "*.java") \
    && cp -r src/web/* build/war \
    && cp -r build/classes/* build/war/WEB-INF/classes \
    && cp lib/*.jar build/war/WEB-INF/lib \
    && rm build/war/WEB-INF/lib/javax.servlet-api-4.0.1.jar \
    && jar cf RollTheTable.war -C build/war .

# ---- Runtime stage: Java 22 JRE + Tomcat 9 ----
FROM eclipse-temurin:22-jre

ENV CATALINA_HOME=/usr/local/tomcat
ENV PATH=$CATALINA_HOME/bin:$PATH

# Reuse the official Tomcat 9 distribution, running on the Java 22 JRE
COPY --from=tomcat:9.0 /usr/local/tomcat $CATALINA_HOME

RUN rm -rf $CATALINA_HOME/webapps/*
COPY --from=build /app/RollTheTable.war $CATALINA_HOME/webapps/RollTheTable.war

EXPOSE 8080
CMD ["catalina.sh", "run"]
