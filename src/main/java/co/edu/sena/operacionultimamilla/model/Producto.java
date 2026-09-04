package co.edu.sena.operacionultimamilla.model;

import jakarta.persistence.*;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String categoria;
    private Integer stock;

    public Producto() {
    }

    public Producto(Long id, String nombre, Integer stock) {
        this.id = id;
        this.nombre = nombre;
        this.stock = stock;
    }

    public Producto(Long id, String nombre, String categoria, Integer stock) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.stock = stock;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
}
