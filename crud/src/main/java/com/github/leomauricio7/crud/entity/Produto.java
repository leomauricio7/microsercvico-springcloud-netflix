package com.github.leomauricio7.crud.entity;

import com.github.leomauricio7.crud.data.dto.ProdutoDTO;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="produto")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString

public class Produto implements Serializable {

    private static final long serialVersionUID = -5739495078527883468L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nome", nullable = false, length = 255)
    private String nome;

    @Column(name="estoque", nullable = false, length = 10)
    private Integer estoque;

    @Column(name="preco", nullable = false, length = 10)
    private Double preco;

    // converte uma prdutoDTO para produto com o ModelMapper
    public static Produto create(ProdutoDTO produtoDTO){
        return new ModelMapper().map(produtoDTO, Produto.class);
    }
}

