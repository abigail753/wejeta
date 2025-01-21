package net.ausiasmarch.wejeta.service;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import net.ausiasmarch.wejeta.entity.TipousuarioEntity;

import net.ausiasmarch.wejeta.repository.TipousuarioRepository;
@Service
public class TipoUsuarioService {

    @Autowired
    private TipousuarioRepository oTipousuarioRepository;

    // Método para obtener un TipoUsuario por ID
    public TipousuarioEntity get(Long id) {
        Optional<TipousuarioEntity> tipousuario = oTipousuarioRepository.findById(id);
        if (tipousuario.isPresent()) {
            return tipousuario.get();
        } else {
            throw new EntityNotFoundException("Usuario no encontrado con ID: " + id);
        }
    }

    // Método para obtener la lista paginada de Tipousuario
    public Page<TipousuarioEntity> getPage(Pageable oPageable, Optional<String> filter) {
        if (filter.isPresent()) {
            return oTipousuarioRepository.findByDescripcionContaining(filter.get(), oPageable);
        } else {
            return oTipousuarioRepository.findAll(oPageable);
        }
    }
}