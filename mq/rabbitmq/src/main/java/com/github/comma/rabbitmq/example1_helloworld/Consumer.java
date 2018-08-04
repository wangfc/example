package com.github.comma.rabbitmq.example1_helloworld;

import com.github.comma.rabbitmq.Constants;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author wangfc
 * @desciption
 * @date 2018/8/2
 */
@Slf4j
public class Consumer {

    public static void main(String[] args) throws Exception{

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(Constants.HOST_NAME);
        factory.setPort(Constants.PORT);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                log.debug(" [x] Received '{}'",message);
            }
        };
        channel.basicConsume(Constants.QUEUE_NAME, true, consumer);
        channel.close();
        connection.close();
    }
}
