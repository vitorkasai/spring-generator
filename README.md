# SPRGEN (Spring Generator)
![img.png](img.png)
## Introdução e Descrição do Projeto
Este projeto acadêmico consiste na implementação do trabalho final (T6) da disciplina compiladores ofertada em 2024/1 pelo professor Daniel Lucrédio.

Foi implementado um compilador que dada uma entrada de texto definindo entidades, seus atributos e as rotas HTTP desejadas (endpoints), é gerado o arquivo .java correspondente a uma versão portátil da implementação de uma API Rest utilizando o framework Spring Boot, e algumas bibliotecas como Lombok e dependências do Spring.

Além disso, o compilador também identifica possíveis erros de compilação seguindo as regras definidas na implementação da gramática da linguagem, e também nos  códigos de análise. 

O compilador identifica erros léxicos, sintáticos e semânticos.

## Identificação de Erros

### Erros Léxicos

### Erros sintáticos

### Erros semânticos

### Erros genéricos (do próprio gerador)

## Gerador de Código Java Spring


## Instruções de Uso

### Tecnologias Necessárias
- Java 17 (java 17.0.6 2023-01-17 LTS)
- Apache Maven 3.9.6

### Arquivo de Entrada
- No arquivo de entrada, você deve definir as entidades conforme o exemplo abaixo:

```
entidades = [
    Pessoa = {
        id: Long
        nome: String
        email: String
        idade: Long
    }
]

endpoints = [
    Pessoa = {
        GET "/pessoa"
        GET "/pessoa/{id}"
        POST "/pessoa"
        PUT "/pessoa/{id}"
        DELETE "/pessoa/{id}"
    }
]
```
### Regras
- É necessário o = para atribuição das entidades.
- É necessário o identificador primário (id) na definição da entidade.
- É necessário o uso dos caracteres [] para definir as entidades.
- É necessário especificar com {} os atributos da entidade.
  - Deve ser definido no modelo: nomeDoAtributo : Tipo

- É necessário o = para atribuição dos endpoints.
- É necessário o uso dos caracteres [] para definir os endpoints.
- É necessário especificar com {} os endpoints da entidade.
  - Deve ser definido no modelo: MétodoHTTP "/path"
  - Em casos de método http GETById, PUT ou DELETE, é necessário passar o id no path da seguinte maneira: "/path/{id}"

### Passo a Passo
1. Clone o repositório em sua máquina.
2. Entre no diretório raiz do projeto java "sprgen" e rode o comando mvn clean install para instalar as dependências do compilador e gerar o programa compilado dentro de target.
3. As entradas para o compilador devem ser inseridas dentro da pasta "casos-de-teste".
4. Para executar, vá para a raiz do projeto "sprgen", e rode o seguinte comando:
   - java -jar target/SprGen-3.0.0-jar-with-dependencies.jar casos-de-teste/analise-gerador/1.in
5. Em caso de sucesso de compilação (não houveram erros), o código gerado será salvo no mesmo local do arquivo de entrada (do exemplo 1.out), e também será gerado dentro de:
   - Bundle/spr-generated-api/src/main/java/com/example/sprGeneratedApi/SprGeneratedApi.java
6. Para iniciar a execução da API Rest em Java Spring gerada, você deve navegar até a raiz do projeto  "spr-generated-api", e rodar os seguintes comandos:
   - mvn clean install
   - mvn spring-boot:run

7. Um servidor no localhost:8080 deverá iniciar contendo os endpoints da Entidade que foram definidos no arquivo de entrada.
8. Em casos de erro de compilação, uma mensagem será devolvida no console, e o arquivo gerado no mesmo diretório do arquivo de entrada, conterá a mensagem e descrição erro, assim como sua classificação.
## Colaboradores
- Vitor Kasai Tanoue, Ciências da Computação - UFSCar, RA: 801904
- Vinícius Guimarães, Ciências da Computação - UFSCar, RA: 802431
- Karen Ketlyn Barcelos, Ciências da Computação - UFSCar, RA: 799657

