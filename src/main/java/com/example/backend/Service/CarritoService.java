package com.example.backend.service;

import com.example.backend.model.Carrito;
import com.example.backend.model.ItemCarrito;
import com.example.backend.model.Producto;
import com.example.backend.model.Usuario;
import com.example.backend.repository.CarritoRepository;
import com.example.backend.repository.ItemCarritoRepository;
import com.example.backend.repository.ProductoRepository;
import com.example.backend.repository.UsuariosRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class CarritoService {

    private final CarritoRepository carritoRepo;
    private final ItemCarritoRepository itemRepo;
    private final ProductoRepository productoRepo;
    private final UsuariosRepository usuariosRepo;

    public CarritoService(CarritoRepository carritoRepo,
                          ItemCarritoRepository itemRepo,
                          ProductoRepository productoRepo,
                          UsuariosRepository usuariosRepo) {
        this.carritoRepo = carritoRepo;
        this.itemRepo = itemRepo;
        this.productoRepo = productoRepo;
        this.usuariosRepo = usuariosRepo;
    }

    public Carrito getOrCreateByUsuarioId(Long usuarioId) {
        if (usuarioId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "usuarioId no puede ser null");
        }
        Carrito carrito = carritoRepo.findByUsuarioId(usuarioId).orElseGet(() -> {
            Usuario usuario = usuariosRepo.findById(usuarioId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
            Carrito c = Carrito.builder()
                    .usuario(usuario)
                    .fechaCreacion(LocalDateTime.now())
                    .build();
            return carritoRepo.save(c);
        });
        precargarItems(carrito);
        return carrito;
    }

    public Carrito addItem(Long usuarioId, Long productoId, int cantidad) {
        if (usuarioId == null || productoId == null || cantidad <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos invalidos para agregar item");
        }
        Carrito carrito = getOrCreateByUsuarioId(usuarioId);
        Producto prod = productoRepo.findById(productoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));

        // Verificar si el producto ya esta en el carrito
        if (carrito.getItems() != null) {
            ItemCarrito existing = carrito.getItems().stream()
                    .filter(it -> it.getProducto().getId().equals(productoId))
                    .findFirst()
                    .orElse(null);
            if (existing != null) {
                existing.setCantidad(existing.getCantidad() + cantidad);
                itemRepo.save(existing);
                return reloadCarrito(carrito.getId());
            }
        } else {
            carrito.setItems(new java.util.ArrayList<>());
        }

        ItemCarrito item = ItemCarrito.builder()
                .carrito(carrito)
                .producto(prod)
                .cantidad(cantidad)
                .precioUnitario(prod.getPrecio())
                .build();
        itemRepo.save(item);
        carrito.getItems().add(item);
        return reloadCarrito(carrito.getId());
    }

    public Carrito removeItem(Long carritoId, Long itemId) {
        if (carritoId == null || itemId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "carritoId y itemId son requeridos");
        }
        itemRepo.deleteById(itemId);
        return reloadCarrito(carritoId);
    }

    public void clearCart(Long carritoId) {
        Carrito c = carritoRepo.findById(carritoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrito no encontrado"));
        if (c.getItems() != null) {
            itemRepo.deleteAll(c.getItems());
            c.getItems().clear();
        }
        carritoRepo.save(c);
    }

    public Carrito updateItemQuantity(Long carritoId, Long itemId, Integer cantidad) {
        if (carritoId == null || itemId == null || cantidad == null || cantidad <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos invalidos para actualizar item");
        }
        ItemCarrito item = itemRepo.findById(itemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item no encontrado"));
        item.setCantidad(cantidad);
        itemRepo.save(item);
        return reloadCarrito(carritoId);
    }

    public List<ItemCarrito> listItems(Long carritoId) {
        Carrito c = carritoRepo.findById(carritoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrito no encontrado"));
        return c.getItems();
    }

    private Carrito reloadCarrito(Long carritoId) {
        Carrito carrito = carritoRepo.findById(carritoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrito no encontrado"));
        precargarItems(carrito);
        return carrito;
    }

    private void precargarItems(Carrito carrito) {
        if (carrito != null && carrito.getItems() != null) {
            carrito.getItems().forEach(it -> {
                if (it.getProducto() != null) {
                    it.getProducto().getId();
                    if (it.getProducto().getTienda() != null) {
                        it.getProducto().getTienda().getId();
                    }
                }
            });
        }
    }
}
