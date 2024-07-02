# Ciclo 3: Implementação do Requisito 3  


> Realizar a venda de itens para os clientes. A venda consiste de:
>     - Data
>     - Cliente
>     - Itens vendidos
>     - Método de pagamento
>     - [Calculado] Frete
>     - [Calculado] Descontos
>     - [Calculado] ICMS
>     - [Calculado] Imposto municipal
>     - Se for um cliente prime, ele pode usar o saldo de cashback para abater na compra.

## Casos de Teste  

### Validação dos dados 

- [x] Inserir formato de data inválida
- [x] Inserir valor de data inválida
- [x] Inserir cliente que não existe
- [x] Inserir lista de itens vazia
- [x] Inserir item que não existe
- [x] Inserir método de pagamento inválido

### Cálculos 

#### Frete 

- [] Calcular frete para cliente padrão na capital
- [] Calcular frete para cliente padrão no interior
- [] Calcular frete para cliente especial na capital
  - Verificar desconto de 30% no valor do frete
- [] Calcular frete para cliente especial no interior
  - Verificar desconto de 30% no valor do frete
- [] Calcular frete para cliente prime na capital
  - Verificar isenção de frete
- [] Calcular frete para cliente prime no interior
  - Verificar isenção de frete

#### Desconto

- [] Calcular desconto de 10% para cliente especial
- [] Calcular desconto de 20% para cliente especial utilizando cartão de crédito da empresa
- [] Calcular cashback para cliente prime (R$0,03 por real gasto)
- [] Calcular cashback para cliente prime utilizando cartão de crédito da empresa (R$0,05 por real gasto)
 
#### Impostos 

- [] Calcular ICMS e imposto municipal para cliente fora do DF
  - 12% de ICMS e 4% de imposto municipal
- [] Calcular ICMS para cliente no DF
  - 18% de ICMS e 0% de imposto municipal

#### Uso de cashback

- [] Utilizar saldo de cashback para abater na compra para cliente prime
- [] Verificar que o saldo é corretamente reduzido do total
- [] Verificar que o saldo de cashback é atualizado após a compra

### Operações CRUD: Inserção

- [] Registrar venda no banco de dados
- [] Verificar que a venda de fato foi registrada
- [] Verificar que a venda contém todos os detalhes corretos (data, cliente, itens vendidos, método de pagamento, frete, descontos, impostos)

## Agrupamento dos testes em Suíte 

* Suíte de testes do Requisito 3: [Requisito3Suite](../trab1/src/test/java/br/unb/Requisito3Suite.java).
