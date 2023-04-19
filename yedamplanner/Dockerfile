FROM openjdk:11-jre
ARG JAR_FILE=target/*.jar 
COPY ${JAR_FILE} app.jar 
ENV TZ=Asia/Seoul
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar"]