# detection-module
detect module
#Zookeer
bin/zookeeper-server-start.sh config/zookeeper.properties 

#Kafka
bin/kafka-server-start.sh config/server.properties 

#To see topics and messages in explorer
./offsetexplorer.sh &

#To see transaction_topic messages
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic transaction_topic --from-beginning

#To see fraud_topic messages
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic fraud_topic --from-beginning

#Postman
http://localhost:9191/api/transactions