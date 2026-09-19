# 📚 Livraria

**Sistema de Gerenciamento de Livros** — uma API REST em Java com Spring Boot, banco de dados PostgreSQL e uma interface web simples para cadastrar, listar, editar e excluir livros.

![Java](https://img.shields.io/badge/Java-17-ED8B00?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.1-6DB33F?logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-4169E1?logo=postgresql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-Wrapper-C71A36?logo=apachemaven&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?logo=docker&logoColor=white)

---

## ✨ Funcionalidades

- **Cadastrar** livros informando título, autor e ano de publicação
- **Listar** todos os livros cadastrados
- **Buscar** um livro pelo ID
- **Editar** os dados de um livro existente
- **Excluir** um livro
- **Evita títulos repetidos** no cadastro: a verificação ignora maiúsculas e minúsculas e, se o título já existir, a API responde com erro `400` e uma mensagem explicando o problema
- **Interface web pronta**, servida pela própria aplicação — não é preciso subir um servidor separado para o front-end

## 🛠️ Tecnologias

| Camada         | Tecnologia                                                     |
| -------------- | -------------------------------------------------------------- |
| Linguagem      | Java 17                                                        |
| Framework      | Spring Boot 4.1.1 (Spring Web MVC, Spring Data JPA e Actuator) |
| Persistência   | JPA / Hibernate                                                |
| Banco de dados | PostgreSQL 15 (via Docker Compose)                             |
| Build          | Maven (com Maven Wrapper)                                      |
| Front-end      | HTML, CSS e JavaScript puro (Fetch API)                        |

## 📋 Pré-requisitos

- [JDK 17](https://adoptium.net/) ou superior
- [Docker](https://www.docker.com/) e Docker Compose (para subir o PostgreSQL)
- [Git](https://git-scm.com/)

> Não é necessário instalar o Maven: o projeto já inclui o Maven Wrapper (`mvnw`).

## 🚀 Como executar

**1. Clone o repositório**

```bash
git clone https://github.com/phenrique2407/Livraria.git
cd Livraria
```

**2. Suba o banco de dados**

```bash
docker compose up -d
```

Isso inicia um contêiner com o PostgreSQL 15 na porta `5432`, já com o banco `banco_livraria` criado. As tabelas são criadas automaticamente pela aplicação na primeira execução.

**3. Execute a aplicação**

```bash
# Linux / macOS
./mvnw spring-boot:run

# Windows
.\mvnw.cmd spring-boot:run
```

**4. Acesse**

| O quê         | Endereço                     |
| ------------- | ---------------------------- |
| Interface web | http://localhost:8081        |
| API REST      | http://localhost:8081/livros |

**Para encerrar**, pare a aplicação com `Ctrl + C` e derrube o banco:

```bash
docker compose down       # para o banco e mantém os dados salvos
docker compose down -v    # para o banco e apaga os dados
```

## 🖥️ Interface web

Ao abrir `http://localhost:8081` você encontra uma página com:

- **Formulário** para cadastrar um novo livro (ao clicar em *Editar*, o mesmo formulário passa a servir para alterar o livro escolhido)
- **Lista** de livros cadastrados, cada um com os botões **Editar** e **Deletar** (com confirmação antes de apagar)
- Botão **Atualizar Lista** para recarregar os dados

Se você tentar cadastrar um título que já existe, a mensagem de erro retornada pela API aparece em um alerta na tela.

## 🔌 API REST

URL base: `http://localhost:8081/livros`

| Método   | Rota            | Descrição                            | Retorno                                                  |
| -------- | --------------- | ------------------------------------ | -------------------------------------------------------- |
| `GET`    | `/livros`       | Lista todos os livros                | `200` com a lista de livros                              |
| `GET`    | `/livros/{id}`  | Busca um livro pelo ID               | `200` com o livro                                        |
| `POST`   | `/livros`       | Cadastra um novo livro               | `201` com o livro criado, ou `400` se o título já existir |
| `PUT`    | `/livros/{id}`  | Atualiza título, autor e ano do livro | `200` com o livro atualizado                             |
| `DELETE` | `/livros/{id}`  | Exclui um livro                      | `200` sem conteúdo                                       |

O Actuator também está habilitado; o endpoint de saúde da aplicação fica em `http://localhost:8081/actuator/health`.

### Modelo `Livro`

| Campo           | Tipo      | Observação                                                                    |
| --------------- | --------- | ----------------------------------------------------------------------------- |
| `id`            | `Long`    | Gerado automaticamente pelo banco                                             |
| `titulo`        | `String`  | Não pode se repetir no cadastro (a verificação ignora maiúsculas/minúsculas)  |
| `autor`         | `String`  |                                                                               |
| `anoPublicacao` | `Integer` |                                                                               |

### Exemplos de uso

Os exemplos abaixo usam `curl` em um terminal Bash (Linux, macOS ou Git Bash no Windows). Você também pode testar com ferramentas como Postman ou Insomnia.

**Cadastrar um livro**

```bash
curl -X POST http://localhost:8081/livros \
  -H "Content-Type: application/json" \
  -d '{"titulo": "Dom Casmurro", "autor": "Machado de Assis", "anoPublicacao": 1899}'
```

Resposta (`201 Created`):

```json
{
  "id": 1,
  "titulo": "Dom Casmurro",
  "autor": "Machado de Assis",
  "anoPublicacao": 1899
}
```

Se o título já estiver cadastrado, a resposta é `400 Bad Request` com a mensagem:

```
Erro: Já existe um livro cadastrado com o título 'Dom Casmurro'!
```

**Listar todos os livros**

```bash
curl http://localhost:8081/livros
```

**Buscar um livro pelo ID**

```bash
curl http://localhost:8081/livros/1
```

**Atualizar um livro**

```bash
curl -X PUT http://localhost:8081/livros/1 \
  -H "Content-Type: application/json" \
  -d '{"titulo": "Dom Casmurro", "autor": "Joaquim Maria Machado de Assis", "anoPublicacao": 1899}'
```

**Excluir um livro**

```bash
curl -X DELETE http://localhost:8081/livros/1
```

## ⚙️ Configuração

As configurações ficam em `src/main/resources/application.properties` e já estão alinhadas com o `docker-compose.yml`:

| Propriedade                     | Valor padrão                                      | Descrição                                |
| ------------------------------- | ------------------------------------------------- | ---------------------------------------- |
| `server.port`                   | `8081`                                            | Porta da aplicação                       |
| `spring.datasource.url`         | `jdbc:postgresql://localhost:5432/banco_livraria` | Conexão com o PostgreSQL                 |
| `spring.datasource.username`    | `usuario_livraria`                                | Usuário do banco                         |
| `spring.datasource.password`    | `senha_livraria`                                  | Senha do banco                           |
| `spring.jpa.hibernate.ddl-auto` | `update`                                          | Cria/atualiza as tabelas automaticamente |
| `spring.jpa.show-sql`           | `true`                                            | Exibe as consultas SQL no console        |

> ⚠️ Essas credenciais servem apenas para desenvolvimento local. Em produção, use variáveis de ambiente ou um gerenciador de segredos e não deixe senhas no repositório.

> 💡 Se a porta `5432` ou a `8081` já estiver em uso na sua máquina, altere a porta correspondente no `docker-compose.yml` (por exemplo, `"5433:5432"`) e no `application.properties`.

## 📁 Estrutura do projeto

```
Livraria/
├── docker-compose.yml                   # PostgreSQL 15 para desenvolvimento
├── pom.xml                              # Dependências e build (Maven)
├── mvnw / mvnw.cmd                      # Maven Wrapper (Linux/macOS e Windows)
└── src/main/
    ├── java/br/com/livraria/
    │   ├── SistemaApplication.java      # Classe principal (Spring Boot)
    │   ├── controle/
    │   │   └── LivroController.java     # Endpoints REST (/livros)
    │   ├── modelo/
    │   │   └── Livro.java               # Entidade JPA
    │   └── repositorio/
    │       └── LivroRepository.java     # Acesso ao banco (Spring Data JPA)
    └── resources/
        ├── application.properties       # Configurações da aplicação
        └── static/
            └── index.html               # Interface web
```

## 🔮 Melhorias futuras

- [ ] Retornar `404 Not Found` quando o livro não for encontrado (busca e atualização por ID)
- [ ] Validar os dados no back-end (campos obrigatórios e ano válido) com Bean Validation
- [ ] Impedir título duplicado também na atualização de um livro
- [ ] Criar testes automatizados
- [ ] Adicionar busca por título/autor e paginação da lista

## 👤 Autor

Desenvolvido por [phenrique2407](https://github.com/phenrique2407).
