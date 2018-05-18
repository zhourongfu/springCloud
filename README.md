# springCloud
服务发现与注册

    依次启动 eurekaService configService configClientA configClentB
    查看服务注册 ->访问 http://localhost:8761
    账号：eureka
    密码：123456
服务消费

	依次启动 eurekaService serviceProducer serviceCustomer apiGateWay

	访问 POST/GET http://localhost:8080/test
	serviceCustomer调用serviceProducer

	访问 POST     http://localhost:8088/serviceProducer/hi
	apiGateWay调用serviceProducer

微服务配置中心

	依次启动 eurekaService configService configClientA configClentB

    访问 http://localhost:7000/configClent/pro   返回配置中心configClent-pro.properties
    访问 http://localhost:7000/configClent/dev   返回配置中心configClent-dev.properties

    访问 http://localhost:8081/test  查看clientA 微服务消费配置中心的值
    访问 http://localhost:8082/test  查看clientB 微服务消费配置中心的值

消息总线bus

    修改配置中心/properties/configClient-pro.properties配置  aaa=im updated

    触发配置中心把修改后的值分发到微服务
    curl -X POST http://config:cf123456@192.168.198.1:7000/bus/refresh

    访问 http://localhost:8081/test  查看clientA 微服务消费配置中心修改后的值
    访问 http://localhost:8082/test  查看clientB 微服务消费配置中心修改后的值

