package cn.iocoder.springboot.lab03.kafkademo.producer;

import cn.iocoder.springboot.lab03.kafkademo.message.Demo06Message;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;

@Component
public class Demo06Producer {

    @Resource
    private KafkaTemplate<Object, Object> kafkaTemplate;

    public SendResult syncSend(Integer id) throws ExecutionException, InterruptedException {
        // 创建 Demo01Message 消息
        Demo06Message message = new Demo06Message();
        message.setId(id);
        // 同步发送消息
        return kafkaTemplate.send(Demo06Message.TOPIC, message).get();
    }
    // 顺序消息发送方法
    public SendResult syncSendOrderly(Integer id) throws ExecutionException, InterruptedException {
        // 创建 Demo01Message 消息
        Demo06Message message = new Demo06Message();
        message.setId(id);
        // 同步发送消息
        // 因为我们使用 String 的方式序列化 key ，所以需要将 id 转换成 String
        // 调用 KafkaTemplate 同步发送消息方法时，我们多传入了 id 作为消息的 key ，
        // 从而实现发送到 DEMO_06 这个 Topic 下的相同 Partition 中。
        return kafkaTemplate.send(Demo06Message.TOPIC, String.valueOf(id), message).get();
    }

}
