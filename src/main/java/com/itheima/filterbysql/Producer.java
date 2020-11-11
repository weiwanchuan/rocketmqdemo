package com.itheima.filterbysql;

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
        for (int i = 1; i <= 10; i++) {
            //4.创建要发送的消息对象,指定topic,指定内容body
            Message msg  = new Message("topic8","消息过滤sql:hello rocketmq".getBytes("UTF-8"));
            //为消息添加属性
            msg.putUserProperty("vip","1");
            //msg.putUserProperty("age","18");
            //5.发送消息
            SendResult sendResult = producer.send(msg);
            System.out.println("返回结果"+sendResult);
        }
        //6.关闭连接
        producer.shutdown();
    }
}
