package com.itheima.order;

import com.itheima.order.domain.Order;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.ArrayList;
import java.util.List;

/**
 * 顺序,生产者,产生消息
 */
public class Producer {
    public static void main(String[] args) throws Exception{
        //1.创建一个发送消息的对象Producer
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        //2.设定发送的命名服务器地址
        producer.setNamesrvAddr("192.168.37.140:9876");
        //3.启动发送的服务
        producer.start();
        //创建要执行的业务队列
        List<Order> orderList = new ArrayList<>();
        Order order11 = new Order();
        order11.setId("a");
        order11.setMsg("主单-1");
        orderList.add(order11);

        Order order12 = new Order();
        order12.setId("a");
        order12.setMsg("子单-2");
        orderList.add(order12);

        Order order13 = new Order();
        order13.setId("a");
        order13.setMsg("支付-3");
        orderList.add(order13);

        Order order14 = new Order();
        order14.setId("a");
        order14.setMsg("推送-4");
        orderList.add(order14);

        Order order21 = new Order();
        order21.setId("b");
        order21.setMsg("主单-1");
        orderList.add(order21);

        Order order22 = new Order();
        order22.setId("b");
        order22.setMsg("子单-2");
        orderList.add(order22);

        Order order31 = new Order();
        order31.setId("c");
        order31.setMsg("主单-1");
        orderList.add(order31);

        Order order32 = new Order();
        order32.setId("c");
        order32.setMsg("子单-2");
        orderList.add(order32);

        Order order33 = new Order();
        order33.setId("c");
        order33.setMsg("支付-3");
        orderList.add(order33);
        //设置消息进入到指定的消息队列中
        for (Order order : orderList) {
            Message msg = new Message("orderTopic",order.toString().getBytes());
            //发送时要指定对应的消息队列选择器
            SendResult result = producer.send(msg, new MessageQueueSelector() {
                /**
                 * 队列消息
                 * @param list
                 * @param message
                 * @param o
                 * @return
                 */
                @Override
                //设置当前消息发送时使用哪个队列,一个topic中默认有四个队列,0~3
                public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                    //根据发送消息的不同,选择不同的消息队列
                    //根据id的哈希值对消息队列的长度取余来计算具体的消息队列
                    int index = order.getId().hashCode() % list.size();
                    //将选择好的消息队列返回
                    return list.get(index);
                }
            }, null);
            System.out.println(result);
        }
        //6.关闭连接
        producer.shutdown();
    }
}
