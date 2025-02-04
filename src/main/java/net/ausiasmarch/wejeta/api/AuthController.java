package net.ausiasmarch.wejeta.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.ausiasmarch.wejeta.bean.LogindataBean;
import net.ausiasmarch.wejeta.entity.UsuarioEntity;
import net.ausiasmarch.wejeta.repository.UsuarioRepository;
import net.ausiasmarch.wejeta.service.AuthService;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService oAuthService;

    @Autowired
    UsuarioRepository oUsuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LogindataBean oLogindataBean) {
        if (oAuthService.checkLogin(oLogindataBean)) {
            UsuarioEntity usuario = oUsuarioRepository.findById(oLogindataBean.getId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            String token = oAuthService.getToken(
                    usuario.getId(),
                    usuario.getNombre(),
                    usuario.getApellido1(),
                    usuario.getApellido2());

            return ResponseEntity.ok("\"" + token + "\"");
        } else {
            return ResponseEntity.status(401).body("\"" + "Error de autenticación" + "\"");
        }
    }

}
