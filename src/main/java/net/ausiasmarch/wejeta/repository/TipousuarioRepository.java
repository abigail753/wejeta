package net.ausiasmarch.wejeta.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import net.ausiasmarch.wejeta.entity.TipousuarioEntity;


public interface TipousuarioRepository extends JpaRepository<TipousuarioEntity, Long> {
 Page<TipousuarioEntity> findByDescripcionContaining(
        String filter2,Pageable oPageable);
}
