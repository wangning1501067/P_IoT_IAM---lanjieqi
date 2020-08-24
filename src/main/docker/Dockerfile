FROM bys-iot.chinanorth2.cloudapp.chinacloudapi.cn/library/openjdk:8
VOLUME /tmp
ADD iam.jar app.jar
#RUN bash -c 'touch /app.jar'
EXPOSE 8888
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
CMD exec java -Djava.security.egd=file:/dev/./urandom $PARAM  -jar -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/  /app.jar
#ENTRYPOINT ["java -DDB_SERVER_HOST=10.26.0.40 -jar /app.jar"]
ENV TZ Asia/Shanghai