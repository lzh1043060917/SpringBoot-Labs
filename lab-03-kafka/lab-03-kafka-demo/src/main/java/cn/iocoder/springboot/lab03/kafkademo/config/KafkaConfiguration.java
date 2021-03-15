package cn.iocoder.springboot.lab03.kafkademo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.*;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class KafkaConfiguration {

    @Bean
    @Primary // 消费异常的处理器
    public ErrorHandler kafkaErrorHandler(KafkaTemplate<?, ?> template) {
        // 创建 DeadLetterPublishingRecoverer 对象,它负责实现，在重试到达最大次数时，Consumer 还是消费失败时，该消息就会发送到死信队列。
        ConsumerRecordRecoverer recoverer = new DeadLetterPublishingRecoverer(template);
        // 创建 FixedBackOff 对象,这里，我们配置了重试 3 次，每次固定间隔 30 秒。
        BackOff backOff = new FixedBackOff(10 * 1000L, 3L);
        // 创建 SeekToCurrentErrorHandler 对象,负责处理异常，串联整个消费重试的整个过程。
        return new SeekToCurrentErrorHandler(recoverer, backOff); // 只针对消息的单条消费失败的消费重试处理
    }

//    @Bean
//    @Primary // 批量消费失败的处理
//    public BatchErrorHandler kafkaBatchErrorHandler() {
//        // 创建 SeekToCurrentBatchErrorHandler 对象
//        SeekToCurrentBatchErrorHandler batchErrorHandler = new SeekToCurrentBatchErrorHandler();
//        // 创建 FixedBackOff 对象
//        BackOff backOff = new FixedBackOff(10 * 1000L, 3L);
//        batchErrorHandler.setBackOff(backOff);
//        // 返回
//        return batchErrorHandler;
//    }
    /*
    * Spring-Kafka 提供消费重试的机制。在消息消费失败的时候，Spring-Kafka 会通过消费重试机制，重新投递该消息给 Consumer ，
    * 让 Consumer 有机会重新消费消息，实现消费成功。当然，Spring-Kafka 并不会无限重新投递消息给 Consumer 重新消费，而是在默认情况下，达到 N 次重试次数时，Consumer 还是消费失败时，该消息就会进入到死信队列。
    * 死信队列用于处理无法被正常消费的消息。当一条消息初次消费失败，Spring-Kafka 会自动进行消息重试；
    * 达到最大重试次数后，若消费依然失败，则表明消费者在正常情况下无法正确地消费该消息，此时，Spring-Kafka 不会立刻将消息丢弃，
    * 而是将其发送到该消费者对应的特殊队列中。
    * Spring-Kafka 将这种正常情况下无法被消费的消息称为死信消息（Dead-Letter Message），
    * 将存储死信消息的特殊队列称为死信队列（Dead-Letter Queue）。后续，我们可以通过对死信队列中的消息进行重发，来使得消费者实例再次进行消费。
    * */

}
