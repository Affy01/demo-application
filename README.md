This application is to demonstrate issues with sending message to dlq
when the message times out or message size is larger than the default 1MB
for the producer. Currently, the messages don't fall into the dlq topic, and the reason
could be that the messaging client isn't aware there was an issue producing the message
in the output topic.

Running the application:
``
./gradlew clean build bootrun
``

Producing a message: 
To produce a message, kafka producer can be used directly to send a message to `input-topic`
``kafka-console-producer --broker-list  localhost:9092 --topic input-topic``

For the timeout issue, these 2 settings need to be uncommented in the application.yml
``delivery.timeout.ms: 1
request.timeout.ms: 1``

For the message size issue `max.request.size: 1` property needs to be
uncommented in the application.yml file. For both the scenarios, the return value for 
`processor().output().send()` is true hence the log `Message sent successfully`
gets printed.


The issues could be because of asynchronous calls between the app and spring + kafka. Hence, the message comes back as 
sent successfully from spring but fails when the spring layer sends it to the kafka layer.