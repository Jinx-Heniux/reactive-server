[![Build Status](https://travis-ci.org/jluccisano/reactive-server.svg?branch=master)](https://travis-ci.org/jluccisano/reactive-server)


#### Prerequisites
maven
docker

#### RUN on dev

```bash
mv application.tpl.yml application.yml
``` 
- Set your value into application.yml

- Start server
```bash
mvn spring-boot:run -Drun.profiles=dev
```

#### RUN standalone


```bash
mvn clean install -P prod
```

```bash
cd target
```


```bash
java -jar reactive-server-0.0.1-SNAPSHOT.jar \
    --server.port=YOUR_PORT \
    --spring.rabbitmq.endpoint=YOUR_RABBITMQ_ENDPOINT \
    --spring.rabbitmq.exchange=YOUR_RABBITMQ_EXCHANGE \
    --spring.rabbitmq.queue=YOUR_RABBITMQ_QUEUE \
    --spring.rabbitmq.gatewayId=YOUR_RABBITMQ_GATEWAYID \
    --spring.influxdb.url=YOUR_INFLUXDB_URL \
    --spring.influxdb.username=YOUR_INFLUXDB_USERNAME \
    --spring.influxdb.password=YOUR_INFLUXDB_PASSWORD \
    --spring.influxdb.database=YOUR_INFLUXDB_DATABASE \
    --spring.influxdb.retention-policy=YOUR_INFLUXDB_RETENTION_POLICY
```
    
#### RUN on docker

```bash
mvn clean package -P prod docker:build
```
or 
```bash
docker build -t jluccisano/reactive-server .
```

```bash
docker run -d -t --name reactive-server \
    -p 8084:8084 \
    -e PORT=$PORT \
    -e RABBITMQ_ENDPOINT=$RABBITMQ_ENDPOINT \
    -e RABBITMQ_EXCHANGE=$RABBITMQ_EXCHANGE \
    -e RABBITMQ_QUEUE=$RABBITMQ_QUEUE \
    -e RABBITMQ_GATEWAYID=$RABBITMQ_GATEWAYID \
    -e INFLUXDB_URL=$INFLUXDB_URL \
    -e INFLUXDB_USERNAME=$INFLUXDB_USERNAME \
    -e INFLUXDB_PASSWORD=$INFLUXDB_PASSWORD \
    -e INFLUXDB_DATABASE=$INFLUXDB_DATABASE \
    -e INFLUXDB_RETENTION_POLICY=$INFLUXDB_RETENTION_POLICY \
    jluccisano/reactive-server
```

/root/.gem/ruby/2.4.0/gems/travis-1.8.8/bin/travis encrypt DOCKER_EMAIL=joseph.luccisano@gmail.com --repo=jluccisano/reactive-server --add


travis encrypt DOCKER_EMAIL=your-docker-hub-email@gmail.com --add env.global.DOCKER_EMAIL
travis encrypt DOCKER_USER=your-docker-hub-username --add env.global.DOCKER_USER
travis encrypt DOCKER_PASS=your-docker-hub-password --add env.global.DOCKER_PASS


