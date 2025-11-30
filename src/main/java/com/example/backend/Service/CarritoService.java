package com.example.backend.service;

import com.example.backend.model.Carrito;
import com.example.backend.model.ItemCarrito;
import com.example.backend.model.Producto;
import com.example.backend.repository.CarritoRepository;
import com.example.backend.repository.ItemCarritoRepository;
import com.example.backend.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class CarritoService {

    private final CarritoRepository carritoRepo;
    private final ItemCarritoRepository itemRepo;
    private final ProductoRepository productoRepo;

    public CarritoService(CarritoRepository carritoRepo, ItemCarritoRepository itemRepo, ProductoRepository productoRepo) {
        this.carritoRepo = carritoRepo;
        this.itemRepo = itemRepo;
        this.productoRepo = productoRepo;
    }

    public Carrito getOrCreateByUsuarioId(Long usuarioId) {
        return carritoRepo.findByUsuarioId(usuarioId).orElseGet(() -> {
            Carrito c = Carrito.builder().fechaCreacion(LocalDateTime.now()).build();
            // usuario debe ser seteado por el controlador antes de guardar
            return carritoRepo.save(c);
        });
    }

    public Carrito addItem(Long usuarioId, Long productoId, int cantidad) {
        Carrito carrito = getOrCreateByUsuarioId(usuarioId);
        Producto prod = productoRepo.findById(productoId).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        ItemCarrito item = ItemCarrito.builder()
                .carrito(carrito)
                .producto(prod)
                .cantidad(cantidad)
                .precioUnitario(prod.getPrecio())
                .build();
        itemRepo.save(item);
        carrito.getItems().add(item);
        return carritoRepo.save(carrito);
    }

    public Carrito removeItem(Long carritoId, Long itemId) {
        itemRepo.deleteById(itemId);
        return carritoRepo.findById(carritoId).orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
    }

    public void clearCart(Long carritoId) {
        Carrito c = carritoRepo.findById(carritoId).orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        if (c.getItems() != null) c.getItems().clear();
        carritoRepo.save(c);
    }

    public List<ItemCarrito> listItems(Long carritoId) {
        Carrito c = carritoRepo.findById(carritoId).orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        return c.getItems();
    }
}