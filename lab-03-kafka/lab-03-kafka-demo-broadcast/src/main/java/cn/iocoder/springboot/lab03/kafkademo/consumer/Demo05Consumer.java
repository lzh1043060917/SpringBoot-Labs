package cn.iocoder.springboot.lab03.kafkademo.consumer;

import cn.iocoder.springboot.lab03.kafkademo.message.Demo05Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Demo05Consumer {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @KafkaListener(topics = Demo05Message.TOPIC,
            groupId = "demo05-consumer-group-" + Demo05Message.TOPIC + "-" + "#{T(java.util.UUID).randomUUID()}")
    public void onMessage(Demo05Message message) {
        logger.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }
    /*
    * 广播消费模式下，相同 Consumer Group 的每个 Consumer 实例都接收全量的消息。
    不过 Kafka 并不直接提供内置的广播消费的功能！！！此时，我们只能退而求其次，
    * 每个 Consumer 独有一个 Consumer Group ，从而保证都能接收到全量的消息。
    * */


    /**
     * 又例如说，我们基于 WebSocket 实现了 IM 聊天，在我们给用户主动发送消息时，
     * 因为我们不知道用户连接的是哪个提供 WebSocket 的应用，所以可以通过 Kafka 广播消费，
     * 每个应用判断当前用户是否是和自己提供的 WebSocket 服务连接，如果是，则推送消息给用户。
     *
     *
     */
    /
}
