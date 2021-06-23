package com.github.leomauricio.crud.controller;

import com.github.leomauricio.crud.data.dto.ProdutoDTO;
import com.github.leomauricio.crud.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    private final ProdutoService produtoService;
    private final PagedResourcesAssembler pagedResourcesAssembler;

    @Autowired
    public ProdutoController(ProdutoService produtoService, PagedResourcesAssembler pagedResourcesAssembler) {
        this.produtoService = produtoService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml"})
    public ProdutoDTO findById(@PathVariable("id") Long id){
        ProdutoDTO produtoDTO = produtoService.findById(id);
        produtoDTO.add(linkTo(methodOn(ProdutoController.class).findById(id)).withSelfRel());
        return produtoDTO;
    }

    @GetMapping(produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<?> findAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "12") int limit,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ){
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection,"nome"));

        Page<ProdutoDTO> produtos = produtoService.findAll(pageable);

        produtos.stream()
                .forEach( p -> p.add(linkTo(methodOn(ProdutoController.class).findById(p.getId())).withSelfRel()));

        PagedModel<EntityModel<ProdutoDTO>> pageModel = pagedResourcesAssembler.toModel(produtos);

        return new ResponseEntity<>(pageModel, HttpStatus.OK);
    }

    @PostMapping(
            produces = {"application/json", "application/xml", "application/x-yaml"},
            consumes = {"application/json", "application/xml", "application/x-yaml"})
    public ProdutoDTO create(@RequestBody ProdutoDTO produtoDTO){
        ProdutoDTO prodDto =  produtoService.create(produtoDTO);
        prodDto.add(linkTo(methodOn(ProdutoController.class).findById(prodDto.getId())).withSelfRel());
        return prodDto;
    }

    @PutMapping(
            produces = {"application/json", "application/xml", "application/x-yaml"},
            consumes = {"application/json", "application/xml", "application/x-yaml"})
    public ProdutoDTO update(@RequestBody ProdutoDTO produtoDTO){
        ProdutoDTO prodDto =  produtoService.update(produtoDTO);
        prodDto.add(linkTo(methodOn(ProdutoController.class).findById(prodDto.getId())).withSelfRel());
        return prodDto;
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        produtoService.delete(id);
        return  ResponseEntity.ok().build();
    }


}