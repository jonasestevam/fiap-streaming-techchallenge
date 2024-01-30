# Streaming Application Documentation

Projeto em WebFlux para a fase 3 da Pós-Tech FIAP

# Documentação REST

## Rotas do Controlador de Vídeo

```markdown
# VideoController

## POST /video/upload
Carrega um arquivo de vídeo.

### Request
```multipart/form-data
file: [binary]
video: {
  "title": "string",
  "description": "string",
  ...
}
```

## GET /video
Recupera uma lista de vídeos com base no filtro fornecido.

### Request
```json
{
  "filter": "string"
}
```

### Response
```json
[
  {
    "id": "string",
    "title": "string",
    "description": "string",
    ...
  },
  ...
]
```

## GET /video/{id}
Recupera um vídeo pelo seu ID.

### Response
```json
{
  "id": "string",
  "title": "string",
  "description": "string",
  ...
}
```

## DELETE /video/{id}
Deleta um vídeo pelo seu ID.

## PUT /video
Atualiza um vídeo.

### Request
```json
{
  "id": "string",
  "title": "string",
  "description": "string",
  ...
}
```

### PUT /video

Esta rota é usada para atualizar um vídeo.

**Parâmetros da solicitação:**

- `VideoDTO`: Objeto que contém as informações atualizadas do vídeo.

**Resposta:**

Retorna um `Mono` que representa o resultado da operação de atualização.

# Rotas do Controlador de Usuários

```markdown
# UserController

## POST /user
Cria um novo usuário.

### Request
```json
{
  "name": "string",
  "email": "string",
  "password": "string"
}
```

### Response
```json
{
  "id": "string",
  "name": "string",
  "email": "string"
}
```

## GET /user/{id}
Recupera um usuário pelo seu ID.

### Response
```json
{
  "id": "string",
  "name": "string",
  "email": "string"
}
```

## PUT /user/{id}
Atualiza um usuário pelo seu ID.

### Request
```json
{
  "name": "string",
  "email": "string",
  "password": "string"
}
```

### Response
```json
{
  "id": "string",
  "name": "string",
  "email": "string"
}
```

## DELETE /user/{id}
Deleta um usuário pelo seu ID.

## PUT /user/{id}/favorite/{videoId}
Adiciona um vídeo aos favoritos do usuário.

### Response
```json
{
  "id": "string",
  "name": "string",
  "email": "string",
  "favorites": ["videoId1", "videoId2", ...]
}
```

## GET /user/{id}/recommended
Recupera vídeos recomendados para um usuário.

### Response
```json
[
  {
    "id": "videoId1",
    "title": "videoTitle1",
    "description": "videoDescription1",
    ...
  },
  ...
]
```

# Rodando o projeto

```sh
docker-compose -f mongodb_composer.yml up -d
./mvnw clean install
./mvnw spring-boot:run
```

## Dependencies

- Spring Boot Starter Data MongoDB Reactive: Para suporte reativo ao MongoDB
- Spring Boot Starter WebFlux: Para aplicações reativas.
- Spring Boot Starter Test: Para testar aplicações SpringBoot.
- Project Lombok: Para redução de código boilerplate.
- MapStruct: Para mapeamentos de objetos.

## Configuração MongoDB

O MonogDB é configurado no arquivo `application.properties` do diretório "src/main/resources".
