package co.edu.sena.operacionultimamilla;

import co.edu.sena.operacionultimamilla.model.Producto;
import co.edu.sena.operacionultimamilla.repository.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OperacionUltimaMillaApplication {

    public static void main(String[] args) {
        SpringApplication.run(OperacionUltimaMillaApplication.class, args);
    }

    @Bean
    CommandLineRunner cargarDatosIniciales(ProductoRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Producto(null, "Teclado", "Periféricos", 20));
                repository.save(new Producto(null, "Mouse", "Periféricos", 15));
                repository.save(new Producto(null, "Monitor", "Pantallas", 10));
            }
        };
    }
}
