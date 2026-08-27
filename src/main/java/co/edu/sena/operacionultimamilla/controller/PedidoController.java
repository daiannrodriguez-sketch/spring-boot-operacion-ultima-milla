package co.edu.sena.operacionultimamilla.controller;

import co.edu.sena.operacionultimamilla.model.Pedido;
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
    public ResponseEntity<Pedido> crearPedido(
            @RequestBody Pedido pedido) {

        Pedido nuevoPedido =
                pedidoService.crearPedido(pedido);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(nuevoPedido);
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> obtenerTodos() {

        return ResponseEntity.ok(
                pedidoService.obtenerTodos()
        );
    }

    @PutMapping("/{id}/confirmar")
    public ResponseEntity<Pedido> confirmarPedido(
            @PathVariable Long id) {

        Pedido pedido =
                pedidoService.confirmarPedido(id);

        return ResponseEntity.ok(pedido);
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Pedido> cancelarPedido(
            @PathVariable Long id) {

        Pedido pedido =
                pedidoService.cancelarPedido(id);

        return ResponseEntity.ok(pedido);
    }

    @PutMapping("/{id}/despachar")
    public ResponseEntity<Pedido> despacharPedido(
            @PathVariable Long id) {

        Pedido pedido =
                pedidoService.despacharPedido(id);

        return ResponseEntity.ok(pedido);
    }
}