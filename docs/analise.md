# Relatório de Análise de Código

## Boas Práticas de Código vs Maus-Cheiros

A seguir uma relação entre as boas práticas de código, essenciais para elaboração de bons projetos, e os maus-cheiros que podem ser resolvidos e/ou evitados a partir da aplicação delas.

### 1. Simplicidade

Um projeto apresenta simplicidade quando o seu código é fácil de entender e manter, sem complexidades desnecessárias, com métodos curtos e objetivos, o que pode evitar os seguintes maus-cheiros:

- _Complexidade desnecessária_ (_Needless Complexity_)
- _Método longo_ (_Long Method_)
- _Inveja de recursos_ (_Feature Envy_)

### 2. Elegância

A elegância é visível em projetos com código claro e direto, características que ajudam a evitar redundâncias e estruturas complicadas, representadas pelos maus-cheiros:

- _Código duplicado_ (_Duplicated Code_)
- _Switches longos_ (_Long Method_ ou _Switch Statements_)

### 3. Modularidade

A modularidade consiste em dividir o código em módulos independentes com responsabilidades específicas. Essa organização do código em partes gerenciáveis ajuda a evitar os seguintes maus-cheiros:

- _Classes grandes_ (_Large Class_)
- _Cargas de dados_ (_Data Clumps_)
- _Divergência de código_ (_Divergent Change_)

### 4. Boas Interfaces

Boas interfaces ajudam a padronizar e modularizar o código. Elas reduzem o acoplamento e facilitam a manutenção, resolvendo os seguintes maus-cheiros:

- _Interface inchada_ (_Large Interface_)
- _Dependência excessiva_ (_Inappropriate Intimacy_)

### 5. Extensibilidade

Um código é extensível quando pode ser facilmente adaptado a novas funcionalidades sem grandes modificações. Essa adaptação é possível quando o código não possui os seguintes maus-cheiros:

- _Métodos gananciosos_ (_Greedy Method_)
- _Código rígido_ (_Rigid Code_ ou _Shotgun Surgery_)

### 6. Evitar Duplicação

A duplicação de código ocorre quando o mesmo trecho se repete ao longo do projeto, e isso pode ser evitado aplicando o princípio DRY (Don't Repeat Yourself)[2]. A duplicação de código geralmente está associada aos seguintes maus-cheiros:.

- _Código duplicado_ (_Duplicated Code_)
- _Métodos e classes redundantes_ (_Redundant Methods/Classes_)
- _Código rígido_ (_Rigid Code_ ou _Shotgun Surgery_)

### 7. Portabilidade

Um projeto portável pode ser executado em diferentes ambientes e plataformas sem grandes modificações. Essa prática evita a ocorrência do maus-cheiros:

- _Código dependente de plataforma_ (_Platform-Dependent Code_)

### 8. Código Idiomático e Bem Documentado

É um código legível e compreensível, que, por exemplo, possui nomes claros para as funções e classes, e bem documentado, possuindo instruções de como executar e quais padrões são utilizados na codificação. Essas características ajudam a evitar:

- _Comentários obsoletos_ (_Obsolete Comments_)
- _Código incompreensível_ (_Incomprehensible Code_)

## Análise do Trabalho Prático 2

**1. Identificação de Maus-Cheiros**

**2. Princípios Violados**

**3. Operações de Refatoração Aplicáveis**

### Referências

[1] Martin Fowler. _Refactoring: Improving the Design of Existing Code_. Addison-Wesley Professional, 1999.<br/>
[2] Robert C. Martin. _Clean Code: A Handbook of Agile Software Craftsmanship_. Prentice Hall, 2008.<br/>
[3] Andrew Hunt, David Thomas. _The Pragmatic Programmer: Your Journey to Mastery_. Addison-Wesley Professional, 1999.<br/>
[4] Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides. _Design Patterns: Elements of Reusable Object-Oriented Software_. Addison-Wesley Professional, 1994.<br/>
