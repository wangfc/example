package com.github.comma.rabbitmq.example2_workqueues;

import com.github.comma.rabbitmq.Constants;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author wangfc
 * @date 2018-08-04 9:57
 */
@Slf4j
public class Work {

    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(Constants.HOST_NAME);
        factory.setPort(Constants.PORT);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        final Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");

                log.debug(" [x] Received '{}'",message);
                try {
                        doWork(message);
                    } catch (InterruptedException e) {
                        log.error("error: {} ",e);

                } finally {
                    log.debug(" [x] Done");
                }
            }
        };
        boolean autoAck = true; // acknowledgment is covered below
        channel.basicConsume(Constants.QUEUE_NAME, autoAck, consumer);
    }

    private static void doWork(String task) throws InterruptedException {
        for (char ch: task.toCharArray()) {
            if (ch == '.') Thread.sleep(3000);
        }
    }

}