
-- zuul-test-master.properties
INSERT INTO cloud_properties(`key`,`value`,`application`,profile,label) VALUES
('spring.rabbitmq.addresses','192.168.198.128:5672','zuul','test','master'),
('spring.rabbitmq.username','guest','zuul','test','master'),
('spring.rabbitmq.password','guest','zuul','test','master'),
('zuul.routes.serviceProducer.serviceId','serviceProducer','zuul','test','master'),
('zuul.routes.serviceProducer.path','/serviceProducer/**','zuul','test','master');

-- serviceProducer-test-master.properties
INSERT INTO cloud_properties(`key`,`value`,`application`,profile,label) VALUES
('spring.rabbitmq.addresses','192.168.198.128:5672','serviceProducer','test','master'),
('spring.rabbitmq.username','guest','serviceProducer','test','master'),
('spring.rabbitmq.password','guest','serviceProducer','test','master');