```
sudo mvn clean package docker:build
sudo mvn clean package docker:build -DpushImageTag
sudo docker images
sudo docker run -p 8084:8084 -t jluccisano/reactive-server

```


```$xslt
influx
use sensor


CREATE CONTINUOUS QUERY cq_1d ON sensor BEGIN SELECT \
mEAN(temperature) AS  mean_temperature, \
mEAN(humidity) AS mean_humidity, \
MIN(temperature) as min_temperature, \
MAX(temperature) as max_temperature, \
MIN(humidity) as min_humidity, \
MAX(humidity) as max_humidity, \
INTO sensor."autogen".downsampled_dht22 \
FROM dht22 \
GROUP BY time(1d), gatewayId \
END

//add GROUP BY gatewayId

CREATE RETENTION POLICY one_years_only ON sensor DURATION 52w REPLICATION 1 DEFAULT

CREATE CONTINUOUS QUERY cq_dht22_1h ON sensor BEGIN SELECT MEAN(temperature) AS  mean_temperature, MEAN(humidity) AS mean_humidity INTO sensor."one_years_only"."cq_dht22_1h" FROM dht22 GROUP BY time(1h), gatewayId END

CREATE CONTINUOUS QUERY cq_dht22_1d ON sensor BEGIN SELECT MEAN(temperature) AS  mean_temperature, MEAN(humidity) AS mean_humidity, MIN(temperature) as min_temperature , MAX(temperature) as max_temperature, MIN(humidity) as min_humidity, MAX(humidity) as max_humidity INTO sensor."one_years_only"."cq_dht22_1d" FROM dht22 GROUP BY time(1d), gatewayId END


SHOW RETENTION POLICIES ON sensor
SHOW CONTINUOUS QUERIES

SELECT * FROM sensor."autogen".downsampled_dht22


DROP CONTINUOUS QUERY cq_1h ON sensor

DROP RETENTION POLICY autogen ON sensor
```
