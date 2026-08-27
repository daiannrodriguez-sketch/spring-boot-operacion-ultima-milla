# Backend Operacion Ultima Milla - API de Gestion de Pedidos



## Mapa de Endpoints

### 1. Crear Pedido
* **HTTP:** `POST /pedidos`
* **Descripcion:** Registra un nuevo pedido en el sistema en estado `PENDIENTE`.
* **Respuesta Exitosa (201 Created):**
```json
{
  "id": 1,
  "cliente": "Jhonatan Camacho",
  "productoId": 1,
  "cantidad": 2,
  "prioridad": "ALTA",
  "estado": "PENDIENTE"
}
```
---

## Catálogo de Productos y Inventario Inicial

Para probar los endpoints del sistema (como la creación de pedidos y las validaciones de stock), utiliza los siguientes productos precargados en memoria:

| ID | Nombre del Producto | Stock Inicial |
|---|---|---|
| `1` | Teclado | 20 unidades |
| `2` | Mouse | 15 unidades |
| `3` | Monitor | 10 unidades |

---

### 2. Confirmar Pedido
* **HTTP:** `PUT /pedidos/{id}/confirmar`
* **Descripcion:** Transiciona un pedido de `PENDIENTE` a `CONFIRMADO` y descuenta las unidades del inventario.
* **Respuesta Exitosa (200 OK):** Objeto `Pedido` con el estado actualizado a `CONFIRMADO`.

---

### 3. Cancelar Pedido
* **HTTP:** `PUT /pedidos/{id}/cancelar`
* **Descripcion:** Cambia el estado del pedido a `CANCELADO`. Si el pedido ya estaba `CONFIRMADO`, restituye el stock al inventario.
* **Respuesta Exitosa (200 OK):** Objeto `Pedido` con el estado actualizado a `CANCELADO`.

---

### 4. Despachar Pedido
* **HTTP:** `PUT /pedidos/{id}/despachar`
* **Descripcion:** Transiciona un pedido de `CONFIRMADO` a `DESPACHADO`.
* **Respuesta Exitosa (200 OK):** Objeto `Pedido` con el estado actualizado a `DESPACHADO`.

---

### 5. Consultar Pendientes
* **HTTP:** `GET /pedidos/pendientes`
* **Descripcion:** Obtiene la lista de todos los pedidos cuyo estado es `PENDIENTE`.
* **Respuesta Exitosa (200 OK):** Lista JSON de objetos `Pedido`.

---

### 6. Consultar Urgentes
* **HTTP:** `GET /pedidos/urgentes`
* **Descripcion:** Obtiene la lista de todos los pedidos registrados con prioridad `URGENTE`.
* **Respuesta Exitosa (200 OK):** Lista JSON de objetos `Pedido`.

---

### 7. Consultar por Estado
* **HTTP:** `GET /pedidos/estado?estado={ESTADO}`
* **Descripcion:** Filtra pedidos segun el estado especificado (`PENDIENTE`, `CONFIRMADO`, `DESPACHADO`, `CANCELADO`).
* **Respuesta Exitosa (200 OK):** Lista JSON de objetos `Pedido`.

---

### 8. Resumen General
* **HTTP:** `GET /pedidos/resumen`
* **Descripcion:** Devuelve los contadores globales y consolidados del sistema.
* **Respuesta Exitosa (200 OK):**
```json
{
  "total": 5,
  "pendientes": 2,
  "confirmados": 1,
  "despachados": 1,
  "cancelados": 1,
  "urgentes": 1
}
```

---

### 9. Algoritmo de Prioridad (Siguiente Pedido)
* **HTTP:** `GET /pedidos/siguiente`
* **Descripcion:** Determina el proximo pedido `PENDIENTE` a atender evaluando la jerarquia de prioridad (`URGENTE` > `ALTA` > `MEDIA` > `BAJA`) y desempatando mediante criterio FIFO (`id` mas antiguo).
* **Respuesta Exitosa (200 OK):** Objeto `Pedido` prioritario.

---

### 10. Pedidos en Riesgo
* **HTTP:** `GET /pedidos/en-riesgo`
* **Descripcion:** Lista los pedidos en estado `PENDIENTE` cuya cantidad solicitada supera el stock disponible en inventario.
* **Respuesta Exitosa (200 OK):** Lista JSON de objetos `Pedido`.

---

## Manejo de Errores y Codigos HTTP

La API implementa validaciones estrictas en la capa de negocio para proteger la integridad de los datos e informa las anomalias mediante codigos de respuesta estandar:

### 400 Bad Request (Solicitud Incorrecta)
Se genera cuando la peticion contiene datos sintacticamente o logicamente invalidos que violan las reglas de negocio del sistema:
* **Datos obligatorios faltantes:** Omitir el nombre del cliente, el `productoId` o la prioridad al crear un pedido.
* **Cantidades no validas:** Intentar registrar un pedido con cantidad menor o igual a cero.
* **Violacion de transiciones de estado:** Intentar despachar un pedido no confirmado, confirmar un pedido cancelado o cancelar una orden ya despachada.
* **Stock insuficiente:** Intentar confirmar un pedido cuya cantidad excede las unidades en inventario.

### 404 Not Found (Recurso No Encontrado)
Se genera cuando la peticion hace referencia a una entidad inexistente dentro de la fuente de datos:
* **Producto no registrado:** Intentar crear un pedido asociando un `productoId` que no existe en el catalogo.
* **Pedido no registrado:** Intentar consultar, confirmar, cancelar o despachar un pedido enviando un `id` inexistente en la ruta.
* **Cola de atencion vacia:** Consultar `/pedidos/siguiente` cuando no existen pedidos en estado `PENDIENTE`.

---


