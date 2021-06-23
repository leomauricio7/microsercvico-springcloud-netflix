package com.github.leomauricio7.pagamento.entity;

import com.github.leomauricio7.pagamento.data.dto.ProdutoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.persistence.*;


@Entity
@Table(name = "produto")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Produto {

    @Id
    private Long id;

    @Column(name = "estoque", nullable = false, length = 10)
    private Integer estoque;

    public static Produto create(ProdutoDTO produtoDTO){
        return new ModelMapper().map(produtoDTO, Produto.class);
    }

}
