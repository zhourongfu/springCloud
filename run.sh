!# /bin/bash
# 单机启动 MYSQL REDIS MQ EUREKA ZUUL
docker-compose -f docker-compose.yml up -d
# swarm集群启动配置中心 认证中心
docker stack deploy -c docker-stack.yml cloud --with-registry-auth