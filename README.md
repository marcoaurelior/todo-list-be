# Backend Todo List

Este é um projeto backend para gerenciamento de tarefas, desenvolvido em Kotlin com o framework Spring Boot, utilizando
Java 21, Maven como gerenciador de dependências e banco de dados PostgreSQL rodando via Docker. O versionamento do banco
é feito com Liquibase, e os testes de integração utilizam Testcontainers para garantir confiabilidade e isolamento.

## Requisitos

Para executar esse projeto, você precisa ter as seguintes ferramentas pré-instaladas:

* Java (versão JDK 21)
* Maven
* Docker e Docker Compose
* IntelliJ IDEA
* Git

## Como executar

1 - Clone o repositório para a sua máquina usando git clone.

    git clone https://github.com/marcoaurelior/todo-list-be.git

2 - Com o docker em execução, execute o comando abaixo na raiz do projeto para subir o banco de dados (postgres)

Com o Docker em execução, suba o banco de dados PostgreSQL:

    docker-compose up

3 - Executando os testes

Para compilar e rodar todos os testes de integração (com dados pré-carregados via data.sql), execute:

    mvn clean install

3.1 - Se desejar pular os testes, utilize:

    mvn clean install -DskipTests

Isso compila e empacota o projeto sem executar os testes.

4 - Estrutura do Projeto

    src/main/kotlin – código-fonte principal

    src/test/kotlin – testes automatizados (unitários e de integração)

    docker-compose-up.sh – script para subir o banco via Docker

    src/main/resources/db/changelog/ – arquivos de changelog do Liquibase

    pom.xml – configurações de dependências e plugins