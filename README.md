# Operación Última Milla - Persistencia JPA + H2

Proyecto adaptado al Spring Boot Persistence Challenge.

## Cambios principales
- Spring Data JPA y H2.
- H2 configurada como archivo en `./data/inventario`.
- `Producto` y `Pedido` convertidos en entidades.
- `ProductoRepository` y `PedidoRepository`.
- CRUD persistente de productos.
- Consultas derivadas de productos.
- Consultas derivadas de pedidos.
- Persistencia de cambios de stock y estados de pedidos.
- Consulta propia: pedidos urgentes pendientes.

## H2 Console
Con la aplicación ejecutándose:
`http://localhost:8080/h2-console`

JDBC URL:
`jdbc:h2:file:./data/inventario`

Usuario: `sa`
Password: vacío
