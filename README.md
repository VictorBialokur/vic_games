# Vic Games

Bem-vindo ao Vic Games, um projeto de API Rest simples para praticar o que aprendi,
assim como um programa console para utilizar destas funções.

Essa API permite o cadastro de clientes e jogos, onde os clientes compram os jogos
de forma a simular um sistema de registro de vendas de lojas físicas.

Utiliza de Java 17 com Spring Boot e Maven, se conectando a um banco MySQL usando de JPA e JPQL com Hibernate. 

Possui também testes unitários com JUnit e Mockito.
 
OBS: Para utilizar o programa console primeiro execute a API e depois o console.

OBS 2: Caso não execute, altere as informações de usuário e senha do MySQL para as suas
em vic_games-api dentro de src/main/resources/application.properties.

# Rotas da API
## Clientes
 "http://localhost:8080/clientes": GET  -> Permite a busca de todos os clientes cadastrados.
                                   POST -> Cadastra um cliente. 

 "http://localhost:8080/clientes/{id}": GET -> Permite a busca de um cliente em específico passando o id.
                                        PUT -> Altera as informações de um cliente.    

 "http://localhost:8080/clientes/{id}/comprados": GET -> Retorna a lista de jogos comprados por um cliente.

 "http://localhost:8080/clientes/{id}/comprar": PUT -> Permite comprar um jogo ao passar o id do cliente na uri
e o id do jogo a ser comprado como corpo.

 "http://localhost:8080/clientes/{id}/delete": DELETE -> Deleta o cliente com o id passado.

## Jogos
 "http://localhost:8080/jogos": GET  -> Retorna os jogos cadastrados.
                                POST -> Cadastra um jogo.

 "http://localhost:8080/jogos/{id}": GET -> Retorna um jogo com o id passado.
                                     PUT -> Altera as informações de um jogo.
