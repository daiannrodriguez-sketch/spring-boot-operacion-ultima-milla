package co.edu.sena.operacionultimamilla.controller;

import co.edu.sena.operacionultimamilla.model.EstadoPedido;
import co.edu.sena.operacionultimamilla.model.Pedido;
import co.edu.sena.operacionultimamilla.model.ResumenPedidosDTO;
import co.edu.sena.operacionultimamilla.service.PedidoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<Pedido> crearPedido(@RequestBody Pedido pedido) {
        Pedido nuevoPedido = pedidoService.crearPedido(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedido);
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> obtenerTodos() {
        return ResponseEntity.ok(pedidoService.obtenerTodos());
    }

    @PutMapping("/{id}/confirmar")
    public ResponseEntity<Pedido> confirmarPedido(@PathVariable Long id) {
        Pedido pedido = pedidoService.confirmarPedido(id);
        return ResponseEntity.ok(pedido);
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Pedido> cancelarPedido(@PathVariable Long id) {
        Pedido pedido = pedidoService.cancelarPedido(id);
        return ResponseEntity.ok(pedido);
    }

    @PutMapping("/{id}/despachar")
    public ResponseEntity<Pedido> despacharPedido(@PathVariable Long id) {
        Pedido pedido = pedidoService.despacharPedido(id);
        return ResponseEntity.ok(pedido);
    }

    // --- ENDPOINTS INTEGRANTE 3 ---

    @GetMapping("/pendientes")
    public ResponseEntity<List<Pedido>> obtenerPendientes() {
        return ResponseEntity.ok(pedidoService.obtenerPendientes());
    }

    @GetMapping("/urgentes")
    public ResponseEntity<List<Pedido>> obtenerUrgentes() {
        return ResponseEntity.ok(pedidoService.obtenerUrgentes());
    }

    @GetMapping("/estado")
    public ResponseEntity<List<Pedido>> obtenerPorEstado(@RequestParam EstadoPedido estado) {
        return ResponseEntity.ok(pedidoService.obtenerPorEstado(estado));
    }

    @GetMapping("/resumen")
    public ResponseEntity<ResumenPedidosDTO> obtenerResumen() {
        return ResponseEntity.ok(pedidoService.obtenerResumen());
    }

    @GetMapping("/siguiente")
    public ResponseEntity<Pedido> obtenerSiguiente() {
        return ResponseEntity.ok(pedidoService.obtenerSiguiente());
    }

    @GetMapping("/en-riesgo")
    public ResponseEntity<List<Pedido>> obtenerEnRiesgo() {
        return ResponseEntity.ok(pedidoService.obtenerEnRiesgo());
    }
}