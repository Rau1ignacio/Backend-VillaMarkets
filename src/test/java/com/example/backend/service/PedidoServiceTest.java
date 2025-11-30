package com.example.backend.service;

import com.example.backend.model.ItemPedido;
import com.example.backend.model.Pedido;
import com.example.backend.model.Producto;
import com.example.backend.model.Tienda;
import com.example.backend.model.Usuario;
import com.example.backend.repository.PedidoRepository;
import com.example.backend.repository.ProductoRepository;
import com.example.backend.repository.TiendaRepository;
import com.example.backend.repository.UsuariosRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * IE2.3.2 - Prueba que Garantiza la lógica transaccional de pedidos.
 */
@SpringBootTest
@Transactional
class PedidoServiceTest {

    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private UsuariosRepository usuariosRepository;
    @Autowired
    private TiendaRepository tiendaRepository;
    @Autowired
    private PedidoRepository pedidoRepository;

    @Test
    void crearPedidoDescuentaStockDelProducto() {
        Usuario usuario = usuariosRepository.save(Usuario.builder()
                .nombres("Tester")
                .username("tester_backend")
                .password("123456")
                .correo("tester@villamarkets.cl")
                .rol("admin")
                .build());

        Tienda tienda = tiendaRepository.save(Tienda.builder()
                .nombre("Tienda Test")
                .vendedor(usuario)
                .build());

        Producto producto = productoRepository.save(Producto.builder()
                .nombre("Producto Test")
                .precio(BigDecimal.valueOf(1990))
                .stock(10)
                .tienda(tienda)
                .build());

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setTienda(tienda);
        pedido.setFechaPedido(LocalDateTime.now());
        pedido.setItems(Collections.singletonList(ItemPedido.builder()
                .producto(producto)
                .cantidad(3)
                .precioUnitario(producto.getPrecio())
                .pedido(pedido)
                .build()));

        Pedido guardado = pedidoService.create(pedido);
        productoRepository.flush();
        pedidoRepository.flush();

        Producto actualizado = productoRepository.findById(producto.getId()).orElseThrow();

        assertEquals(7, actualizado.getStock(), "El stock debe disminuir según la cantidad comprada");
        assertEquals(3, guardado.getItems().get(0).getCantidad());
    }
}
