package com.github.comma.rabbitmq.example2_workqueues;

import com.github.comma.rabbitmq.Constants;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wangfc
 * @date 2018-08-04 9:53
 */
@Slf4j
public class NewTask {

    public static void main(String[] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(Constants.HOST_NAME);
        factory.setPort(Constants.PORT);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(Constants.QUEUE_NAME,true,false,false,null);

        String[] messages = {"a1",".","a2.",".","a3."};
        String message = getMessage(messages);
        log.debug("[x] Sent '{}'",message) ;
        channel.basicPublish("", Constants.QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        channel.close();
        connection.close();

    }

    private static String getMessage(String[] strings){
        if (strings.length < 1)
            return "Hello World!";
        return joinStrings(strings, " ");
    }

    private static String joinStrings(String[] strings, String delimiter) {
        int length = strings.length;
        if (length == 0) return "";
        StringBuilder words = new StringBuilder(strings[0]);
        for (int i = 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }
        return words.toString();
    }

}