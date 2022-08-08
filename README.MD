# Sistema de análise de crédito

A motivação deste repositório pode ser encontrada nesse link: 

![Tests](https://img.shields.io/github/workflow/status/allanCordeiro/loan-application/Java%20CI?label=Tests)

![Workflow](https://img.shields.io/github/workflow/status/allanCordeiro/loan-application/Java%20CI)

## Caso de uso

É importante sabermos por que você decidiu fazer a solução, portanto deixe clara a motivação das suas escolhas. 

Uma empresa de empréstimo precisa criar um sistema de análise de crédito para fornecer aos seus clientes as seguintes funcionalidades:
### i. Cadastro de clientes 

O cliente pode cadastrar: nome, e-mail, CPF, RG, endereço completo, renda e senha.
### ii. Login

A autenticação será realizada por e-mail e senha.
### iii. Solicitação de empréstimo

Para solicitar um empréstimo, precisamos do valor do empréstimo, data da primeira parcela e quantidade de parcelas. 
O máximo de parcelas será 60 e a data da primeira parcela deve ser no máximo 3 meses após o dia atual.

### iv. Acompanhamento das solicitações de empréstimo

O cliente pode visualizar a lista de empréstimos solicitados por ele mesmo e também os detalhes de um de seus empréstimos.
Na listagem, devemos retornar no mínimo o código do empréstimo, o valor e a quantidade de parcelas.
No detalhe do empréstimo, devemos retornar: código do empréstimo, valor, quantidade de parcelas, data da primeira parcela, e-mail do cliente e renda do cliente.

### Final

Quando finalizar, você deve criar uma tag no GitHub chamada tqi_evolution_avaliacao. Desta forma, nosso time já irá avaliar o material final.

### Instalação e utilização

- Clonar o repositório
- Executar o comando _docker compose up -d_

O serviço está configurado inicialmente para executar sobre a porta 8080.

Caso o container credit-analysis não execute, é possível executar os passos:
- Rebuildar o docker file através do comando _mvn clean package_
- Ou utilizar a imagem disponível no [Docker Hub](https://hub.docker.com/repository/docker/allancordeiros/credit-analysis). 

### Endpoints
#### Login
```
curl --location --request POST 'http://localhost:8080/login' \
--header 'Content-Type: application/json' \
--data-raw ' {
    "email": "user@email.com",
    "password": "1234"
 }'
```

Exemplo de resposta válida:

```
eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbGxhbkBjb3JkZWlyby5jb20iLCJleHAiOjE2NTk5NzI0OTR9...
```
Este token deve ser utilizado como bearer token nas requisições de Loan e na atualização de cadastro do cliente.

#### Customers

##### Post
```
curl --location --request POST 'http://localhost:8080/customers' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Customer Name",
    "email": "customer@email.com",
    "rg": "12345678",
    "cpf": "236.782.420-73",
    "password": "1234",
    "incomeValue": 12000,
    "address": {
        "street": "Rua 1",
        "number": "123",
        "neighborhood": "Fancy Neighborhood",
        "cep": "04255-020",
        "city": "Fancy City",
        "state": "MG"
    }
}'
```
Exemplo de resposta válida:

```
{    
    "id": "b6edf885-21a4-4aa3-8a16-5b1297b14f76",
    "name": "Customer Name",
    "email": "customer@email.com",
    "rg": "12345678",
    "cpf": "236.782.420-73",
    "password": "1234",
    "incomeValue": 12000,
    "address": {
        "street": "Rua 1",
        "number": "123",
        "neighborhood": "Fancy Neighborhood",
        "cep": "04255-020",
        "city": "Fancy City",
        "state": "MG"
    }
}
```
##### Put
```
curl --location --request PUT 'http://localhost:8080/customers/39874610-fa78-48dd-b9f4-b0e1103f86b9' \
--header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.(...)' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Customer's New Name",
    "email": "customer@email.com",
    "rg": "12345678",
    "cpf": "236.782.420-73",
    "password": "NewPassord",
    "incomeValue": 4500.0,
    "address": {
        "street": "Av 5",
        "number": "54",
        "neighborhood": "Not so Fancy Neighborhood",
        "cep": "04255-020",
        "city": "Not so Fancy City",
        "state": "ES"
    }
}'
```
Exemplo de resposta válida:

```
{
    "id": "39874610-fa78-48dd-b9f4-b0e1103f86b9",
    "name": "Customer's New Name",
    "email": "customer@email.com",
    "rg": "12345678",
    "cpf": "236.782.420-73",
    "incomeValue": 4500.0,
    "address": {
        "street": "Av 5",
        "number": "54",
        "neighborhood": "Not so Fancy Neighborhood",
        "cep": "04255-020",
        "city": "Not so Fancy City",
        "state": "ES",
        "complement": null
    }
}
```

#### Loan

##### Post

```
curl --location --request POST 'http://localhost:8080/loans' \
--header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.(...)' \
--header 'Content-Type: application/json' \
--data-raw '{
    "customerId": "ba7df91d-492e-415a-8cfc-7c3caf644614",
    "value": 2567,
    "firstInstallmentDate": "2022-09-20",
    "installmentQty": 20

}'
```
Exemplo de resposta válida:

```
{
    "id": 2,
    "customerId": "ba7df91d-492e-415a-8cfc-7c3caf644614",
    "value": 2567.0,
    "firstInstallmentDate": "2022-09-20",
    "installmentQty": 20
}
```

##### Get
```
curl --location --request GET 'http://localhost:8080/loans/39874610-fa78-48dd-b9f4-b0e1103f86b9' \
--header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.(...)'
```

Exemplo de resposta válida: 

```
[
    {
        "id": 3,
        "value": 2567.0,
        "installmentQty": 20
    },
    {
        "id": 4,
        "value": 2567.0,
        "installmentQty": 20
    }
]
```

##### Get "Loan details"

```
curl --location --request GET 'http://localhost:8080/loans/39874610-fa78-48dd-b9f4-b0e1103f86b9/4' \
--header 'Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.(...)'
```

Exemplo de resposta válida:

```
{
    "id": 4,
    "customerId": "39874610-fa78-48dd-b9f4-b0e1103f86b9",
    "value": 2567.0,
    "firstInstallmentDate": "2022-09-20",
    "installmentQty": 20,
    "customerEmail": "customer@email.com",
    "customerIncomeValue": 4500.0
}
```