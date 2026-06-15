# Sistema-produtos-150626

# Sistema de Gestão de Produtos - Evolução Profissional


# Sistema de Gestão de Produtos - Evolução Profissional

## 📝 Descrição do Projeto
Evolução de uma aplicação acadêmica para um ambiente profissional. O projeto original, que consistia em uma simulação de backend via terminal com dados armazenados em memória, foi totalmente reestruturado. A solução atual implementa uma API REST verdadeira utilizando Spring Boot, com persistência de dados em um banco de dados PostgreSQL real. O frontend foi modernizado com uma interface responsiva, e toda a arquitetura de execução (backend e banco de dados) foi containerizada utilizando Docker e redes virtuais para garantir a comunicação isolada e eficiente.

## 👥 Integrantes da Equipe
* João Vitor Araújo da Silva
* Bruno da Silva Basilio
* Diogo Renovato

## 🛠️ Tecnologias Utilizadas
* **Frontend:** HTML5, CSS3, JavaScript e Bootstrap 5
* **Backend:** Java 17, Spring Boot, Spring Data JPA
* **Banco de Dados:** PostgreSQL
* **Infraestrutura e DevOps:** Docker, Docker Compose e Docker Network

## 🚀 Instruções para Execução da Aplicação

### Pré-requisitos
* Ter o [Docker](https://www.docker.com/) e o [Docker Compose](https://docs.docker.com/compose/) instalados na máquina.

### Passos para rodar localmente

1. **Clonar o repositório:**
   ```bash
   git clone [URL_DO_SEU_REPOSITORIO]
   cd [NOME_DA_PASTA_DO_REPOSITORIO]

### 2. `docker-compose.yml`
```yaml
version: '3.8'
services:
  postgres:
    image: postgres:latest
    container_name: db-postgres
    environment:
      POSTGRES_USER: admin
      POSTGRES_PASSWORD: admin
      POSTGRES_DB: produtos_db
    ports:
      - "5432:5432"
    networks:
      - projeto-network

  backend:
    build: ./backend
    container_name: api-produtos
    ports:
      - "8080:8080"
    depends_on:
      - postgres
    environment:
      DB_HOST: postgres
      DB_USER: admin
      DB_PASS: admin
      DB_NAME: produtos_db
    networks:
      - projeto-network

networks:
  projeto-network:
    driver: bridge


HTML 
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestão de Produtos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<nav class="navbar navbar-dark bg-primary shadow-sm">
    <div class="container">
        <span class="navbar-brand mb-0 h1">📦 Sistema de Gestão de Produtos</span>
    </div>
</nav>

<div class="container mt-5">
    <div class="row">
        <div class="col-md-4 mb-4">
            <div class="card shadow-sm">
                <div class="card-header bg-white">
                    <h5 class="mb-0" id="formTitle">Novo Produto</h5>
                </div>
                <div class="card-body">
                    <form id="produtoForm">
                        <input type="hidden" id="produtoId">
                        <div class="mb-3">
                            <label class="form-label">Nome do Produto</label>
                            <input type="text" class="form-control" id="nome" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Preço (R$)</label>
                            <input type="number" step="0.01" class="form-control" id="preco" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Estoque</label>
                            <input type="number" class="form-control" id="estoque" required>
                        </div>
                        <button type="submit" class="btn btn-primary w-100">Salvar</button>
                    </form>
                    <div id="mensagem" class="mt-3"></div>
                </div>
            </div>
        </div>

        <div class="col-md-8">
            <div class="card shadow-sm">
                <div class="card-body">
                    <h5 class="card-title mb-3">Produtos Cadastrados</h5>
                    <div class="table-responsive">
                        <table class="table table-hover align-middle">
                            <thead class="table-light">
                                <tr>
                                    <th>ID</th>
                                    <th>Nome</th>
                                    <th>Preço</th>
                                    <th>Estoque</th>
                                    <th>Ações</th>
                                </tr>
                            </thead>
                            <tbody id="tabelaProdutos"></tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    const API_URL = 'http://localhost:8080/produtos';

    async function carregarProdutos() {
        const response = await fetch(API_URL);
        const produtos = await response.json();
        const tbody = document.getElementById('tabelaProdutos');
        tbody.innerHTML = '';
        produtos.forEach(p => {
            tbody.innerHTML += `
                <tr>
                    <td>${p.id}</td>
                    <td>${p.nome}</td>
                    <td>R$ ${p.preco.toFixed(2)}</td>
                    <td>${p.estoque}</td>
                    <td>
                        <button class="btn btn-sm btn-warning" onclick="editarProduto(${p.id}, '${p.nome}', ${p.preco}, ${p.estoque})">Editar</button>
                        <button class="btn btn-sm btn-danger" onclick="excluirProduto(${p.id})">Excluir</button>
                    </td>
                </tr>`;
        });
    }

    document.getElementById('produtoForm').addEventListener('submit', async (e) => {
        e.preventDefault();
        const id = document.getElementById('produtoId').value;
        const produto = {
            nome: document.getElementById('nome').value,
            preco: parseFloat(document.getElementById('preco').value),
            estoque: parseInt(document.getElementById('estoque').value)
        };
        const method = id ? 'PUT' : 'POST';
        const url = id ? `${API_URL}/${id}` : API_URL;

        const response = await fetch(url, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(produto)
        });

        if (response.ok) {
            mostrarMensagem('Produto salvo com sucesso!', 'success');
            document.getElementById('produtoForm').reset();
            document.getElementById('produtoId').value = '';
            carregarProdutos();
        } else {
            const erro = await response.text();
            mostrarMensagem(erro, 'danger');
        }
    });

    async function excluirProduto(id) {
        if (confirm('Tem certeza que deseja excluir?')) {
            await fetch(`${API_URL}/${id}`, { method: 'DELETE' });
            carregarProdutos();
            mostrarMensagem('Produto excluído.', 'success');
        }
    }

    function editarProduto(id, nome, preco, estoque) {
        document.getElementById('produtoId').value = id;
        document.getElementById('nome').value = nome;
        document.getElementById('preco').value = preco;
        document.getElementById('estoque').value = estoque;
    }

    function mostrarMensagem(texto, tipo) {
        const div = document.getElementById('mensagem');
        div.innerHTML = `<div class="alert alert-${tipo}">${texto}</div>`;
        setTimeout(() => div.innerHTML = '', 3000);
    }

    carregarProdutos();
