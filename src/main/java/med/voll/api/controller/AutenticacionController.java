package med.voll.api.controller;

import med.voll.api.domain.usuario.DatosAutenticarUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity AutenticarUsuario(DatosAutenticarUsuario datosAutenticarUsuario){
        Authentication token = new UsernamePasswordAuthenticationToken(datosAutenticarUsuario.login(),datosAutenticarUsuario.clave());
        authenticationManager.authenticate(token);

        return ResponseEntity.ok().build();
    }
}
