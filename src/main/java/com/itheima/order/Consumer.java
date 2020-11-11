package com.itheima.order;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * 顺序
 * 消费者
 * 接收消息
 */
public class Consumer {
    public static void main(String[] args) throws Exception{
        //1.创建一个接收消息的对象consumer
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
        //2.设定接收的命令服务器地址
        consumer.setNamesrvAddr("192.168.37.140:9876");
        //3.设置接收消息对应的topic,对应的sub标签为任意 *
        consumer.subscribe("orderTopic","*");
        //4.开启监听,用于接收消息
        //使用单线程的模式从消息队列中取数据，一个线程绑定一个消息队列
        //接收消息对象要用 MessageListenerOrderly
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
                for (MessageExt msg : list) {
                    System.out.println(Thread.currentThread().getName() + "" + "顺序消息" + new String(msg.getBody()));
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        //5.启动接收消息
        consumer.start();
        System.out.println("接收消息已启动");
    }
}