</script>
</body>
</html>


docker

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]


pom.xml


<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.0</version>
    </parent>
    <groupId>com.sistema</groupId>
    <artifactId>produtos</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <properties>
        <java.version>17</java.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <scope>runtime</scope>
        </dependency>
    </dependencies>
</project>

properties

spring.datasource.url=jdbc:postgresql://${DB_HOST:localhost}:5432/${DB_NAME:produtos_db}
spring.datasource.username=${DB_USER:admin}
spring.datasource.password=${DB_PASS:admin}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
server.port=8080


main

package com.sistema.produtos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SistemaProdutosApplication {
    public static void main(String[] args) {
        SpringApplication.run(SistemaProdutosApplication.class, args);
    }
}

main
package com.sistema.produtos.model;

import jakarta.persistence.*;

@Entity
@Table(name = "produtos")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false)
    private Double preco;

    @Column(nullable = false)
    private Integer estoque;

    public Produto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Double getPreco() { return preco; }
    public void setPreco(Double preco) { this.preco = preco; }
    public Integer getEstoque() { return estoque; }
    public void setEstoque(Integer estoque) { this.estoque = estoque; }
}


main

package com.sistema.produtos.repository;

import com.sistema.produtos.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    boolean existsByNomeIgnoreCase(String nome);
}


main

package com.sistema.produtos.repository;

import com.sistema.produtos.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    boolean existsByNomeIgnoreCase(String nome);
}

main

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


