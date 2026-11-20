# Portal ECL — Serviços

Projeto Final Integrador · Turma 3.º ATIS · Escola de Comércio de Lisboa

> Projeto académico demonstrativo — não constitui um canal oficial de inscrição ou de suporte técnico da Escola de Comércio de Lisboa. Todos os dados de teste são fictícios.

Aplicação Java Web com quatro áreas:

| Área | O que faz |
|---|---|
| **Oferta formativa** | Catálogo dos cursos da ECL com pesquisa, filtros por modalidade e área e página de detalhe (horas, componentes, perfil de saída, saídas profissionais, fonte e data de verificação) |
| **Pré-inscrição** | Formulário com validação no navegador e no servidor; grava com código único, data/hora e estado «Recebida» |
| **Reportar avaria** | Evolução do formulário de pedidos de apoio: perfil, sala da lista, categoria, prioridade; código para acompanhar o pedido |
| **Área reservada** | Login (Gestor/Técnico) com password em hash; resumo por estado; filtros; mudança de estado; observações técnicas; histórico |

## Tecnologias

Java 17 · Jakarta Servlet 6 / JSP / JSTL 3 · JDBC · MariaDB ou MySQL · Apache Tomcat 10.1 · Maven · Ubuntu Server 24.04 (VM)

## Estrutura

```
portal-ecl/
├── database/ecl.sql              criação das tabelas, cursos verificados e dados fictícios
├── docs/
│   ├── INSTALACAO_SERVIDOR.md    VM, IP fixo, Tomcat, MariaDB, firewall, backups
│   └── PLANO_TESTES.md           testes T01–T10 e testes de segurança
├── scripts/                      backup.sh, repor.sh (Linux) e backup.bat (Windows)
├── pom.xml
└── src/main/
    ├── java/pt/ecl/
    │   ├── model/    Curso, PreInscricao, Ocorrencia, Sala, Utilizador, Historico
    │   ├── dao/      acesso à BD com PreparedStatement
    │   ├── servlet/  Inicio, Cursos, PreInscricao, Avaria, Login, Gestao
    │   └── util/     Ligacao, Senhas (PBKDF2), Codigos, filtros de autenticação e segurança
    ├── resources/db.properties.exemplo
    └── webapp/       JSP, CSS e web.xml
```

## Modelo de dados

```
cursos (id_curso PK) ──< pre_inscricoes (id_curso FK)
salas (id PK) ──< ocorrencias (id_sala FK) >── utilizadores (id PK)
utilizadores (id PK) ──< historico_estados (id_utilizador FK)
```

## Correr no computador de desenvolvimento

1. Criar a base de dados: executar `database/ecl.sql` no MySQL/MariaDB (trocar antes `MUDAR_ESTA_PASSWORD`).
2. Copiar `src/main/resources/db.properties.exemplo` para `db.properties` na mesma pasta e pôr a password. Este ficheiro está no `.gitignore` e **nunca vai para o GitHub**.
3. Abrir a pasta `portal-ecl` no IntelliJ (`File > Open`).
4. Configurar o Tomcat 10.1 com o artefacto `ecl-web:war exploded` e o contexto `/ecl` (IntelliJ Ultimate) ou com o plugin Smart Tomcat (Community).
5. Abrir `http://localhost:8080/ecl/`.

Em alternativa, a configuração pode vir das variáveis de ambiente `ECL_DB_URL`, `ECL_DB_USER` e `ECL_DB_PASSWORD`.

## Instalar no servidor

Ver [docs/INSTALACAO_SERVIDOR.md](docs/INSTALACAO_SERVIDOR.md).

## Área reservada

`http://IP_DO_SERVIDOR:8080/ecl/gestao`

O script SQL cria os utilizadores `gestor` (perfil Gestor) e `tecnico` (perfil Técnico). As passwords de demonstração são comunicadas à equipa e devem ser trocadas no servidor (ver secção 9 do guia de instalação).

## Segurança

- Validação de todos os formulários no navegador **e** no servidor
- `PreparedStatement` em todas as consultas (contra SQL injection)
- Escape de HTML com `c:out` / `fn:escapeXml` (contra XSS)
- Passwords em hash PBKDF2-SHA256 com sal
- Sessão renovada no login, cookie HttpOnly, token CSRF nos formulários da gestão
- Credenciais da base de dados fora do repositório; utilizador da BD só com SELECT, INSERT e UPDATE
- Página de erro sem informação interna; dados pessoais nunca no URL

## Cópias de segurança

`scripts/backup.sh` cria uma cópia comprimida em `/var/backups/portal-ecl` e apaga as com mais de 14 dias. `scripts/repor.sh <ficheiro>` repõe uma cópia. Ver a secção 10 do guia de instalação.

## Fontes da oferta formativa

Dados consultados a 20/11/2026 em <https://escolacomerciolisboa.pt/escola-profissional/cursos-profissionalizantes-ecl/> e nas páginas individuais de cada curso. Cursos sem página pública ou sem carga horária publicada aparecem como «A confirmar».
