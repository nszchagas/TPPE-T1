# Ciclo 4: Implementação do Requisito 5

> **Calcular o saldo de cashback para clientes Prime.**

## Casos de Teste

### Validação dos Dados

- [ ] Testar se o cliente é do tipo Prime antes de calcular o saldo de cashback
- [ ] Testar se o valor total das compras é calculado corretamente
- [ ] Testar se o saldo de cashback é calculado com base no valor total das compras e nas condições de pagamento

### Cálculo do Saldo de Cashback

- [ ] Testar o cálculo do cashback com base em uma compra paga com dinheiro
- [ ] Testar o cálculo do cashback com base em uma compra paga com cartão de crédito da empresa
- [ ] Testar o cálculo do saldo de cashback após múltiplas compras

### Atualização do Saldo de Cashback

- [ ] Testar a atualização do saldo de cashback após uma nova compra
- [ ] Testar se o saldo de cashback é corretamente adicionado ao saldo existente

### Resgate do Saldo de Cashback

- [ ] Testar o uso do saldo de cashback em uma nova compra
- [ ] Testar se o valor do cashback utilizado é corretamente subtraído do saldo

## Agrupamento dos Testes em Suíte

* Suíte de testes do Requisito 5: [Requisito5Suite](../trab1/src/test/java/br/unb/Requisito5Suite.java).

