package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("El filtro está siendo llamado");
        //Obtener token de los headers
        var authHeader = request.getHeader("Authorization");
        if (authHeader !=null) {
            System.out.println("Validamos que token no es null");
            var token = authHeader.replace("Bearer ", "");
            System.out.println(token);
            System.out.println("tokenService: " + tokenService.getSubject(token)); //Este usuario tiene sesión?
            var nombreUsuario = tokenService.getSubject(token);
            if (nombreUsuario!=null){
                //token válido
                var usuario = usuarioRepository.findByLogin(nombreUsuario); //retorna usuario
                var authentication = new UsernamePasswordAuthenticationToken(usuario, null,
                        usuario.getAuthorities()); //Forzamos un inicio de sesión
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
        }
        filterChain.doFilter(request, response);
    }
}
