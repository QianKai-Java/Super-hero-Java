package com.next.api;

import com.next.pojo.bo.MovieMQMessageBo;
import com.next.utils.JsonUtils;
import com.next.utils.MoviePushUtil;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConsumer {
    @RabbitListener(queues = {RabbitMQConfig.QUEUE_NAME_PUSH})
    public void listMQPush(String payload, Message message){
        System.out.println(payload);
        MovieMQMessageBo movieMQMessageBo = JsonUtils.jsonToPojo(payload, MovieMQMessageBo.class);
        MoviePushUtil.doPush(movieMQMessageBo.getTitle(),movieMQMessageBo.getText());
    }
}
