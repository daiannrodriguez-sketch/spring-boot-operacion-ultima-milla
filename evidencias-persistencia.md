1. ¿Qué información desapareció? 
Todos los productos y pedidos creados durante la ejecución.

2. ¿Dónde estaba almacenada?
En la memoria RAM del sistema dentro de las listas (List / ArrayList).  

3. ¿Por qué reiniciar la aplicación afecta a una lista en memoria?
La memoria RAM es volátil; al finalizar el proceso de Java, la memoria asignada se libera y el estado se destruye.

4. ¿Qué debería cambiar para conservarla?
Mover el almacenamiento desde colecciones en memoria hacia una base de datos persistente mediante un repositorio.  

5. ¿ProductoRepository es una clase o una interfaz?
Es una interfaz. No es una clase porque no contiene la implementación de los métodos; Spring Data JPA genera automáticamente la clase con la implementación en tiempo de ejecución.

6. ¿Qué representa Producto en JpaRepository<Producto, Long>?
Representa la entidad del modelo (mapeada a la tabla de la base de datos) sobre la cual se realizarán las operaciones CRUD.

7. ¿Qué representa Long?
Representa el tipo de dato de la clave primaria (ID) de la entidad Producto

8. ¿Por qué ya no es necesario recorrer una lista para buscar por id?
Porque JpaRepository delega la consulta directamente al motor de la base de datos mediante SQL (SELECT * FROM producto WHERE id = ?). El motor accede al registro de forma optimizada a través de índices en memoria B-Tree, eliminando la necesidad de iterar manualmente elemento por elemento en una lista Java en memoria RAM.

