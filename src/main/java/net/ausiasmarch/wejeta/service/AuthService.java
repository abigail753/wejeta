package net.ausiasmarch.wejeta.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import net.ausiasmarch.wejeta.bean.LogindataBean;
import net.ausiasmarch.wejeta.entity.UsuarioEntity;
import net.ausiasmarch.wejeta.exception.ResourceNotFoundException;
import net.ausiasmarch.wejeta.exception.UnauthorizedAccessException;
import net.ausiasmarch.wejeta.repository.UsuarioRepository;

@Service
public class AuthService {

    @Autowired
    JWTService JWTHelper;

    @Autowired
    UsuarioRepository oUsuarioRepository;

    @Autowired
    HttpServletRequest oHttpServletRequest;

    public boolean checkLogin(LogindataBean oLogindataBean) {
        if (oUsuarioRepository.findByIdAndPassword(oLogindataBean.getId(), oLogindataBean.getPassword())
                .isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    private Map<String, Object> getClaims(Long id, String nombre, String apellido1, String apellido2) {
        Map<String, Object> claims = new HashMap<>();
        
        String fullName = nombre + " " + apellido1 + " " + apellido2;
        
        claims.put("id", id.toString());
        claims.put("fullName", fullName);
    
        return claims;
    }
    

    public String getToken(Long id, String nombre, String apellido1, String apellido2) {
        // Crear el nombre completo concatenando los datos
        String fullName = nombre + " " + apellido1 + " " + apellido2;
        
        // Crear un mapa con los claims que se van a incluir en el token
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);  // Añadir el ID del usuario
        claims.put("fullName", fullName);  // Añadir el nombre completo
        
        // Llamar al servicio JWT para generar el token
        return JWTHelper.generateToken(claims);
    }
    
    
    public UsuarioEntity getUsuarioFromToken() {
        Object idAttribute = oHttpServletRequest.getAttribute("id");
        if (idAttribute == null) {
            throw new UnauthorizedAccessException("No hay usuario en la sesión");
        }

        Long id = Long.valueOf(idAttribute.toString());

        return oUsuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + id));
    }

    public boolean isSessionActive() {
        return oHttpServletRequest.getAttribute("id") != null;
    }
//
//
    //public boolean isAdmin() {
    //    return this.getUsuarioFromToken().getTipousuario().getId() == 1L;
    //}
//
    //public boolean isContable() {
    //    return this.getUsuarioFromToken().getTipousuario().getId() == 2L;
    //}
//
    //public boolean isAuditor() {
    //    return this.getUsuarioFromToken().getTipousuario().getId() == 3L;
    //}
//
    //public boolean isAdminOrContable() {
    //    return this.isAdmin() || this.isContable();
    //}
//
    //public boolean isContableWithItsOwnData(Long id) {
    //    UsuarioEntity oUsuarioEntity = this.getUsuarioFromToken();
    //    return this.isContable() && oUsuarioEntity.getId() == id;
    //}
//
    //public boolean isAuditorWithItsOwnData(Long id) {
    //    UsuarioEntity oUsuarioEntity = this.getUsuarioFromToken();
    //    return this.isAuditor() && oUsuarioEntity.getId() == id;
    //}
//
}
