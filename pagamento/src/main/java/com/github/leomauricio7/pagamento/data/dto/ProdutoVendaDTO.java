package com.github.leomauricio7.pagamento.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.leomauricio7.pagamento.entity.ProdutoVenda;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@Data
@JsonPropertyOrder({"id","idProduto", "quantidade"})
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
public class ProdutoVendaDTO extends RepresentationModel<ProdutoVendaDTO> implements Serializable {

    private static final long serialVersionUID = 2364082830909642500L;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("idProduto")
    private Long idProduto;

    @JsonProperty("quantidade")
    private Integer quantidade;


    public static ProdutoVendaDTO create(ProdutoVenda produtoVenda){
        return new ModelMapper().map(produtoVenda, ProdutoVendaDTO.class);
    }
}
