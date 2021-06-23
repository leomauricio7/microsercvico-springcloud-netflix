package com.github.leomauricio.crud.service;

import com.github.leomauricio.crud.data.dto.ProdutoDTO;
import com.github.leomauricio.crud.entity.Produto;
import com.github.leomauricio.crud.exception.ResourceNotFoundException;
import com.github.leomauricio.crud.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public ProdutoDTO create(ProdutoDTO produtoDTO){
        ProdutoDTO prodDtoRt = ProdutoDTO.create(produtoRepository.save(Produto.create(produtoDTO)));
        return prodDtoRt;
    }

    public Page<ProdutoDTO> findAll(Pageable pageable){
        var page = produtoRepository.findAll(pageable);
        return page.map(this::convertToProdutoDTO);
    }

    private ProdutoDTO convertToProdutoDTO(Produto produto){
        return ProdutoDTO.create(produto);
    }

    public ProdutoDTO findById(Long id){
        var entity =  produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado."));
        return ProdutoDTO.create(entity);
    }

    public ProdutoDTO update(ProdutoDTO produtoDTO){
        final Optional<Produto> optionalProduto =  produtoRepository.findById(produtoDTO.getId());

        if(!optionalProduto.isPresent()){
            new ResourceNotFoundException("Produto não encontrado.");
        }

        return  ProdutoDTO.create(produtoRepository.save(Produto.create(produtoDTO)));
    }

    public void delete(Long id){
        var entity =  produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado."));
       produtoRepository.delete(entity);
    }
}
