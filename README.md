# Employee CRUD

Projeto de CRUD de funcionários desenvolvido com Spring Boot, Feign Client e PostgreSQL.

## Sumário

- [Descrição](#descrição)
- [Tecnologias](#tecnologias)
- [Pré-requisitos](#pré-requisitos)
- [Como rodar](#como-rodar)
- [Como testar](#como-testar)
- [Configurações importantes](#configurações-importantes)
- [Cobertura de testes](#cobertura-de-testes)
- [Contribuições](#contribuições)
- [Licença](#licença)

## Descrição

Este projeto implementa um sistema CRUD para gerenciamento de funcionários, utilizando Spring Boot para a aplicação, Feign Client para comunicação com APIs externas e PostgreSQL como banco de dados.

## Tecnologias

- Java 17+
- Spring Boot
- Spring Data JPA
- Feign Client
- PostgreSQL
- Maven
- JaCoCo (para cobertura de testes)
- Mockito & JUnit 5 (para testes unitários)

## Pré-requisitos

- Java 17 ou superior instalado
- Maven 3.6 ou superior instalado
- Banco de dados PostgreSQL configurado e rodando

## Como rodar

1. Clone o repositório:

 ```bash
 git clone <URL_DO_REPOSITORIO>
 cd employee-crud
 ```

2. Configure as propriedades do banco de dados no arquivo

 ```src/main/resources/application.properties ou application.yml.   ```

3. Execute o projeto usando Maven:

  ```mvn clean install```
  ```mvn spring-boot:run```

## Como rodar

Para executar os testes unitários e gerar o relatório de cobertura:

 ```mvn clean verify```

## Configurações importantes

1) O plugin JaCoCo está configurado para excluir classes dto, model e exceptions da cobertura, pois são POJOs e não contêm lógica a ser testada.
2) Caso queira alterar exclusões ou configurações do JaCoCo, veja o arquivo pom.xml.
3) Configure as credenciais e URL do banco de dados no arquivo application.properties ou application.yml.

## Contribuições

Este é um projeto pessoal, não estão previstas contribuições externas.

## Licença

Este projeto é privado e todos os direitos são reservados ao autor.

Nenhuma parte deste código pode ser copiada, modificada, distribuída ou usada sem a permissão expressa do autor.

© 2025 [Mariana Ramacciotti]. Todos os direitos reservados.
