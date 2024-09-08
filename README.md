# SPRGEN (Spring Generator)
![img.png](img.png)
## Introdução e Descrição do Projeto
Este projeto acadêmico consiste na implementação do trabalho final (T6) da disciplina compiladores ofertada em 2024/1 pelo professor Daniel Lucrédio.

Foi implementado um compilador que dada uma entrada de texto definindo entidades, seus atributos e as rotas HTTP desejadas (endpoints), é gerado o arquivo .java correspondente a uma versão portátil da implementação de uma API Rest utilizando o framework Spring Boot, e algumas bibliotecas como Lombok e dependências do Spring.

Além disso, o compilador também identifica possíveis erros de compilação seguindo as regras definidas na implementação da gramática da linguagem, e também nos  códigos de análise. 

O compilador identifica erros léxicos, sintáticos e semânticos.

## Identificação de Erros

O projeto contém exemplos de execução e compilação identificando erros em `sprgen/casos-de-teste`.
Os casos de teste que identificam erros estão separados em análise léxicas, análise sintáticas e análise semânticas em suas respectivas pastas.

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
### Regras do arquivo de entrada
- A atribuição das entidades deve ser feita utilizando o símbolo `=`.
- Cada entidade deve incluir obrigatoriamente um identificador primário (`id`).
- As entidades devem ser definidas entre colchetes `[]`.
- Os atributos de cada entidade devem ser especificados entre chaves `{}` no seguinte formato:
    - `nomeDoAtributo : Tipo`

- A atribuição dos endpoints também deve ser feita utilizando o símbolo `=`.
- Os endpoints devem ser definidos entre colchetes `[]`.
- As rotas de cada entidade devem ser especificadas entre chaves `{}` no seguinte formato:
    - `MétodoHTTP "/path"`
    - Para os métodos HTTP `GET` por id específico, `PUT` ou `DELETE`, é obrigatório passar o `id` no caminho, da seguinte forma: `"/path/{id}"`.

### Passo a Passo
1. Clone o repositório em sua máquina.
2. Entre no diretório raiz do projeto java "sprgen" e rode o comando mvn clean install para instalar as dependências do compilador e gerar o programa compilado dentro de target.
3. As entradas para o compilador devem ser inseridas dentro da pasta "casos-de-teste".
4. Para executar, vá para a raiz do projeto "sprgen", e rode o seguinte comando:
   - `java -jar target/SprGen-3.0.0-jar-with-dependencies.jar casos-de-teste/analise-gerador/1.in`
5. Em caso de sucesso de compilação (não houveram erros), o código gerado será salvo no mesmo local do arquivo de entrada (do exemplo 1.out), e também será gerado dentro de:
   - `Bundle/spr-generated-api/src/main/java/com/example/sprGeneratedApi/SprGeneratedApi.java`
6. Para iniciar a execução da API Rest em Java Spring gerada, você deve navegar até a raiz do projeto  "spr-generated-api", e rodar os seguintes comandos:
   - `mvn clean install`
   - `mvn spring-boot:run`

7. Um servidor backend no localhost:8080 deverá iniciar contendo os endpoints da Entidade que foram definidos no arquivo de entrada.
8. Em casos de erro de compilação, uma mensagem será devolvida no console, e o arquivo gerado no mesmo diretório do arquivo de entrada, conterá a mensagem e descrição erro, assim como sua classificação.

### Melhorias Sugeridas para o Projeto (To-Do)
Durante o desenvolvimento deste projeto, foram identificadas várias oportunidades de melhoria. 

De acordo com as orientações do professor, é recomendado estudar e analisar projetos de ofertas anteriores da disciplina como referência para implementar novos projetos.

Algumas sugestões que não foram desenvolvidas no projeto atual incluem:

- Uso de identificadores adicionais além do "id" primário:
  - Atualmente, o projeto considera apenas o identificador primário id como parâmetro para busca e referência das entidades.
  - Por exemplo, as operações de busca e remoção são feitas exclusivamente pelo id, o que limita a criação de endpoints mais flexíveis, como /spr-generated-api/pessoa/{nome}, que permitiriam a busca por outros atributos.

- Integração com JPA e uso de banco de dados fictício:
  - No projeto atual, as entidades são armazenadas em memória usando um Map<Long, Entidade>, simulando um banco de dados. 
  - Seria uma melhoria significativa utilizar um banco de dados em memória, como o H2 Database, que oferece suporte a JDBC e JPA.
  - Essa alteração exigiria mudanças no código gerado, como a configuração do modelo de geração de identificadores primários, além da inclusão de anotações JPA como @Column e @Table para definir as colunas e tabelas.
  - Além disso, seria interessante mapear relacionamentos entre entidades usando as anotações JPA, como @OneToOne, @OneToMany e @ManyToMany.

- Geração dinâmica de métodos no Repository:
  - Atualmente, todos os métodos básicos do Repository são gerados automaticamente, sem distinção.
  - Uma possível melhoria seria gerar apenas os métodos necessários para os endpoints definidos, evitando a criação desnecessária de métodos.

Esses pontos podem ser implementados em implementações futuras para tornar o projeto mais robusto e flexível.

## Colaboradores
- Vitor Kasai Tanoue, Ciências da Computação - UFSCar, RA: 801904
- Vinícius Guimarães, Ciências da Computação - UFSCar, RA: 802431
- Karen Ketlyn Barcelos, Ciências da Computação - UFSCar, RA: 799657

