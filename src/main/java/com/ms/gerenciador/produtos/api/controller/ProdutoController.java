package com.ms.gerenciador.produtos.api.controller;

import com.ms.gerenciador.produtos.api.model.ProdutoAtualizaEstoqueRequest;
import com.ms.gerenciador.produtos.api.model.ProdutoRequest;
import com.ms.gerenciador.produtos.api.model.ProdutoResponse;
import com.ms.gerenciador.produtos.domain.exception.EntradaEstoqueInvalidaException;
import com.ms.gerenciador.produtos.domain.servico.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @GetMapping
    public List<ProdutoResponse> listarTodos() {
        return produtoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.consultarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ProdutoResponse> adicionar(@RequestBody ProdutoRequest produto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.adicionar(produto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse> atualizar(@PathVariable Long id,
                                                     @RequestBody ProdutoRequest produtoRequest) {
        return ResponseEntity.ok(produtoService.atualizar(id, produtoRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/entrada-estoque")
    public ResponseEntity<ProdutoResponse> entradaEstoque(@PathVariable Long id,
                                                          @RequestBody ProdutoAtualizaEstoqueRequest produtoAtualizaEstoqueRequest)
            throws EntradaEstoqueInvalidaException {
        return ResponseEntity.ok(produtoService.entradaEstoque(id, produtoAtualizaEstoqueRequest));
    }


    @PostMapping("/{id}/saida-estoque")
    public ResponseEntity<ProdutoResponse> saidaEstoque(@PathVariable Long id,
                                                        @RequestBody ProdutoAtualizaEstoqueRequest produtoAtualizaEstoqueRequest)
            throws EntradaEstoqueInvalidaException {
        return ResponseEntity.ok(produtoService.saidaEstoque(id, produtoAtualizaEstoqueRequest));
    }
}