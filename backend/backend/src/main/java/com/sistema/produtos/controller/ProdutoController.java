package com.sistema.produtos.controller;

import com.sistema.produtos.model.Produto;
import com.sistema.produtos.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*") 
public class ProdutoController {

    @Autowired
    private ProdutoRepository repository;

    @GetMapping
    public ResponseEntity<List<Produto>> listarTodos() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    public ResponseEntity<?> cadastrarProduto(@RequestBody Produto produto) {
        if (produto.getNome() == null || produto.getNome().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Nome é obrigatório.");
        }
        if (produto.getPreco() == null || produto.getPreco() <= 0) {
            return ResponseEntity.badRequest().body("Preço deve ser positivo.");
        }
        if (produto.getEstoque() == null || produto.getEstoque() < 0) {
            return ResponseEntity.badRequest().body("Estoque não pode ser negativo.");
        }
        if (repository.existsByNomeIgnoreCase(produto.getNome())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Produto já cadastrado.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(produto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarProduto(@PathVariable Long id, @RequestBody Produto produtoAtualizado) {
        return repository.findById(id).map(produto -> {
            if (produtoAtualizado.getNome() == null || produtoAtualizado.getNome().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Nome é obrigatório.");
            }
            if (!produto.getNome().equalsIgnoreCase(produtoAtualizado.getNome()) && 
                repository.existsByNomeIgnoreCase(produtoAtualizado.getNome())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Produto já cadastrado com este nome.");
            }
            produto.setNome(produtoAtualizado.getNome());
            produto.setPreco(produtoAtualizado.getPreco());
            produto.setEstoque(produtoAtualizado.getEstoque());
            return ResponseEntity.ok(repository.save(produto));
        }).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarProduto(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
        }
        repository.deleteById(id);
        return ResponseEntity.ok("Produto deletado com sucesso.");
    }
}
