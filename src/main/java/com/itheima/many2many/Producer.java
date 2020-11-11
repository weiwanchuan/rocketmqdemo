package com.itheima.many2many;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

/**
 * 生产者,产生消息
 */
public class Producer {
    public static void main(String[] args) throws Exception{
        //1.创建一个发送消息的对象Producer
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        //2.设定发送的命名服务器地址
        producer.setNamesrvAddr("192.168.37.140:9876");
        //3.启动发送的服务
        producer.start();
        //4.创建要发送的消息对象,指定topic,指定内容body
        for (int i = 1; i <= 10; i++) {
            Message msg  = new Message("topic3",("生产者2,hello rocketmq"+i).getBytes("UTF-8"));
            //5.发送消息
            SendResult sendResult = producer.send(msg);
            System.out.println("返回结果"+sendResult);
        }
        //6.关闭连接
        producer.shutdown();
    }
}
