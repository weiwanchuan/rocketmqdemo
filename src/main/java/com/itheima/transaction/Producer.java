package com.itheima.transaction;

import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * 事务:生产者,产生消息
 */
public class Producer {
    public static void main1(String[] args) throws Exception {
        //事务消息使用的产生着是
        TransactionMQProducer producer = new TransactionMQProducer("group1");
        //设置ip,端口
        producer.setNamesrvAddr("192.168.37.140:9876");
        //添加本地事务对应的监听
        producer.setTransactionListener(new TransactionListener() {
            //正常事务过程
            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object o) {
                //中间过程状态
                return LocalTransactionState.UNKNOW;
            }
            //事务补偿过程
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                System.out.println("事务补偿...");
                //补偿提交
                //return LocalTransactionState.COMMIT_MESSAGE;
                //补偿回滚
                return LocalTransactionState.ROLLBACK_MESSAGE;
            }
        });
        producer.start();
        Message msg = new Message("tranTopic", "事务消息:hello".getBytes());
        TransactionSendResult result = producer.sendMessageInTransaction(msg, null);
        System.out.println(result);
        //事务补偿过程必须保障服务器在运行过程中，否则将无法进行正常的事务补偿
        //producer.shutdown();
    }
    public static void main(String[] args) throws Exception {
        //事务消息使用的产生着是
        TransactionMQProducer producer = new TransactionMQProducer("group1");
        //设置ip,端口
        producer.setNamesrvAddr("192.168.37.140:9876");
        //添加本地事务对应的监听
        producer.setTransactionListener(new TransactionListener() {
            //正常事务过程
            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object o) {
                //事务提交过程
                return LocalTransactionState.COMMIT_MESSAGE;
            }
            //事务补偿过程
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                return null;
            }
        });
        producer.start();
        Message msg = new Message("tranTopic", "事务消息:hello".getBytes());
        TransactionSendResult result = producer.sendMessageInTransaction(msg, null);
        System.out.println(result);
        producer.shutdown();
    }
    public static void main2(String[] args) throws Exception {
        //事务消息使用的产生着是
        TransactionMQProducer producer = new TransactionMQProducer("group1");
        //设置ip,端口
        producer.setNamesrvAddr("192.168.37.140:9876");
        //添加本地事务对应的监听
        producer.setTransactionListener(new TransactionListener() {
            //正常事务过程
            @Override
            public LocalTransactionState executeLocalTransaction(Message message, Object o) {
                //事务回滚过程(如果是回滚,那么消息将在mq中被删除,消费方就消费不了)
                return LocalTransactionState.ROLLBACK_MESSAGE;
            }
            //事务补偿过程
            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
                return null;
            }
        });
        producer.start();
        Message msg = new Message("tranTopic", "事务消息:hello".getBytes());
        TransactionSendResult result = producer.sendMessageInTransaction(msg, null);
        System.out.println(result);
        producer.shutdown();
    }
}
