# Ciclo 4: Implementação do Requisito 4

> **Calcular o valor das vendas do último mês para cada cliente e verificar se ele é elegível para ser considerado um
cliente especial.**

## Casos de Teste

### Validação dos Dados

- [ ] Testar se a data de início e a data de fim para o cálculo das vendas são válidas e que a data de fim não está no
  futuro
- [ ] Testar se o cliente está cadastrado antes de verificar a elegibilidade
- [ ] Testar se o cálculo do valor total das vendas está correto para o período especificado

### Cálculo do Valor das Vendas

- [ ] Testar o cálculo do valor total das vendas do último mês para um cliente
- [ ] Testar a soma correta dos valores das vendas, incluindo o valor dos produtos e os impostos aplicados

### Verificação da Elegibilidade para Cliente Especial

- [ ] Testar a verificação de elegibilidade para cliente especial baseado no valor total das compras (acima de R$100,00)
- [ ] Testar a verificação de elegibilidade quando o valor total das compras é exatamente R$100,00
- [ ] Testar a verificação de elegibilidade para clientes que não atingem o valor necessário

## Agrupamento dos Testes em Suíte

* Suíte de testes do Requisito 4: [Requisito4Suite](../trab1/src/test/java/br/unb/Requisito4Suite.java).
