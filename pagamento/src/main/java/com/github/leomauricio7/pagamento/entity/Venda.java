package com.github.leomauricio7.pagamento.entity;

import com.github.leomauricio7.pagamento.data.dto.ProdutoVendaDTO;
import com.github.leomauricio7.pagamento.data.dto.VendaDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="venda")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Venda implements Serializable {

    private static final long serialVersionUID = -8555504067018026356L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(pattern = "MM/dd/yyyy")
    @Column(name = "data", nullable = false, length = 255)
    private Date data;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "venda", cascade = {CascadeType.REMOVE})
    private List<ProdutoVenda> produtos;

    @Column(name = "valorTotal", nullable = false, length = 10)
    private Double valorTotal;

    public static Venda create(VendaDTO vendaDTO){
        return new ModelMapper().map(vendaDTO, Venda.class);
    }

}
