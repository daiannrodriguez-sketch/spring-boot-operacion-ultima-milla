package co.edu.sena.operacionultimamilla.service;

import co.edu.sena.operacionultimamilla.model.Producto;
import co.edu.sena.operacionultimamilla.repository.ProductoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    public Producto buscarPorId(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "El producto no existe"));
    }

    public Producto crear(Producto producto) {
        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre es obligatorio");
        }
        if (producto.getStock() == null || producto.getStock() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El stock no puede ser negativo");
        }
        producto.setId(null);
        return productoRepository.save(producto);
    }

    public Producto actualizar(Long id, Producto datos) {
        Producto producto = obtenerPorId(id);
        if (datos.getNombre() != null && !datos.getNombre().trim().isEmpty()) {
            producto.setNombre(datos.getNombre());
        }
        if (datos.getCategoria() != null) {
            producto.setCategoria(datos.getCategoria());
        }
        if (datos.getStock() != null) {
            if (datos.getStock() < 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El stock no puede ser negativo");
            }
            producto.setStock(datos.getStock());
        }
        return productoRepository.save(producto);
    }

    public void eliminar(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El producto no existe");
        }
        productoRepository.deleteById(id);
    }

    public boolean existeProducto(Long id) {
        return productoRepository.existsById(id);
    }

    public boolean hayStock(Long id, Integer cantidad) {
        Producto producto = buscarPorId(id);
        return producto != null && producto.getStock() >= cantidad;
    }

    public void descontarStock(Long id, Integer cantidad) {
        Producto producto = obtenerPorId(id);
        if (producto.getStock() < cantidad) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stock insuficiente");
        }
        producto.setStock(producto.getStock() - cantidad);
        productoRepository.save(producto);
    }

    public void aumentarStock(Long id, Integer cantidad) {
        Producto producto = obtenerPorId(id);
        producto.setStock(producto.getStock() + cantidad);
        productoRepository.save(producto);
    }

    public List<Producto> buscarPorCategoria(String categoria) {
        return productoRepository.findByCategoria(categoria);
    }

    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public List<Producto> buscarStockBajo(Integer limite) {
        return productoRepository.findByStockLessThan(limite);
    }
}
