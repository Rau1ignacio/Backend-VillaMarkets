
package com.example.backend.Repository;

import com.example.backend.model.Productos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductosRepository extends JpaRepository<Productos, Long> {

    List<Productos> findByCategoriaProducto(String categoriaProducto);
    List<Productos> findByNombreProducto(String nombreProducto);
    List<Productos> findByPrecioProductoBetween(double minPrecio, double maxPrecio);


}
