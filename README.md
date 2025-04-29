# 🗳️ Voting App — Sistema de Votação em Java com Kafka & PostgreSQL

[![Java](https://img.shields.io/badge/Java-21-blue.svg)](https://www.oracle.com/br/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2-success)](https://spring.io/projects/spring-boot)
[![Kafka](https://img.shields.io/badge/Kafka-3.6.0-orange.svg)](https://kafka.apache.org/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue.svg)](https://www.postgresql.org/)
[![Tests](https://img.shields.io/badge/Testes-JUnit5%20%2B%20Mockito-9cf)](https://junit.org/junit5/)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](LICENSE)

---

## 📦 Sobre o Projeto

Sistema completo de votação com:

- ✅ Cadastro de pautas, sessões, votos e associados
- 🔁 Comunicação assíncrona via Kafka
- 📡 Agendamento de tarefas com `@Scheduled`
- 🔄 Fluxo de finalização e apuração com eventos
- 🧪 Testes unitários com Spring Boot + Mocks
- 🐳 Infraestrutura com Docker Compose
- 🔌 Producers e Consumers Kafka desacoplados

---

## 🚀 Tecnologias

- Java 21
- Spring Boot 3.2+
- Apache Kafka
- PostgreSQL
- Spring Kafka
- Spring Data JDBC
- Docker & Docker Compose
- Flyway
- JUnit 5 + Mockito
- H2 para testes
- Testcontainers (opcional para integração)
- Jacoco (opcional para cobertura)

---

## 📁 Estrutura

```bash
src/main/java/com/br/voting/
├── config/        # Configurações (Kafka, Logs, Mensagens)
├── controller/    # Endpoints REST
├── consumer/      # Consumers Kafka
├── dto/           # Requisições e respostas
├── enums/         # Enums do sistema
├── event/         # Eventos Kafka
├── exception/     # Tratamento de erros
├── mapper/        # Conversores DTO <-> Model
├── model/         # Entidades
├── producer/      # Producers Kafka
├── repository/    # Acesso a dados
├── scheduler/     # Agendamentos
└── service/       # Regras de negócio
```

---

## 🧪 Testes

```bash
./mvnw test
```

Testes com `@SpringBootTest`, mocks via `TestMockConfig`, sem dependência de Kafka ou banco.

Cobertura:
- Services
- Consumers
- Schedulers

---

## 🐳 Docker

Suba tudo com:

```bash
docker-compose up --build
```

Sobe:
- PostgreSQL
- Zookeeper
- Kafka
- Infra para rodar localmente

---

## 📬 Consumers Kafka

| Evento                     | Ação                                     |
|---------------------------|------------------------------------------|
| `sessao.finalizada`       | Atualiza o status da pauta               |

---

## ⏱️ Schedulers

| Método                        | Descrição                              |
|------------------------------|------------------------------------------|
| `abrirSessoesAgendadas()`    | Abre sessões com horário agendado      |
| `fecharSessoesExpiradas()`   | Fecha sessões vencidas e publica evento |

---

## 🎯 Diferenciais

- Clean Architecture 🌱
- Pronto para escalar com Testcontainers
- Estrutura de testes sólida com Mocks
- Pronto para CI/CD
- Preparado para testes de performance e integração

---

## 🙋‍♂️ Autor

Desenvolvido por **Léo** — um projeto para estudos, portfólio ou aplicação real.

---