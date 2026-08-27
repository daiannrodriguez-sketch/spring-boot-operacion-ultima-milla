package co.edu.sena.operacionultimamilla.service;

import co.edu.sena.operacionultimamilla.model.Producto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoService {

    private final List<Producto> productos = new ArrayList<>();

    public ProductoService() {

        productos.add(new Producto(1L, "Teclado", 20));
        productos.add(new Producto(2L, "Mouse", 15));
        productos.add(new Producto(3L, "Monitor", 10));
    }

    public Producto buscarPorId(Long id) {

        for (Producto producto : productos) {

            if (producto.getId().equals(id)) {
                return producto;
            }
        }

        return null;
    }

    public boolean existeProducto(Long id) {

        return buscarPorId(id) != null;
    }
}