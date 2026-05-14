# 🏢 Sistema de Reservas de Salas — Coworking & Eventos Empresariais

## 📋 Contexto e Objetivo

Uma empresa de coworking e eventos empresariais enfrentava dificuldades operacionais
no processo de reservas de salas e auditórios. O processo era executado manualmente
ou através de planilhas descentralizadas, gerando problemas como:

- Retrabalho operacional
- Conflitos de agenda
- Inconsistência de dados
- Dificuldade de rastreamento das informações
- Incompatibilidade de integração entre sistemas
- Baixa confiabilidade no processamento das reservas

Esta API foi desenvolvida para **centralizar e modernizar** esse fluxo, oferecendo
um sistema confiável de gerenciamento de reservas.

---

## ✅ Funcionalidades

- Cadastro de salas (reunião coletiva, reunião individual e auditório)
- Ativação e desativação de salas
- Realização de reservas com data e horário
- Validação automática de conflitos de horário
- Cancelamento de reservas
- Consulta de agenda diária (todas as salas ou por sala específica)
- Consulta de salas livres em um dado intervalo de horário
- Listagem de reservas por responsável

---

## 🏗️ Arquitetura

O projeto segue os princípios da **Arquitetura Hexagonal (Ports and Adapters)**,
separando claramente as responsabilidades em camadas:

```
src/main/java/com/desafio/reservas/
├── controllers/          # Camada de entrada (REST Controllers)
├── application/
│   ├── dtos/             # Objetos de transferência de dados (Request/Response)
│   ├── usecase/          # Interfaces dos casos de uso
│   ├── service/          # Implementações dos casos de uso
│   └── exception/        # Exceções de negócio
├── domain/
│   ├── model/            # Entidades de domínio (Sala, Reserva)
│   ├── enums/            # Enumerações (TipoSala, StatusReserva)
│   └── port/             # Interfaces de porta (SalaPort, ReservaPort)
├── infrastructure/
│   ├── adapter/          # Implementações das portas (SalaAdapter, ReservaAdapter)
│   ├── repository/       # Interfaces JPA (SalaRepository, ReservaRepository)
│   └── mappers/          # Mapeadores de entidade para DTO
└── config/               # Configurações gerais (Swagger, etc.)
```

---

## ⚙️ Como Rodar o Projeto

### 1. Clonar o repositório

```bash
git clone https://github.com/seu-usuario/reservas.git
cd reservas
```

### 2. Rodar a aplicação

```bash
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

---

## 📚 Documentação da API (Swagger)

Acesse a documentação interativa em:

```
http://localhost:8080/swagger-ui.html
```

---

## 🔗 Endpoints

### 🏠 Salas — `/api/v1/salas`

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/api/v1/salas` | Cadastrar nova sala |
| `GET` | `/api/v1/salas` | Listar todas as salas |
| `GET` | `/api/v1/salas/{id}` | Buscar sala por ID |
| `GET` | `/api/v1/salas/ativas` | Listar salas ativas |
| `GET` | `/api/v1/salas/tipo/{tipo}` | Listar salas por tipo |
| `PATCH` | `/api/v1/salas/{id}/ativar` | Ativar sala |
| `PATCH` | `/api/v1/salas/{id}/desativar` | Desativar sala |

#### Tipos de sala aceitos
- `REUNIAO_COLETIVA`
- `REUNIAO_INDIVIDUAL`
- `AUDITORIO`

#### Exemplo — Cadastrar sala
```json
POST /api/v1/salas
{
  "nome": "Sala Amazônia",
  "tipo": "REUNIAO_COLETIVA",
  "capacidade": 10
}
```

---

### 📅 Reservas — `/api/v1/reservas`

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/api/v1/reservas` | Criar nova reserva |
| `GET` | `/api/v1/reservas/{id}` | Buscar reserva por ID |
| `PATCH` | `/api/v1/reservas/{id}/cancelar` | Cancelar reserva |
| `GET` | `/api/v1/reservas/agenda` | Agenda diária (todas as salas) |
| `GET` | `/api/v1/reservas/agenda/sala/{salaId}` | Agenda por sala e dia |
| `GET` | `/api/v1/reservas/responsavel/{responsavel}` | Reservas por responsável |
| `POST` | `/api/v1/reservas/salas-livres` | Consultar salas livres |

#### Exemplo — Criar reserva
```json
POST /api/v1/reservas
{
  "salaId": 1,
  "responsavel": "João Silva",
  "inicio": "2025-06-15T09:00",
  "fim": "2025-06-15T10:00"
}
```

#### Exemplo — Consultar salas livres
```json
POST /api/v1/reservas/salas-livres
{
  "inicio": "2025-06-15T09:00",
  "fim": "2025-06-15T10:00"
}
```

#### Exemplo — Consultar agenda diária
```
GET /api/v1/reservas/agenda?data=2025-06-15
```

#### Exemplo — Agenda por sala e dia
```
GET /api/v1/reservas/agenda/sala/1?data=2025-06-15
```

---

## 🚦 Códigos de Resposta

| Código | Descrição |
|---|---|
| `200` | Sucesso |
| `201` | Criado com sucesso |
| `400` | Dados inválidos |
| `404` | Recurso não encontrado |
| `409` | Conflito de horário |
| `422` | Entidade em estado inválido (sala inativa, reserva já cancelada, etc.) |

---

## 🔒 Regras de Negócio

- Não é possível criar duas reservas ativas para a mesma sala com horários sobrepostos
- Não é possível reservar uma sala inativa
- O horário de fim deve ser posterior ao horário de início
- Não é possível criar reservas para datas/horários no passado
- Não é possível cancelar uma reserva já cancelada
- Não é possível cadastrar duas salas com o mesmo nome
- Não é possível ativar uma sala já ativa ou desativar uma sala já inativa
