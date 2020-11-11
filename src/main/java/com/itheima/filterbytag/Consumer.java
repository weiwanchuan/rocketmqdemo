package com.itheima.filterbytag;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * 消费者,接收消息
 */
public class Consumer {
    public static void main(String[] args) throws Exception{
        //1.创建一个接收消息的对象consumer
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
        //2.设定接收的命令服务器地址
        consumer.setNamesrvAddr("192.168.37.140:9876");
        //3.设置接收消息对应的topic,对应的sub标签为任意 *
        consumer.subscribe("topic7","tag2 || tag1");
        //4.开启监听,用于接收消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                for (MessageExt msg : list) {
                    System.out.println("消息"+new String(msg.getBody()));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        //5.启动接收消息
        consumer.start();
        System.out.println("接收消息已启动");
    }
}
