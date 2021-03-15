package cn.iocoder.springboot.lab03.kafkademo.producer;

import cn.iocoder.springboot.lab03.kafkademo.message.Demo02Message;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import javax.annotation.Resource;

@Component
public class Demo02Producer {

    @Resource
    private KafkaTemplate<Object, Object> kafkaTemplate;

    public ListenableFuture<SendResult<Object, Object>> asyncSend(Integer id) {
        // 创建 Demo02Message 消息
        Demo02Message message = new Demo02Message();
        message.setId(id);
        // 异步发送消息
        return kafkaTemplate.send(Demo02Message.TOPIC, message);
    }

    /*
    * 而 Kafka 提供的批量发送消息，它提供了一个 RecordAccumulator 消息收集器，
    * 将发送给相同 Topic 的相同 Partition 分区的消息们，
    * “偷偷”收集在一起，当满足条件时候，一次性批量发送提交给 Kafka Broker 。如下是三个条件，满足任一即会批量发送：
    * 【数量】batch-size ：超过收集的消息数量的最大条数。
    【空间】buffer-memory ：超过收集的消息占用的最大内存。
    【时间】linger.ms ：超过收集的时间的最大等待时长，单位：毫秒。
    *
    * */
}
