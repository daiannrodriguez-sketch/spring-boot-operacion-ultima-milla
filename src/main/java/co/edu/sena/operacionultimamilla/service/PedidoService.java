package co.edu.sena.operacionultimamilla.service;

import co.edu.sena.operacionultimamilla.model.*;
import co.edu.sena.operacionultimamilla.repository.PedidoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProductoService productoService;

    public PedidoService(PedidoRepository pedidoRepository, ProductoService productoService) {
        this.pedidoRepository = pedidoRepository;
        this.productoService = productoService;
    }

    public Pedido crearPedido(Pedido pedido) {
        if (pedido.getCliente() == null || pedido.getCliente().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El cliente es obligatorio");
        }
        if (pedido.getProductoId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El productoId es obligatorio");
        }
        if (!productoService.existeProducto(pedido.getProductoId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El producto no existe");
        }
        if (pedido.getCantidad() == null || pedido.getCantidad() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La cantidad debe ser mayor que cero");
        }
        if (pedido.getPrioridad() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La prioridad es obligatoria");
        }

        pedido.setId(null);
        pedido.setEstado(EstadoPedido.PENDIENTE);
        return pedidoRepository.save(pedido);
    }

    public List<Pedido> obtenerTodos() {
        return pedidoRepository.findAll();
    }

    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    public Pedido confirmarPedido(Long id) {
        Pedido pedido = buscarPorId(id);
        if (pedido == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El pedido no existe");
        }
        if (pedido.getEstado() != EstadoPedido.PENDIENTE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Solo se pueden confirmar pedidos pendientes");
        }
        if (!productoService.hayStock(pedido.getProductoId(), pedido.getCantidad())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No hay stock suficiente");
        }

        productoService.descontarStock(pedido.getProductoId(), pedido.getCantidad());
        pedido.setEstado(EstadoPedido.CONFIRMADO);
        return pedidoRepository.save(pedido);
    }

    public Pedido cancelarPedido(Long id) {
        Pedido pedido = buscarPorId(id);
        if (pedido == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El pedido no existe");
        }
        if (pedido.getEstado() == EstadoPedido.CANCELADO) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El pedido ya está cancelado");
        }
        if (pedido.getEstado() == EstadoPedido.DESPACHADO) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se puede cancelar un pedido despachado");
        }
        if (pedido.getEstado() == EstadoPedido.CONFIRMADO) {
            productoService.aumentarStock(pedido.getProductoId(), pedido.getCantidad());
        }
        pedido.setEstado(EstadoPedido.CANCELADO);
        return pedidoRepository.save(pedido);
    }

    public Pedido despacharPedido(Long id) {
        Pedido pedido = buscarPorId(id);
        if (pedido == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El pedido no existe");
        }
        if (pedido.getEstado() != EstadoPedido.CONFIRMADO) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Solo se pueden despachar pedidos confirmados");
        }
        pedido.setEstado(EstadoPedido.DESPACHADO);
        return pedidoRepository.save(pedido);
    }

    public List<Pedido> obtenerPendientes() {
        return pedidoRepository.findByEstado(EstadoPedido.PENDIENTE);
    }

    public List<Pedido> obtenerUrgentes() {
        return pedidoRepository.findByPrioridad(Prioridad.URGENTE);
    }

    public List<Pedido> obtenerPorEstado(EstadoPedido estado) {
        return pedidoRepository.findByEstado(estado);
    }

    public ResumenPedidosDTO obtenerResumen() {
        long total = pedidoRepository.count();
        long pendientes = pedidoRepository.findByEstado(EstadoPedido.PENDIENTE).size();
        long confirmados = pedidoRepository.findByEstado(EstadoPedido.CONFIRMADO).size();
        long despachados = pedidoRepository.findByEstado(EstadoPedido.DESPACHADO).size();
        long cancelados = pedidoRepository.findByEstado(EstadoPedido.CANCELADO).size();
        long urgentes = pedidoRepository.findByPrioridad(Prioridad.URGENTE).size();
        return new ResumenPedidosDTO(total, pendientes, confirmados, despachados, cancelados, urgentes);
    }

    public Pedido obtenerSiguiente() {
        return pedidoRepository.findByEstado(EstadoPedido.PENDIENTE).stream()
                .min(Comparator.comparing(Pedido::getPrioridad, (p1, p2) ->
                        Integer.compare(p2.ordinal(), p1.ordinal()))
                        .thenComparing(Pedido::getId))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No hay pedidos pendientes por atender"));
    }

    public List<Pedido> obtenerEnRiesgo() {
        return pedidoRepository.findByEstado(EstadoPedido.PENDIENTE).stream()
                .filter(p -> !productoService.hayStock(p.getProductoId(), p.getCantidad()))
                .toList();
    }

    public List<Pedido> buscarPorPrioridad(Prioridad prioridad) {
        return pedidoRepository.findByPrioridad(prioridad);
    }

    public List<Pedido> buscarPorCliente(String cliente) {
        return pedidoRepository.findByClienteContainingIgnoreCase(cliente);
    }

    public List<Pedido> obtenerUrgentesPendientes() {
        return pedidoRepository.findByEstadoAndPrioridad(
                EstadoPedido.PENDIENTE, Prioridad.URGENTE);
    }
}
