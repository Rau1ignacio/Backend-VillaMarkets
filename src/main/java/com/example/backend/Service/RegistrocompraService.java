package com.example.backend.Service;

import com.example.backend.model.Registrocompra;
import com.example.backend.Repository.RegistroCompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegistrocompraService {

    @Autowired
    private RegistroCompraRepository registroCompraRepository;

    // Obtener todos los registros de compra
    public List<Registrocompra> getAllRegistros() {
        return registroCompraRepository.findAll();
    }

    // Obtener registro por ID
    public Optional<Registrocompra> getRegistroById(Long id) {
        return registroCompraRepository.findById(id);
    }

    // Crear nuevo registro de compra
    public Registrocompra createRegistro(Registrocompra registrocompra) {
        return registroCompraRepository.save(registrocompra);
    }

    // Actualizar registro de compra
    public Registrocompra updateRegistro(Long id, Registrocompra registroDetails) {
        return registroCompraRepository.findById(id)
            .map(registro -> {
                registro.setIdUsuario(registroDetails.getIdUsuario());
                registro.setNombresUsuario(registroDetails.getNombresUsuario());
                registro.setApellidosUsuario(registroDetails.getApellidosUsuario());
                registro.setRutUsuario(registroDetails.getRutUsuario());
                registro.setDvRut(registroDetails.getDvRut());
                registro.setFechaCompra(registroDetails.getFechaCompra());
                registro.setMontoTotal(registroDetails.getMontoTotal());
                return registroCompraRepository.save(registro);
            })
            .orElseThrow(() -> new RuntimeException("Registro no encontrado con id: " + id));
    }

    // Eliminar registro de compra
    public void deleteRegistro(Long id) {
        registroCompraRepository.deleteById(id);
    }

    // Buscar registros por ID de usuario
    public List<Registrocompra> getRegistrosByUsuarioId(Long idUsuario) {
        return registroCompraRepository.findByIdUsuario(idUsuario);
    }
}
