package com.ms.gerenciador.produtos.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProdutoResponse {
    private Long id;
    private String descricao;
    private BigDecimal preco;
    private Integer quantidade;
}
