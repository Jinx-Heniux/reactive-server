#!/bin/sh

docker pull jluccisano/reactive-server:latest
docker stop reactive-server
docker rm reactive-server
docker run --name reactive-server -p 8084:8084 -d  jluccisano/reactive-server:latest