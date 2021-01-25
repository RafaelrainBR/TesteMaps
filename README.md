# TesteMaps - LEVEL 2
Feito por Rafael Rain.
_____

Infelizmente não deu tempo de implementar todos os sistemas, que dentre eles estão:
- Requisição de saldo por data
- Requisição de movimento por data
- Frontend incompleto.

Creio que então eu falhei nesse nível do teste.
Se quiser ver o nível 1, por favor acesse: 
https://github.com/RafaelrainBR/TesteMaps/tree/level-1

## Dependencias: 
| Nome | Versão | Descrição |
| --- | --- | --- |
| Spring Boot Web | 2.4.2 | Framework web principal. |
| Spring Data JPA | 2.4.2 | Livraria para manipulação de entidades |
| MySQL Connector | 8 | Conector para MySQL |
| Lombok | 1.18 | Ferramenta que facilita na criação de classes |
_____


## Buildando e iniciando a aplicação:
Primeiramente para construir a aplicação, usa-se o seguinte comando:
```bash
./mvnw package
```

Logo após isso, a `jar` da aplicação estará no repositório `target/testemaps-0.0.1-SNAPSHOT.jar`
```bash
java -jar -Dfile.encoding=UTF-8 testemaps-0.0.1-SNAPSHOT.jar
```

## Configuração
Na raiz do aquivo `testemaps-0.0.1-SNAPSHOT.jar` temos um arquivo chamado `application.properties`. 
Nele estão guardados as principais configurações utilizadas.
Exemplo de arquivo:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/maps_db
spring.datasource.username=root
spring.datasource.password=password
spring.jpa.generate-ddl=true
```
_____

## Rotas
- Ações:
  - Requisitar todas as ações: `GET http://localhost:8080/assets`
  - Requisitar ação por id: `GET http://localhost:8080/assets/15`
  - Criar nova ação: `POST http://localhost:8080/assets`
    - exemplo de body:
    ```json
    {
      "name": "SP-498436",
      "price": 8.0264,
      "type": "RF",
      "emissionDate": "16-09-2021",
      "expirationDate": "21-09-2021"
    }
    ```
  - Atualizar ação: `PUT http://localhost:8080/assets`
    - formato do body é o mesmo da rota acima.
  - Deletar ação por id: `DELETE http://localhost:8080/assets/22`
- Usuários:
  - Requisitar todas os usuários: `GET http://localhost:8080/users`
  - Requisitar usuário por id: `GET http://localhost:8080/users/15`
  - Criar novo usuário: `POST http://localhost:8080/users`
    - exemplo de body:
    ```json
    {
      "name": "Dilma Bolsonaro",
      "balance": 2.02
    }
    ```
  - Atualizar usuário: `PUT http://localhost:8080/users`
    - formato do body é o mesmo da rota acima.
  - Deletar usuário por id: `DELETE http://localhost:8080/users/22`
- Movimentos:
  - Requisitar Compra de ação: `GET http://localhost:8080/movement/buy`
    - Parametros Query: `user_id`, `asset_id`, `amount`, `value`, `date`
    - user_id: Id do usuário. Exemplo: 65
    - asset_id: Id da ação. Exemplo: 26
    - amount: Quantidade de ações à comprar. Exemplo: 12
    - value: Valor de compra. Exemplo: 4681.0234
    - date: Data na qual será feita a movimentação. Exemplo: 04-03-2022
  - Requisitar Venda de ação: `GET http://localhost:8080/movement/sell`
    - Formato é o mesmo da rota acima.
- Posição:
  - Requisitar posição de uma ação de um usuário: `GET http://localhost:8080/position`
    - Parametros Query: `user_id`, `asset_id`.
    - user_id: Id do usuário. Exemplo: 65
    - asset_id: Id da ação. Exemplo: 26
