

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

- [] Calcular ICMS e imposto municipal para cliente fora do DF
    - 12% de ICMS e 4% de imposto municipal
- [] Calcular ICMS para cliente no DF
    - 18% de ICMS e 0% de imposto municipal

#### Uso de cashback

- [] Utilizar ou não saldo de cashback para abater na compra para cliente prime
- [] Verificar que o saldo é corretamente reduzido do total
- [] Verificar que o saldo de cashback é atualizado após a compra

### Operações CRUD: Inserção

- [] Registrar venda no banco de dados
- [] Verificar que a venda de fato foi registrada
- [] Verificar que a venda contém todos os detalhes corretos (data, cliente, itens vendidos, método de pagamento, frete,
  descontos, impostos)

### Nota Fiscal

- [] Verificar que os valores de impostos, frete e desconto estão corretos nos cenários:

| Cliente             | Região     | Localização | ICMS | Imposto Municipal | Frete    | Desconto/Cashback     |
|---------------------|------------|-------------|------|-------------------|----------|-----------------------|
| Padrão              | Fora do DF | Capital     | 12%  | 4%                | Capital  | -                     |
| Padrão              | Fora do DF | Interior    | 12%  | 4%                | Interior | -                     |
| Padrão              | No DF      | Capital     | 18%  | 0%                | Capital  | -                     |
| Padrão              | No DF      | Interior    | 18%  | 0%                | Interior | -                     |
| Especial            | Fora do DF | Capital     | 12%  | 4%                | Capital  | 10%                   |
| Especial            | Fora do DF | Interior    | 12%  | 4%                | Interior | 10%                   |
| Especial            | No DF      | Capital     | 18%  | 0%                | Capital  | 10%                   |
| Especial            | No DF      | Interior    | 18%  | 0%                | Interior | 10%                   |
| Especial com Cartão | Fora do DF | Capital     | 12%  | 4%                | Capital  | 20%                   |
| Especial com Cartão | Fora do DF | Interior    | 12%  | 4%                | Interior | 20%                   |
| Especial com Cartão | No DF      | Capital     | 18%  | 0%                | Capital  | 20%                   |
| Especial com Cartão | No DF      | Interior    | 18%  | 0%                | Interior | 20%                   |
| Prime               | Fora do DF | Capital     | 12%  | 4%                | Capital  | R$0,03 por real gasto |
| Prime               | Fora do DF | Interior    | 12%  | 4%                | Interior | R$0,03 por real gasto |
| Prime               | No DF      | Capital     | 18%  | 0%                | Capital  | R$0,03 por real gasto |
| Prime               | No DF      | Interior    | 18%  | 0%                | Interior | R$0,03 por real gasto |
| Prime com Cartão    | Fora do DF | Capital     | 12%  | 4%                | Capital  | R$0,05 por real gasto |
| Prime com Cartão    | Fora do DF | Interior    | 12%  | 4%                | Interior | R$0,05 por real gasto |
| Prime com Cartão    | No DF      | Capital     | 18%  | 0%                | Capital  | R$0,05 por real gasto |
| Prime com Cartão    | No DF      | Interior    | 18%  | 0%                | Interior | R$0,05 por real gasto |

## Agrupamento dos testes em Suíte

* Suíte de testes do Requisito 3: [Requisito3Suite](../trab1/src/test/java/br/unb/Requisito3Suite.java).
