package cn.iocoder.springboot.lab03.kafkademo.consumer;

import cn.iocoder.springboot.lab03.kafkademo.message.Demo06Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Demo06Consumer {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @KafkaListener(topics = Demo06Message.TOPIC,
            groupId = "demo06-consumer-group-" + Demo06Message.TOPIC,
            concurrency = "2") // 创建两个线程消费DEMO06下的消费
    public void onMessage(Demo06Message message) {
        logger.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }

    /*
    * 本节是并发消费
    * 首先，我们来创建一个 Topic 为 "DEMO_06" ，并且设置其 Partition 分区数为 10 。
然后，我们创建一个 Demo06Consumer 类，并在其消费方法上，添加 @KafkaListener(concurrency=2) 注解。
再然后，我们启动项目。Spring-Kafka 会根据 @KafkaListener(concurrency=2) 注解，创建 2 个 Kafka Consumer 。
* 注意噢，是 2 个 Kafka Consumer 呢！！！后续，每个 Kafka Consumer 会被单独分配到一个线程中，进行拉取消息，消费消息。
之后，Kafka Broker 会将 Topic 为 "DEMO_06" 分配给创建的 2 个 Kafka Consumer 各 5 个 Partition 。
* 😈 如果不了解 Kafka Broker “分配区分”机制单独胖友，可以看看 《Kafka 消费者如何分配分区》 文章。
这样，因为 @KafkaListener(concurrency=2) 注解，
* 创建 2 个 Kafka Consumer ，就在各自的线程中，拉取各自的 Topic 为 "DEMO_06" 的 Partition 的消息，
* 各自串行消费。从而，实现多线程的并发消费。
    * */
}
