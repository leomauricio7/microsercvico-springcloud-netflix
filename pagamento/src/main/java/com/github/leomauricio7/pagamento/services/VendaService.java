package com.github.leomauricio7.pagamento.services;

import com.github.leomauricio7.pagamento.data.dto.VendaDTO;
import com.github.leomauricio7.pagamento.entity.ProdutoVenda;
import com.github.leomauricio7.pagamento.entity.Venda;
import com.github.leomauricio7.pagamento.repository.ProdutoVendaRepository;
import com.github.leomauricio7.pagamento.repository.VendaRepository;
import com.github.leomauricio7.pagamento.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VendaService {

    private final VendaRepository vendaRepository;
    private final ProdutoVendaRepository produtoVendaRepository;

    @Autowired
    public VendaService(VendaRepository vendaRepository, ProdutoVendaRepository produtoVendaRepository) {
        this.vendaRepository = vendaRepository;
        this.produtoVendaRepository = produtoVendaRepository;
    }

    public VendaDTO create(VendaDTO vendaDTO){
        // salva a venda
        Venda venda = vendaRepository.save(Venda.create(vendaDTO));
        // cria uma lista de produtos da venda
        List<ProdutoVenda> produtosSalvos =  new ArrayList<>();
        // percorre a lista de produtos
        vendaDTO.getProdutos().forEach( p -> {
            // cria um objeto produto venda
            ProdutoVenda pv = ProdutoVenda.create(p);
            // seta a venda no objeto produto venda
            pv.setVenda(venda); // seta a venda
            // adcionar na lista de produtos salvos o produto venda
            produtosSalvos.add(produtoVendaRepository.save(pv));
        });
        // seta os produtos na venda
        venda.setProdutos(produtosSalvos);
        // retorna a venda criada
        return VendaDTO.create(venda);
    }

    public Page<VendaDTO> findAll(Pageable pageable){
        var page = vendaRepository.findAll(pageable);
        return page.map(this::convertToVendaDTO);
    }

    public VendaDTO findById(Long id){
        var entity =  vendaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venda não encontrada."));
        return VendaDTO.create(entity);
    }

    public void delete(Long id){
        var entity =  vendaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venda não encontrada."));
        vendaRepository.delete(entity);
    }

    private VendaDTO convertToVendaDTO(Venda venda){
        return VendaDTO.create(venda);
    }
}
