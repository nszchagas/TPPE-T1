

# Ciclo 3: Implementação do Requisito 3

> Realizar a venda de itens para os clientes. A venda consiste de:
> - Data
> - Cliente
> - Itens vendidos
> - Método de pagamento
> - [Calculado] Frete
> - [Calculado] Descontos
> - [Calculado] ICMS
> - [Calculado] Imposto municipal
> - Se for um cliente prime, ele pode usar o saldo de cashback para abater na compra.

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

- [x] Calcular frete para cliente padrão na capital
- [x] Calcular frete para cliente padrão no interior
- [x] Calcular frete para cliente especial na capital
    - Verificar desconto de 30% no valor do frete
- [x] Calcular frete para cliente especial no interior
    - Verificar desconto de 30% no valor do frete
- [x] Calcular frete para cliente prime na capital
    - Verificar isenção de frete
- [x] Calcular frete para cliente prime no interior
    - Verificar isenção de frete

#### Desconto

- [x] Calcular desconto de 10% para cliente especial
- [x] Calcular desconto de *10% na compra + 10% em tudo para cliente especial utilizando cartão de crédito da empresa
    - Verificar que está discriminado na nota fiscal

#### Cashback
- [x] Calcular cashback para cliente prime (R$0,03 por real gasto)
- [x] Calcular cashback para cliente prime utilizando cartão de crédito da empresa (R$0,05 por real gasto)

#### Impostos

- [x] Calcular ICMS e imposto municipal para cliente fora do DF
    - 12% de ICMS e 4% de imposto municipal
- [x] Calcular ICMS para cliente no DF
    - 18% de ICMS e 0% de imposto municipal

### Operações CRUD: Inserção

- [x] Registrar venda no banco de dados
- [x] Verificar que a venda de fato foi registrada
- [x] Verificar que a venda contém todos os detalhes corretos (data, cliente, itens vendidos, método de pagamento, frete,
  descontos, impostos)


#### Uso de cashback

- [] Utilizar ou não saldo de cashback para abater na compra para cliente prime
- [] Verificar que o saldo é corretamente reduzido do total
- [] Verificar que o saldo de cashback é atualizado após a compra


### Nota Fiscal

- [x] Verificar que os valores de impostos, frete e desconto estão corretos.
- [x] Emitir nota fiscal

## Agrupamento dos testes em Suíte

* Suíte de testes do Requisito 3: [Requisito3Suite](../src/test/java/br/unb/Requisito3Suite.java).
