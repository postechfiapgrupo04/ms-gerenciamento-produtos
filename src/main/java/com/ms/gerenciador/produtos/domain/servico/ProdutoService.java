package com.ms.gerenciador.produtos.domain.servico;

import com.ms.gerenciador.produtos.api.model.ProdutoAtualizaEstoqueRequest;
import com.ms.gerenciador.produtos.api.model.ProdutoRequest;
import com.ms.gerenciador.produtos.api.model.ProdutoResponse;
import com.ms.gerenciador.produtos.domain.exception.EntradaEstoqueInvalidaException;
import com.ms.gerenciador.produtos.domain.exception.ProdutoNaoEncontradoException;
import com.ms.gerenciador.produtos.domain.model.Produto;
import com.ms.gerenciador.produtos.domain.repositorio.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    private final ModelMapper modelMapper;

    public List<ProdutoResponse> listarTodos() {
        List<Produto> produtos = produtoRepository.findAll();
        return produtos.stream()
                .map(produto -> modelMapper.map(produto, ProdutoResponse.class))
                .collect(Collectors.toList());
    }

    public ProdutoResponse consultarPorId(Long id) {
        return modelMapper.map(buscarPorIdOuFalhar(id), ProdutoResponse.class);
    }

    private Produto buscarPorIdOuFalhar(Long id) {
        return produtoRepository.findById(id).orElseThrow(() -> new ProdutoNaoEncontradoException(id));
    }

    public ProdutoResponse adicionar(ProdutoRequest produtoRequest) {
        Produto produto = produtoRepository.save(modelMapper.map(produtoRequest, Produto.class));
        return modelMapper.map(produto, ProdutoResponse.class);
    }

    public ProdutoResponse atualizar(Long id, ProdutoRequest produtoRequest) {

        Produto produto = buscarPorIdOuFalhar(id);
        produto.setDescricao(produtoRequest.getDescricao());
        produto.setPreco(produtoRequest.getPreco());
        produto.setQuantidade(produtoRequest.getQuantidade());

        return modelMapper.map(produtoRepository.save(produto), ProdutoResponse.class);
    }

    public void deletar(Long id) {
        Produto produto = buscarPorIdOuFalhar(id);
        produtoRepository.delete(produto);
    }

    public ProdutoResponse entradaEstoque(Long id, ProdutoAtualizaEstoqueRequest produtoAtualizaEstoqueRequest)
            throws EntradaEstoqueInvalidaException {
        Produto produto = buscarPorIdOuFalhar(id);
        validarEntradaEstoque(produto, produtoAtualizaEstoqueRequest);

        produto.setQuantidade(produto.getQuantidade() + produtoAtualizaEstoqueRequest.getQuantidade());
        return modelMapper.map(produtoRepository.save(produto), ProdutoResponse.class);
    }

    public ProdutoResponse saidaEstoque(Long id, ProdutoAtualizaEstoqueRequest produtoAtualizaEstoqueRequest)
            throws EntradaEstoqueInvalidaException {
        Produto produto = buscarPorIdOuFalhar(id);
        validarSaidaEstoque(produto, produtoAtualizaEstoqueRequest);

        produto.setQuantidade(produto.getQuantidade() - produtoAtualizaEstoqueRequest.getQuantidade());
        return modelMapper.map(produtoRepository.save(produto), ProdutoResponse.class);
    }

    private void validarSaidaEstoque(Produto produto, ProdutoAtualizaEstoqueRequest produtoAtualizaEstoqueRequest)
            throws EntradaEstoqueInvalidaException {
        entradaNegativaInvalida(produto, produtoAtualizaEstoqueRequest);

        if(produtoAtualizaEstoqueRequest.getQuantidade() > produto.getQuantidade()){
            throw new EntradaEstoqueInvalidaException(
                    String.format("Quantidade de saida não pode ser maior do que o estoque atual %d, do produto %d",
                            produto.getQuantidade(), produto.getId()));
        }
    }


    public static void validarEntradaEstoque(Produto produto, ProdutoAtualizaEstoqueRequest produtoAtualizaEstoqueRequest)
            throws EntradaEstoqueInvalidaException {
        entradaNegativaInvalida(produto, produtoAtualizaEstoqueRequest);

        if (produtoAtualizaEstoqueRequest.getQuantidade() < produto.getQuantidade()) {
            throw new EntradaEstoqueInvalidaException(
                    String.format("Quantidade de entrada não pode ser menor do que o estoque atual %d, do produto %d",
                            produto.getQuantidade(), produto.getId()));
        }
    }

    private static void entradaNegativaInvalida(Produto produto, ProdutoAtualizaEstoqueRequest produtoAtualizaEstoqueRequest) throws EntradaEstoqueInvalidaException {
        if (produtoAtualizaEstoqueRequest.getQuantidade() < 0) {
            throw new EntradaEstoqueInvalidaException(
                    String.format("Quantidade de entrada não pode ser negativa para o produto %d", produto.getId()));
        }
    }
}
