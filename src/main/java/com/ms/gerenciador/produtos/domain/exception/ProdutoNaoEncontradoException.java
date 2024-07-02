package com.ms.gerenciador.produtos.domain.exception;

public class ProdutoNaoEncontradoException extends RuntimeException {
    public ProdutoNaoEncontradoException(Long id) {
        super(String.format("Produto com codigo %d não encontrado!", id));
    }
}
