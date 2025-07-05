[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-2e0aaae1b6195c2367325f4f02e2d04e9abb55f0b24a779b69b11b9e10269abc.svg)](https://classroom.github.com/online_ide?assignment_repo_id=19896161&assignment_repo_type=AssignmentRepo)
# 🧩 ENUNCIADO COMPLETO – Sistema de Reservas para Local de Videojuegos

## 🧠 OBJETIVO

Se debe desarrollar un sistema *fullstack* con:

- 🧩 Backend en *Java + Spring Boot 3*
- 🎮 Frontend en *Angular 19* (componentes standalone, routing, template driven forms, comunicación entre componentes)

El sistema permitirá a los clientes reservar un *puesto de juego para jugar un videojuego específico en un horario determinado*. Se debe permitir la visualización de las reservas, aplicación de filtros y validaciones de negocio.

---

## 🚀 BACKEND – Requisitos

### ✅ Tecnologías y Arquitectura

- Spring Boot 3+
- Arquitectura por capas (Entity, DTO, Repository, Service, Controller)
- Spring Data JPA
- Base de datos H2 o MySQL
- Validaciones con Jakarta Bean Validation (`@NotBlank`, `@NotNull`, etc.)
- Fechas en formato `yyyy-MM-dd'T'HH:mm:ss`
- Manejo de errores con JSON + códigos HTTP
- Opcional: uso de ModelMapper o MapStruct

---

### 📄 ENTIDADES

#### Cliente
```java
Long id;
String nombreCompleto; // obligatorio
String email;
````

#### Videojuego

```java
Long id;
String titulo; // obligatorio
String genero;
```

#### PuestoJuego

```java
Long id;
String nombre; // obligatorio
String tipo;   // PC, Consola, etc.
```

#### Reserva

```java
Long id;
Cliente cliente;       // obligatorio
Videojuego videojuego; // obligatorio
PuestoJuego puesto;    // obligatorio
LocalDateTime fechaHora; // obligatorio
Integer duracionMinutos; // obligatorio
String observaciones;
```

---

### 📌 REGLAS DE NEGOCIO

* ⏰ Horario permitido: entre *10:00 y 22:00*
* ❌ Un cliente no puede tener más de una reserva en un mismo día
* ⛔ No se permite solapamiento de reservas en el mismo puesto
* ⏳ Las reservas deben tener duración exacta: 30, 60, 90 o 120 minutos

---

### 📡 ENDPOINTS API

#### `GET /api/v1/reservas`

**Query params**:

* `cliente_id`, `videojuego_id`, `puesto_id`, `fecha_hora`

#### `POST /api/v1/reservas`

```json
{
  "cliente_id": 1,
  "videojuego_id": 2,
  "puesto_id": 3,
  "fechaHora": "2025-07-01T12:00:00",
  "duracionMinutos": 60,
  "observaciones": "Con joystick"
}
```

#### Recursos auxiliares

* `GET /api/v1/clientes`
* `GET /api/v1/videojuegos`
* `GET /api/v1/puestos`

---

### ⚠ ERRORES

| Código | Descripción                    |
| ------ | ------------------------------ |
| 400    | Validación fallida             |
| 404    | Entidad no encontrada          |
| 409    | Conflicto de reglas de negocio |
| 500    | Error inesperado               |

---

## 🌐 FRONTEND – Angular 19

### ✅ Tecnologías y Requisitos

* Angular 19 (standalone)
* Routing con `@angular/router`
* Template driven forms
* Comunicación entre componentes (`@Input`, `@Output`)
* Servicios con `HttpClient` + `Observable`

---

### 🧭 Rutas

| Ruta             | Componente             | Descripción                 |
| ---------------- | ---------------------- | --------------------------- |
| `/reservas`      | `ReservaListComponent` | Lista y filtros de reservas |
| `/nueva-reserva` | `ReservaFormComponent` | Formulario de reserva nueva |

---

### 🔁 Comunicación

* `ReservaContainerComponent` es el contenedor principal
* `ReservaFormComponent` emite una reserva nueva
* Al reservar, se redirige a `/reservas` y se actualiza la lista

---

### 🎮 Formulario de Reserva

* Todos los `select` (cliente, videojuego, puesto) deben venir de la API
* Fechas con `<input type="date">` y `<input type="time">`
* Duración seleccionable (30, 60, 90, 120 min)
* Validación visual
* Botón de enviar deshabilitado si el formulario es inválido

---

### 🧪 Servicios

* `ReservaService`
* `ClienteService`
* `VideojuegoService`
* `PuestoService`

---

### 🧱 Estructura de Proyecto (Frontend)

```bash
src/
├── app/
│   ├── components/
│   │   ├── reserva-form.component.ts
│   │   ├── reserva-list.component.ts
│   │   └── reserva-container.component.ts
│   ├── services/
│   │   └── *.service.ts
│   ├── models/
│   │   └── *.model.ts
│   ├── app.routes.ts
│   ├── app.component.ts
│   └── main.ts
```
