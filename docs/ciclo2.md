# Ciclo 2: Implementação do Requisito 2  


> Cadastrar diferentes tipos de produtos, contendo:
>  - Código do item
>  - Descrição 
>  - Valor de Venda
>  - Unidade (peça, unidade, metro, cm3, etc...)

## Casos de Teste para Cadastro de Cliente 

### Validação dos dados 

- [x] Testar se descrição é diferente de "" ou de "   "
- [x] Testar se valor de venda aceita valores negativos
- [x] Testar validação de ponto/vírgula
- [x] Testar unidade inválida 
- [x] Testar código inválido

### Operações CRUD: Inserção 

- [] Testar se código é único no banco
- [] Testar inserção: verificar que o produto foi inserido no banco 
- [] Testar cadastro de produto repetido 

## Agrupamento dos testes em Suíte 

* Suíte de testes do Requisito 2: [Requisito2Suite](../trab1/src/test/java/br/unb/Requisito2Suite.java).
