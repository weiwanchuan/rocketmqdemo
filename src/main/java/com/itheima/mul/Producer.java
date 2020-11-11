package com.itheima.mul;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.util.ArrayList;
import java.util.List;

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
        //创建一个集合保存多个消息
        List<Message> msgList = new ArrayList<Message>();

        Message msg1 = new Message("topic6",("批量消息：hello rocketmq "+1).getBytes("UTF-8"));
        Message msg2 = new Message("topic6",("批量消息：hello rocketmq "+2).getBytes("UTF-8"));
        Message msg3 = new Message("topic6",("批量消息：hello rocketmq "+3).getBytes("UTF-8"));

        msgList.add(msg1);
        msgList.add(msg2);
        msgList.add(msg3);

        //发送批量消息(每次发送的消息总量不得超过4M）
        //消息的总长度包含4个信息：topic,body,消息的属性,日志（20字节）
        SendResult send = producer.send(msgList);
        System.out.println(send);
        //6.关闭连接
        producer.shutdown();
    }
}
