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
* Ter o Docker e o Docker Compose instalados na máquina.

### Passos para rodar localmente

1. **Clonar o repositório:**
   ```bash
   git clone [URL_DO_SEU_REPOSITORIO]
   cd [NOME_DA_PASTA_DO_REPOSITORIO]
2. Subir a infraestrutura via Docker Compose:
  docker-compose up -d --build

3.Acessar a Aplicação:
​Backend (API): porta 8080.
​Banco de Dados (PostgreSQL): porta 5432.
​Frontend: Abra o arquivo index.html no navegador.
