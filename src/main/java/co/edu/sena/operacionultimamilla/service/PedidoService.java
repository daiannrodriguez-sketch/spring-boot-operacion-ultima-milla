package co.edu.sena.operacionultimamilla.service;

import co.edu.sena.operacionultimamilla.model.Pedido;
import co.edu.sena.operacionultimamilla.model.EstadoPedido;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

    private final List<Pedido> pedidos = new ArrayList<>();

    private Long siguienteId = 1L;

    private final ProductoService productoService;

    public PedidoService(ProductoService productoService) {
        this.productoService = productoService;
    }

    public Pedido crearPedido(Pedido pedido) {

        // Validar cliente
        if (pedido.getCliente() == null ||
            pedido.getCliente().trim().isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El cliente es obligatorio"
            );
        }

        // Validar producto
        if (pedido.getProductoId() == null) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El productoId es obligatorio"
            );
        }

        if (!productoService.existeProducto(pedido.getProductoId())) {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "El producto no existe"
            );
        }

        // Validar cantidad
        if (pedido.getCantidad() == null ||
            pedido.getCantidad() <= 0) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La cantidad debe ser mayor que cero"
            );
        }

        // Validar prioridad
        if (pedido.getPrioridad() == null) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La prioridad es obligatoria"
            );
        }

        // Generar ID
        pedido.setId(siguienteId);

        // Todo pedido nuevo comienza pendiente
        pedido.setEstado(EstadoPedido.PENDIENTE);

        // Guardar pedido
        pedidos.add(pedido);

        // Preparar siguiente ID
        siguienteId++;

        return pedido;
    }

    public List<Pedido> obtenerTodos() {

        return pedidos;
    }
}