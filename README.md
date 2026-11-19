# ECL Web

Aplicação web da Escola de Comércio de Lisboa com três funcionalidades:

- **Oferta formativa** – lista dos cursos da escola, lidos da base de dados.
- **Pré-inscrição** – formulário para futuros alunos, com validação no servidor.
- **Registo de avarias** – formulário para comunicar avarias nas salas.

Tem ainda uma **área de gestão** (com login) para consultar as pré-inscrições e as avarias e mudar o estado de cada avaria.

## Tecnologias

- Java 17, Jakarta Servlets 6, JSP + JSTL 3
- MySQL 8
- Apache Tomcat 10.1
- Maven

## Como pôr a funcionar

### 1. Base de dados
Com o MySQL a correr (por exemplo pelo XAMPP ou pelo MySQL Workbench), executar o script:

```
database/ecl.sql
```

O script cria a base de dados `ecl`, as tabelas, os cursos e o utilizador `ecl_app` (password `ecl2026`).
Se quiserem outros dados de acesso, alterem `src/main/resources/db.properties`.

### 2. Abrir no IntelliJ
`File > Open` e escolher a pasta do projeto. O IntelliJ reconhece o `pom.xml` e descarrega as dependências.

### 3. Configurar o Tomcat 10.1
- **IntelliJ Ultimate:** `Run > Edit Configurations > + > Tomcat Server > Local`. Escolher a pasta do Tomcat, ir ao separador *Deployment*, carregar em `+ > Artifact > ecl-web:war exploded` e pôr `/ecl` no *Application context*.
- **IntelliJ Community:** instalar o plugin **Smart Tomcat**, criar uma configuração *Smart Tomcat* com a pasta do Tomcat e o context path `/ecl`.

Depois é carregar em Run e abrir `http://localhost:8080/ecl/`.

Em alternativa: `mvn package` e copiar `target/ecl.war` para a pasta `webapps` do Tomcat.

### 4. Acesso pela rede local
O Tomcat aceita ligações de outros computadores na porta 8080. No servidor, ver o IP com `ipconfig` (Windows) ou `ip a` (Linux) e abrir noutro computador da rede:

```
http://IP_DO_SERVIDOR:8080/ecl/
```

Se não abrir, é preciso permitir a porta 8080 na firewall do servidor.

## Área de gestão
`http://localhost:8080/ecl/login`
Utilizador: `admin` · Password: `ecl2026` (definidos no `web.xml`)

## Estrutura

```
src/main/java/pt/ecl/
├── model/     Curso, PreInscricao, Avaria
├── dao/       acesso à base de dados (CursoDAO, PreInscricaoDAO, AvariaDAO)
├── servlet/   Inicio, Cursos, PreInscricao, Avaria, Login, Gestao
└── util/      Ligacao (ligação ao MySQL) e AutenticacaoFilter
src/main/webapp/
├── css/estilo.css
└── WEB-INF/views/   páginas JSP
database/ecl.sql
```
