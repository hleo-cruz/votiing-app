# 🗳️ Voting App - Sistema de Votação com Kafka e PostgreSQL

Este é um projeto de sistema de votação desenvolvido em **Spring Boot 3.2+**, utilizando:
- **Apache Kafka** para eventos assíncronos
- **PostgreSQL** para persistência de dados
- **Docker Compose** para infraestrutura local

---

## 🚀 Tecnologias e Ferramentas

- Java 21
- Spring Boot 3.2
- Spring Kafka
- Spring Data JDBC
- PostgreSQL
- Apache Kafka
- Flyway (migração de banco)
- Docker & Docker Compose
- Testcontainers (para testes de integração)
- H2 Database (para testes unitários)
- JUnit 5 + Mockito
- Jacoco (opcional para cobertura de testes)

---

## 📋 Funcionalidades

- Cadastro e gerenciamento de **Associados**.
- Cadastro e gerenciamento de **Pautas** (temas de votação).
- Abertura e gerenciamento de **Sessões de votação**.
- Registro de **Votos**.
- **Scheduler** para abrir sessões automaticamente no horário programado.
- **Scheduler** para finalizar sessões expiradas e publicar eventos no Kafka.
- **Consumer Kafka** que escuta eventos de sessão finalizada e atualiza o status da pauta.
- **Validação de CPF**.
- **Mensagens customizadas** em caso de erros de negócio.

---

## 📚 Estrutura do Projeto

```
src/main/java/com/br/voting/
├── controller/
├── consumer/
├── dto/
├── enums/
├── event/
├── exception/
├── mapper/
├── model/
├── producer/
├── repository/
├── scheduler/
├── service/
└── config/
```

---

## ⚙️ Como rodar o projeto

### 1. Pré-requisitos

- Docker + Docker Compose
- Java 21
- Maven

### 2. Subir infraestrutura local

```bash
docker-compose -f docker-compose.yml up --build
```

Este comando sobe:
- PostgreSQL
- Zookeeper
- Kafka

### 3. Rodar a aplicação localmente

```bash
./mvnw spring-boot:run
```

Ou rode diretamente pela sua IDE (IntelliJ, Eclipse, etc).

---

## 🧪 Executar Testes

### Testes Unitários

Os testes unitários foram criados para:

- **Services**
- **Schedulers**
- **Consumers**

Rodar:

```bash
./mvnw test
```

**Principais características:**
- Testes de Services usando `@SpringBootTest` + Mocks via `@Import(TestMockConfig.class)`
- Testes dos Schedulers validando o fluxo de fechamento e abertura de sessões
- Testes do Consumer Kafka mockando a integração com `pautaService`
- Banco H2 simula ambiente Postgres em memória para acelerar testes

---

## 📜 Testes Agendados

**Schedulers**:

| Scheduler | Função |
|:--|:--|
| `abrirSessoesAgendadas()` | Abre sessões que estavam aguardando |
| `fecharSessoesExpiradas()` | Fecha sessões vencidas e publica evento no Kafka |

**Consumers**:

| Consumer | Função |
|:--|:--|
| `SessaoFinalizadaConsumer` | Escuta eventos de sessão finalizada e atualiza status da pauta |

---

## 📦 Docker Compose

Arquivos:

- **docker-compose.yml** → Infraestrutura Kafka + Postgres
- **Dockerfile** → Empacota a aplicação para container Docker

---

## 🎯 Diferenciais do Projeto

- Código limpo e seguindo princípios SOLID
- Configurações externas centralizadas (`application.yml`, `application-test.yml`)
- Integração com Kafka desacoplada (Producer + Consumer)
- Testes unitários sem dependência real de infraestrutura
- Mock centralizado em `TestMockConfig`
- Preparado para escalar testes de integração com Testcontainers
- Preparado para CI/CD rodando testes em pipelines

---