package com.github.leomauricio7.crud.message;

import com.github.leomauricio7.crud.data.dto.ProdutoDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
// classe de encio de message para o rabbitmq
public class ProdutoSendMessage {

    @Value("${crud.rabbitmq.exchange}")
    String exchange;

    @Value("${crud.rabbitmq.routingkey}")
    String routingkey;

    public final RabbitTemplate rabbitTemplate;

    @Autowired
    public ProdutoSendMessage(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    // metodo de envio de messages
    public void sendMessage(ProdutoDTO produtoDTO){
        rabbitTemplate.convertAndSend(exchange, routingkey, produtoDTO);
    }

}
