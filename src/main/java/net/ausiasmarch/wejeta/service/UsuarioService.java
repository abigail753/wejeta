package net.ausiasmarch.wejeta.service;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import net.ausiasmarch.wejeta.entity.UsuarioEntity;
import net.ausiasmarch.wejeta.repository.UsuarioRepository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@AllArgsConstructor
public class UsuarioService {

    HttpServletRequest oHttpServletRequest;

    UsuarioRepository oUsuarioRepository;

    AuthService oAuthService;

    public UsuarioEntity get(Long id) {

        Optional<UsuarioEntity> usuario = oUsuarioRepository.findById(id);
        if (usuario.isPresent()) {
            return usuario.get();
        } else {
            throw new EntityNotFoundException("Usuario no encontrado con ID: " + id);
        }
    }

    public Page<UsuarioEntity> getPage(Pageable oPageable, Optional<String> filter) {

        if (filter.isPresent()) {
            return oUsuarioRepository
                    .findByNombreContainingOrApellido1ContainingOrApellido2Containing(
                            filter.get(), filter.get(), filter.get(),
                            oPageable);
        } else {
            return oUsuarioRepository.findAll(oPageable);
        }
    }

    public Long count() {
        return oUsuarioRepository.count();
    }

    public Long delete(Long id) {
        oUsuarioRepository.deleteById(id);
        return 1L;
    }

    public UsuarioEntity create(UsuarioEntity oUsuarioEntity) {
        return oUsuarioRepository.save(oUsuarioEntity);
    }

    public UsuarioEntity update(UsuarioEntity oUsuarioEntity) {

        UsuarioEntity oUsuarioEntityFromDatabase = oUsuarioRepository.findById(oUsuarioEntity.getId()).get();
        if (oUsuarioEntity.getNombre() != null) {
            oUsuarioEntityFromDatabase.setNombre(oUsuarioEntity.getNombre());
        }
        if (oUsuarioEntity.getApellido1() != null) {
            oUsuarioEntityFromDatabase.setApellido1(oUsuarioEntity.getApellido1());
        }
        if (oUsuarioEntity.getApellido2() != null) {
            oUsuarioEntityFromDatabase.setApellido2(oUsuarioEntity.getApellido2());
        }
        return oUsuarioRepository.save(oUsuarioEntityFromDatabase);
    }

    public Long deleteAll() {
        oUsuarioRepository.deleteAll();
        return this.count();
    }
}
