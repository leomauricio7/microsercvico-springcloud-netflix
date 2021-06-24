package com.github.leomauricio7.pagamento.config;

import com.github.leomauricio7.pagamento.data.dto.ProdutoDTO;
import com.github.leomauricio7.pagamento.entity.Produto;
import com.github.leomauricio7.pagamento.repository.ProdutoRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ProdutoReceiveMessage {

    private final ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoReceiveMessage(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    // metodo de escuta a fila do rabbitmq
    @RabbitListener(queues = {"${crud.rabbitmq.queue}"}) // anotação para escutar a fila do rabbitmq
    public void receive(@Payload ProdutoDTO produtoDTO){
        produtoRepository.save(Produto.create(produtoDTO));
    }
}
