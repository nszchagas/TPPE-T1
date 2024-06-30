# Ciclo 1: Implementação do Requisito 1 

> Cadastrar clientes dos três tipos (padrão, especial e prime):
> - Com endereço contendo o estado e se é na capital ou interior. 

## Casos de Teste para Cadastro de Cliente 

### Validação dos dados 

- Inserir tipo inválido (nem padrão, nem especial, nem prime)
    - Deve ser case insensitive na comparacao
- Inserir estado que não existe
- Inserir localização inválida (nem capital nem interior)

### Operações CRUD: inserção

- Testar inserção: verificar que o cliente de fato foi inserido no banco de dados
  - Não deve permitir a inserção de repetidos

## Agrupamento dos testes em Suíte 

* Suíte de testes do Requisito 1: [Requisito1Suite](../trab1/src/test/java/br/unb/Requisito1Suite.java).
