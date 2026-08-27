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

        if (pedido.getCliente() == null ||
            pedido.getCliente().trim().isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El cliente es obligatorio"
            );
        }

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

        if (pedido.getCantidad() == null ||
            pedido.getCantidad() <= 0) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La cantidad debe ser mayor que cero"
            );
        }

        if (pedido.getPrioridad() == null) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La prioridad es obligatoria"
            );
        }

        pedido.setId(siguienteId);
        pedido.setEstado(EstadoPedido.PENDIENTE);

        pedidos.add(pedido);

        siguienteId++;

        return pedido;
    }

    public List<Pedido> obtenerTodos() {

        return pedidos;
    }

    public Pedido buscarPorId(Long id) {

        for (Pedido pedido : pedidos) {

            if (pedido.getId().equals(id)) {
                return pedido;
            }
        }

        return null;
    }

    public Pedido confirmarPedido(Long id) {

        Pedido pedido = buscarPorId(id);

        if (pedido == null) {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "El pedido no existe"
            );
        }

        if (pedido.getEstado() != EstadoPedido.PENDIENTE) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Solo se pueden confirmar pedidos pendientes"
            );
        }

        if (!productoService.hayStock(
                pedido.getProductoId(),
                pedido.getCantidad())) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "No hay stock suficiente"
            );
        }

        productoService.descontarStock(
                pedido.getProductoId(),
                pedido.getCantidad()
        );

        pedido.setEstado(EstadoPedido.CONFIRMADO);

        return pedido;
    }

    public Pedido cancelarPedido(Long id) {

        Pedido pedido = buscarPorId(id);

        if (pedido == null) {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "El pedido no existe"
            );
        }

        if (pedido.getEstado() == EstadoPedido.CANCELADO) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El pedido ya está cancelado"
            );
        }

        if (pedido.getEstado() == EstadoPedido.DESPACHADO) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "No se puede cancelar un pedido despachado"
            );
        }

        if (pedido.getEstado() == EstadoPedido.CONFIRMADO) {

            productoService.aumentarStock(
                    pedido.getProductoId(),
                    pedido.getCantidad()
            );
        }

        pedido.setEstado(EstadoPedido.CANCELADO);

        return pedido;
    }

    public Pedido despacharPedido(Long id) {

        Pedido pedido = buscarPorId(id);

        if (pedido == null) {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "El pedido no existe"
            );
        }

        if (pedido.getEstado() != EstadoPedido.CONFIRMADO) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Solo se pueden despachar pedidos confirmados"
            );
        }

        pedido.setEstado(EstadoPedido.DESPACHADO);

        return pedido;
    }
}