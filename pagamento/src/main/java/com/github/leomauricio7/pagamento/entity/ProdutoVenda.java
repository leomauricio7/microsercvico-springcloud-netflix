package com.github.leomauricio7.pagamento.entity;

import com.github.leomauricio7.pagamento.data.dto.ProdutoDTO;
import com.github.leomauricio7.pagamento.data.dto.ProdutoVendaDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "produto_venda")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProdutoVenda implements Serializable {

    private static final long serialVersionUID = 8345778807600636647L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "idProduto", nullable = false, length = 10)
    private Long idProduto;

    @Column(name = "quantidade", nullable = false, length = 10)
    private Integer quantidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_venda")
    private Venda venda;

    public static ProdutoVenda create(ProdutoVendaDTO produtoVendaDTO){
        return new ModelMapper().map(produtoVendaDTO, ProdutoVenda.class);
    }
}
