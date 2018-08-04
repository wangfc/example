package com.github.comma.rabbitmq.example1_helloworld;

import com.github.comma.rabbitmq.Constants;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wangfc
 * @desciption
 * @date 2018/8/2
 */
@Slf4j
public class Producder {

    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(Constants.HOST_NAME);
        factory.setPort(Constants.PORT);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(Constants.QUEUE_NAME  , false, false, false, null);
        String message = "Hello World!";
        channel.basicPublish("", Constants.QUEUE_NAME, null, message.getBytes());
        log.debug(" [x] Sent '{}'",message);

        channel.close();
        connection.close();
    }
}
