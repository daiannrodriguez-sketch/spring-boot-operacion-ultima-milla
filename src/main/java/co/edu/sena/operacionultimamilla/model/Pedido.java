package co.edu.sena.operacionultimamilla.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cliente;
    private Long productoId;
    private Integer cantidad;

    @Enumerated(EnumType.STRING)
    private Prioridad prioridad;

    @Enumerated(EnumType.STRING)
    private EstadoPedido estado;

    public Pedido() {
    }

    public Pedido(Long id, String cliente, Long productoId, Integer cantidad,
                   Prioridad prioridad, EstadoPedido estado) {
        this.id = id;
        this.cliente = cliente;
        this.productoId = productoId;
        this.cantidad = cantidad;
        this.prioridad = prioridad;
        this.estado = estado;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }
    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public Prioridad getPrioridad() { return prioridad; }
    public void setPrioridad(Prioridad prioridad) { this.prioridad = prioridad; }
    public EstadoPedido getEstado() { return estado; }
    public void setEstado(EstadoPedido estado) { this.estado = estado; }
}
