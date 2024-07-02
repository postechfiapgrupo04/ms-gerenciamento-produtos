package com.ms.gerenciador.produtos.api;

import com.ms.gerenciador.produtos.api.model.ExceptionResponse;
import com.ms.gerenciador.produtos.domain.exception.EntradaEstoqueInvalidaException;
import com.ms.gerenciador.produtos.domain.exception.ProdutoNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ProdutoNaoEncontradoException.class)
    public ResponseEntity<ExceptionResponse> produtoNaoEncontradoExceptionHandle(ProdutoNaoEncontradoException e) {
        ExceptionResponse response = ExceptionResponse.builder()
                .mensagem(e.getMessage())
                .dataHora(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ExceptionResponse> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        ExceptionResponse response = ExceptionResponse.builder()
                .mensagem(e.getMessage())
                .dataHora(LocalDateTime.now())
                .build();

        return ResponseEntity.status(e.getStatusCode()).body(response);
    }
    @ExceptionHandler(EntradaEstoqueInvalidaException.class)
    public ResponseEntity<ExceptionResponse> entradaEstoqueInvalidaException(EntradaEstoqueInvalidaException e) {
        ExceptionResponse response = ExceptionResponse.builder()
                .mensagem(e.getMessage())
                .dataHora(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
