package co.edu.sena.operacionultimamilla.repository;

import co.edu.sena.operacionultimamilla.model.EstadoPedido;
import co.edu.sena.operacionultimamilla.model.Pedido;
import co.edu.sena.operacionultimamilla.model.Prioridad;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByEstado(EstadoPedido estado);
    List<Pedido> findByPrioridad(Prioridad prioridad);
    List<Pedido> findByClienteContainingIgnoreCase(String cliente);
    List<Pedido> findByEstadoAndPrioridad(EstadoPedido estado, Prioridad prioridad);
}
