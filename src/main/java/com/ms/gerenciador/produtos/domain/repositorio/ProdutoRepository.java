package com.ms.gerenciador.produtos.domain.repositorio;

import com.ms.gerenciador.produtos.domain.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> { }

