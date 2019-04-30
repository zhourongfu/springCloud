# springCloud
![结构](https://github.com/weilus923/springCloud/blob/master/test.jpg)
### 注册中心 eureka

    依次启动 eurekaService
    查看服务注册 ->访问 http://localhost:8761
    账号：eureka
    密码：123456

### 配置中心 config
> 1. docker安装rabbitMq
```
docker run -d --name=RABBITMQ -p 5672:5672 -p 15672:15672 \
-e RABBITMQ_DEFAULT_USER=guest -e RABBITMQ_DEFAULT_PASS=guest \
rabbitmq:3-management
```

> 2. 变更config配置,使配置自动生效
```
    curl -X POST http://config:cf123456@192.168.198.1:7000/bus/refresh?destination=gateway:**
```

> 3. 向eureka指定IP:  EUREKA_INSTANCE_IP-ADDRESS
```
    docker run -d -v /data/logs:/tmp/logs --name=config -e EUREKA_INSTANCE_IP-ADDRESS=192.168.198.128 -p 7000:7000 weiluscloud/config
```

### 用户中心 oauth2
> 1. 用户登录          oauth2 密码模式
> 2. 授权登录          oauth2 授权码模式
> 3. 授权微服务访问     oauth2 客户端模式

### 网关 zuul

### 熔断 hystrix
> 1. docker启动服务提供者
```
docker run -d --name=feign-service \
-e "spring.application.name=feign-service" \
-e "server.port=8050" \
-e EUREKA_INSTANCE_IP-ADDRESS=192.168.198.128 \
weilus.cloud/feign-hystrix
```
> 2. docker启动服务消费者
```
docker run -d --name=feign-call \
-e "spring.application.name=feign-call" \
-e "server.port=8060" \
-e EUREKA_INSTANCE_IP-ADDRESS=192.168.198.128 \
weilus.cloud/feign-hystrix
```

### 熔断监控 turbine

> 1. 监控hystrix消费服务状况

    消费者集群TEST-TURBINE
    访问 http://127.0.0.1:9101/hystrix
    输入监控流 http://127.0.0.1:9101/turbine.stream?cluster=TEST-TURBINE
