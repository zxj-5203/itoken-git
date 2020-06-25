FROM openjdk:8-jre
MAINTAINER zxj <13116052056@163.com>

# 设置环境变量
ENV APP_VERSION 1.0.0-SNAPSHOT

COPY dockerize-linux-amd64-v0.6.1.tar.gz .
RUN tar -C /usr/local/bin -xzvf dockerize-linux-amd64-v0.6.1.tar.gz \
    && rm dockerize-linux-amd64-v0.6.1.tar.gz

RUN mkdir /app

COPY itoken-zipkin-$APP_VERSION.jar /app/app.jar
# 配置dockerize，等待http://192.168.141.180:8888/itoken-eureka/prod/master服务，5分钟，
ENTRYPOINT ["dockerize", "-timeout", "5m", "-wait", "http://192.168.141.180:8888/itoken-zipkin/prod/master", "java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/app.jar", "--spring.profiles.active=prod"]

EXPOSE 9411