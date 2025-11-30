package com.example.backend.service;

import com.example.backend.model.Carrito;
import com.example.backend.model.ItemCarrito;
import com.example.backend.model.Producto;
import com.example.backend.model.Usuario;
import com.example.backend.repository.CarritoRepository;
import com.example.backend.repository.ItemCarritoRepository;
import com.example.backend.repository.ProductoRepository;
import com.example.backend.repository.UsuariosRepository;
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
    private final UsuariosRepository usuariosRepo;

    public CarritoService(CarritoRepository carritoRepo, ItemCarritoRepository itemRepo, 
                         ProductoRepository productoRepo, UsuariosRepository usuariosRepo) {
        this.carritoRepo = carritoRepo;
        this.itemRepo = itemRepo;
        this.productoRepo = productoRepo;
        this.usuariosRepo = usuariosRepo;
    }

    public Carrito getOrCreateByUsuarioId(Long usuarioId) {
        if (usuarioId == null) {
            throw new RuntimeException("usuarioId no puede ser null");
        }
        return carritoRepo.findByUsuarioId(usuarioId).orElseGet(() -> {
            Usuario usuario = usuariosRepo.findById(usuarioId)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            Carrito c = Carrito.builder()
                    .usuario(usuario)
                    .fechaCreacion(LocalDateTime.now())
                    .build();
            return carritoRepo.save(c);
        });
    }

    public Carrito addItem(Long usuarioId, Long productoId, int cantidad) {
        if (usuarioId == null || productoId == null || cantidad <= 0) {
            throw new RuntimeException("Datos inválidos para agregar item");
        }
        Carrito carrito = getOrCreateByUsuarioId(usuarioId);
        Producto prod = productoRepo.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        
        // Verificar si el producto ya está en el carrito
        if (carrito.getItems() != null) {
            ItemCarrito existing = carrito.getItems().stream()
                    .filter(it -> it.getProducto().getId().equals(productoId))
                    .findFirst()
                    .orElse(null);
            if (existing != null) {
                existing.setCantidad(existing.getCantidad() + cantidad);
                itemRepo.save(existing);
                return carritoRepo.save(carrito);
            }
        }
        
        ItemCarrito item = ItemCarrito.builder()
                .carrito(carrito)
                .producto(prod)
                .cantidad(cantidad)
                .precioUnitario(prod.getPrecio())
                .build();
        itemRepo.save(item);
        if (carrito.getItems() != null) {
            carrito.getItems().add(item);
        }
        return carritoRepo.save(carrito);
    }

    public Carrito removeItem(Long carritoId, Long itemId) {
        itemRepo.deleteById(itemId);
        return carritoRepo.findById(carritoId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
    }

    public void clearCart(Long carritoId) {
        Carrito c = carritoRepo.findById(carritoId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        if (c.getItems() != null) {
            itemRepo.deleteAll(c.getItems());
            c.getItems().clear();
        }
        carritoRepo.save(c);
    }

    public List<ItemCarrito> listItems(Long carritoId) {
        Carrito c = carritoRepo.findById(carritoId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        return c.getItems();
    }
}