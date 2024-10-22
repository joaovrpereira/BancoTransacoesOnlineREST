# Banco Digital REST

O **Banco Digital REST** é um projeto bancário simples onde é possível, por meio de métodos HTTP,  a Criação e Edição de Contas bancárias, Gerenciamento de Chaves Pix e a funcionalidade de se realizar transferências entre diferentes contas. Desenvolvido durante um Bootcamp da empresa Matera, o projeto utiliza a linguagem **Java**, em conjunto com o framework **SpringBoot** e **Maven**, para o gerenciamento de dependências. Para a camada de banco de dados, foi utilizado o H2Console Database, além do Hibernate para o Mapeamento Objeto-Relacional.

O projeto é composto por dois Microsserviços: **Conta** e **Bacen**, integrados utilizando o conceito de  **FeignClient**.

 - **Conta**: Responsável pela criação e edição de contas bancárias e a realização de transferências Pix.
 -  **Bacen**: Gerenciamento de Chaves Pix.

# Endpoints Principais

- **GET**: /api/contas - Consulta de Contas
- **POST**: /api/contas - Cadastro de Contas
- **GET**: /api/contas/{id} - Consulta de Conta pelo ID
- **PUT**: /api/contas/{id} - Atualização de Conta pelo ID
- **DELETE**: /api/contas/{id} - Exclusão de Conta pelo ID

- **GET**: /api/bacen/chaves/{id} - Busca de Chave PIX pelo ID
- **POST**: /api/bacen/chaves - Criação de Chave PIX

# Testes

Para verificar o funcionamento correto da aplicação, foram utilizados alguns conceitos como **Testes Unitários e de Integração**, empregando duas ferramentas para testes, em Java.

### 1. Mockito
Utilizado para simular o comportamento do  `ContaService`  e do  `PixService`  durante os testes do controlador  `ContaController`. Isso permite que os testes se concentrem na lógica do controlador sem a necessidade de implementar a lógica do serviço.

### 2. WireMock
Utilizado para simular a API do Bacen, que é chamada quando uma chave Pix é criada. Isso garante que, durante os testes de integração, possamos testar a interação entre o  `ContaController`  e o serviço Bacen sem depender de uma implementação real ou de uma rede externa.

## Conclusão

Durante o desenvolvimento desse projeto pude revisar conceitos essenciais sobre boas práticas de desenvolvimento e escolhas arquitetônicas. Além disso, a importância dos testes em uma aplicação se tornou muito clara.
