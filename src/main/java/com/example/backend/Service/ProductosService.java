
package com.example.backend.Service;

import com.example.backend.Repository.ProductosRepository;
import com.example.backend.model.Productos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductosService {

    @Autowired
    private ProductosRepository productosRepository;

    public Productos saveProducto(Productos producto) {
        return productosRepository.save(producto);
    }

    public Productos getProductoById(long id) {
        return productosRepository.findById(id).orElse(null);
    }

    public void deleteProducto(long id) {
        productosRepository.deleteById(id);
    }

    public List<Productos> getAllProductos() {

        return productosRepository.findAll();
    }

    

}