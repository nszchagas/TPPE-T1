# TPPE-T1

Trabalho 1 da disciplina de TPPE 1º/2024.

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
