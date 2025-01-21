package net.ausiasmarch.wejeta.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import net.ausiasmarch.wejeta.entity.TipousuarioEntity;
import net.ausiasmarch.wejeta.entity.UsuarioEntity;
import net.ausiasmarch.wejeta.service.TipoUsuarioService;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/tipousuario")
public class TipoUsuarioController {
    
 @Autowired
    TipoUsuarioService oTipoUsuarioService;


     @GetMapping("")
    public ResponseEntity<Page<TipousuarioEntity>> getPage(
            Pageable oPageable,
            @RequestParam  Optional<String> filter) {
        return new ResponseEntity<Page<TipousuarioEntity>>(oTipoUsuarioService.getPage(oPageable, filter), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipousuarioEntity> getTipoUsuarioById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(oTipoUsuarioService.get(id));
    }


}
