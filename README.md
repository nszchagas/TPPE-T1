# TPPE-T1

## Identificação 

- Aluno: Nicolas Chagas Souza 
- Matrícula: 200042327

Trabalho 1 da disciplina de TPPE 1º/2024.

- O enunciado do trabalho está disponível no repositório da disciplina [fga0242](https://github.com/andrelanna/fga0242), no caminho [TP/README.md](https://github.com/andrelanna/fga0242/blob/master/TP/README.md).

- Tipos de cliente: 
    - padrão
    - especial (compras mensais acima de 100 reais)
    - prime (pagam mensalidade de 20 reais)

- Benefícios:
    - especial: 
        - 10% de desconto sobre o valor total da compra
            - com +10% (total 20%) de desconto sobre o valor total do pedido, se usarem o cartão da empresa (os primeiros dígitos são 4296 13...)
        - 30% de desconto no valor do frete
    - prime:
        - frete grátis
        - cashback de 3 centavos por real gasto na loja
            - ou de 5 centavos, usando o cartão da loja

> Os valores acumulados como cashback podem ser utilizados como descontos em compras.

- Impostos:
    - Comprador fora do DF: 12% de ICMS + 4% de imposto municipal
    - Comprador do DF: 18% de ICMS 

- Fretes:

|                   | Capital  | Interior | 
|-------------------|----------|----------|
|Distrito Federal   | R$  5,00 |          |
|Regiao Centro-oeste| R$ 10,00 | R$ 13,00 |
|Regiao Nordeste    | R$ 15,00 | R$ 18,00 |
|Regiao Norte       | R$ 20,00 | R$ 25,00 |
|Regiao Sudeste     | R$  7,00 | R$ 10,00 |
|Regiao Sul         | R$ 10,00 | R$ 13,00 |

- A Nota fiscal, deve conter
    - Impostos ICMS e municipal para cada item
    - Descontos

### Requisitos 

1. Cadastrar clientes dos três tipos (padrão, especial e prime):
    - Com endereço contendo o estado e se é na capital ou interior. 
2. Cadastrar diferentes tipos de produtos, contendo:
    - Código do item
    - Descrição 
    - Valor de Venda
    - Unidade (peça, unidade, metro, cm3, etc...)
3. Realizar a venda de itens para os clientes. A venda consiste de:
    - Data
    - Cliente
    - Itens vendidos
    - Método de pagamento
    - [Calculado] Frete
    - [Calculado] Descontos
    - [Calculado] ICMS
    - [Calculado] Imposto municipal
    - Se for um cliente prime, ele pode usar o saldo de cashback para abater na compra.
4. Calcular o valor das vendas do último mês de cada cliente:
    - verificar se é elegível para ser cliente especial
5. Calcular o saldo de cashback para os clientes prime 

> Os testes devem ser triangulados utilizando a parametrização de testes do JUnit4. 



## Configuração do Ambiente

- Java: `openjdk:11` 
- Maven: `maven:3.8.5-openjdk-11`
- JUnit4: `junit 4.13.2`

### Criação do Projeto com Maven

O comando a seguir gera a pasta `trab1`.

```shell
./mvn mvn archetype:generate -DgroupId=br.unb -DartifactId=trab1 -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
```
### Configuração do Projeto

- Atualize no arquivo [pom.xml](./trab1/pom.xml) a versão do junit para 4:

```xml
 <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.13.2</version>
      <scope>test</scope>
    </dependency>
  </dependencies>
```
- Adicione a seção de properties do projeto ao [pom.xml](./trab1/pom.xml) para configurar a versão de java adequada:

```xml
<properties>
    <maven.compiler.source>11</maven.compiler.source>
    <maven.compiler.target>11</maven.compiler.target>
    <maven.compiler.release>11</maven.compiler.release>
  </properties>
```

- Adicione o plugin responsável pelo build ao [pom.xml](./trab1/pom.xml):

```xml
<build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>3.8.1</version>
        <configuration>
          <source>${maven.compiler.source}</source>
          <target>${maven.compiler.target}</target>
        </configuration>
      </plugin>
    </plugins>
  </build>
```
### Instale as dependências 

```shell
./mvn clean install # ou mvn clean install, caso tenha maven instalado localmente.
```


## Implementação 

- Ciclo 1 de TDD: [docs/ciclo1.md](./docs/ciclo1.md)
