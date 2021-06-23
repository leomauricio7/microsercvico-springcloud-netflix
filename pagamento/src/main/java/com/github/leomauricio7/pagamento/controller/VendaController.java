package com.github.leomauricio7.pagamento.controller;

import com.github.leomauricio7.pagamento.data.dto.VendaDTO;
import com.github.leomauricio7.pagamento.services.VendaService;
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
@RequestMapping("/venda")
public class VendaController {

    private final VendaService vendaService;
    private final PagedResourcesAssembler<VendaDTO> pagedResourcesAssembler;

    @Autowired
    public VendaController(VendaService vendaService, PagedResourcesAssembler pagedResourcesAssembler) {
        this.vendaService = vendaService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml"})
    public VendaDTO findById(@PathVariable("id") Long id){
        VendaDTO produtoDTO = vendaService.findById(id);
        produtoDTO.add(linkTo(methodOn(VendaController.class).findById(id)).withSelfRel());
        return produtoDTO;
    }

    @GetMapping(produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<?> findAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "12") int limit,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ){
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection,"data"));

        Page<VendaDTO> produtos = vendaService.findAll(pageable);

        produtos.stream()
                .forEach( p -> p.add(linkTo(methodOn(VendaController.class).findById(p.getId())).withSelfRel()));

        PagedModel<EntityModel<VendaDTO>> pageModel = pagedResourcesAssembler.toModel(produtos);

        return new ResponseEntity<>(pageModel, HttpStatus.OK);
    }

    @PostMapping(
            produces = {"application/json", "application/xml", "application/x-yaml"},
            consumes = {"application/json", "application/xml", "application/x-yaml"})
    public VendaDTO create(@RequestBody VendaDTO vendaDTO){
        VendaDTO prodDto =  vendaService.create(vendaDTO);
        prodDto.add(linkTo(methodOn(VendaController.class).findById(prodDto.getId())).withSelfRel());
        return prodDto;
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        vendaService.delete(id);
        return  ResponseEntity.ok().build();
    }
}
