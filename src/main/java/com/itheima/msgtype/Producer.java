package com.itheima.msgtype;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.util.concurrent.TimeUnit;

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
            Message msg  = new Message("topic5","单项消息:hello rocketmq".getBytes("UTF-8"));
            //5.发送消息
            //异步发送
//            producer.send(msg, new SendCallback() {
//                //表示结果返回成功
//                @Override
//                public void onSuccess(SendResult sendResult) {
//                    System.out.println(sendResult);
//                }
//                //表示结果返回失败
//                @Override
//                public void onException(Throwable throwable) {
//                    System.out.println(throwable);
//                }
//            });
            producer.sendOneway(msg);

        }
        //休眠10秒,使异步请求发送成功
        TimeUnit.SECONDS.sleep(10);
        //6.关闭连接
        producer.shutdown();
    }
}
