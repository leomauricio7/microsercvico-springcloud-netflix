package com.github.leomauricio7.pagamento.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.leomauricio7.pagamento.entity.ProdutoVenda;
import com.github.leomauricio7.pagamento.entity.Venda;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@JsonPropertyOrder({"id","data","produtos", "valorTotal"})
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
public class VendaDTO extends RepresentationModel<VendaDTO> implements Serializable {

    private static final long serialVersionUID = 4958620323932687908L;

    @JsonProperty("id")
    private Long id;

    @JsonProperty("data")
    private Date data;

    @JsonProperty("produtos")
    private List<ProdutoVendaDTO> produtos;

    @JsonProperty("valorTotal")
    private Double valorTotal;

    public static VendaDTO create(Venda venda){
        return new ModelMapper().map(venda, VendaDTO.class);
    }
}
