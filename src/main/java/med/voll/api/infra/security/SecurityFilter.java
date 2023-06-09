package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("El filtro está siendo llamado");
        //Obtener token de los headers
        var token = request.getHeader("Authorization");//.replace("Bearer ","");
        if (token == "" || token == null){
            throw new RuntimeException("El token enviado no es válido");
        }
        token = token.replace("Bearer ", "");
        System.out.println(token);
        System.out.println("tokenService: " + tokenService.getSubject(token)); //Este usuario tiene sesión?

        filterChain.doFilter(request, response);
    }
}
