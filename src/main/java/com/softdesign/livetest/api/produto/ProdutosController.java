package com.softdesign.livetest.api.produto;

import com.softdesign.livetest.domain.produto.ProdutoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URISyntaxException;
import java.util.List;

@RestController
public class ProdutosController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutosController.class);

    private final ProdutoService produtoService;

    public ProdutosController(
            ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping("/produtos")
    public ResponseEntity<List<ProdutoDTO>> listAll() {
        var produtos = produtoService.listAll();
        return ResponseEntity.ok().body(ProdutoDTO.from(produtos));
    }

    @GetMapping("/produtos/{id}")
    public ResponseEntity<ProdutoDTO> get(@PathVariable("id") String id) {
        var produto = produtoService.getById(id);
        return ResponseEntity.ok().body(ProdutoDTO.from(produto));
    }

    @PutMapping("/produtos/{id}")
    public ResponseEntity<ProdutoDTO> update(@PathVariable("id") String id, @RequestBody UpdateProdutoRequest updateProdutoRequest) {
        var produto = produtoService.update(updateProdutoRequest.toProduto(id));
        return ResponseEntity.ok().body(ProdutoDTO.from(produto));
    }

    @PostMapping("/produtos")
    public ResponseEntity<ProdutoDTO> create(@RequestBody CreateProdutoRequest createProdutoRequest) throws URISyntaxException {
        var produto = produtoService.create(createProdutoRequest.toProduto());
        var createdUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(produto.id()).toUri();
        return ResponseEntity.created(createdUri).body(ProdutoDTO.from(produto));
    }

}
