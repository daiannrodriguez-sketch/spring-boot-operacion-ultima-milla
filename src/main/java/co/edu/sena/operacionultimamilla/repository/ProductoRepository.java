package co.edu.sena.operacionultimamilla.repository;

import co.edu.sena.operacionultimamilla.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByCategoria(String categoria);
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    List<Producto> findByStockLessThan(Integer limite);
}
