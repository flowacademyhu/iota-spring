package hu.flowacademy.iotaspring.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
public class AuthorizationFilter extends BasicAuthenticationFilter {

    // FIXME fix code duplication
    private static final String JWT_KEY = "f6cf0b0044d6f75d024aaf55a49f206be9276b9d42b6f493c229e33c9c66fb30f8f410adcc1cad4b8ac346d6d8580c73ba0ee90003b0c24faf7d15c6f2bf76a5";

    public AuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String authorization = request.getHeader("Authorization");
        if (!StringUtils.hasText(authorization)) {
            chain.doFilter(request, response);
            return;
        }

        String jwtToken = authorization.replace("Bearer ", "");

        Object user = Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(JWT_KEY.getBytes()))
                .parse(jwtToken).getBody();

        if (user instanceof Map) {
            // the context only stores the authorized username
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(((Map) user).get("sub").toString(),
                            null,
                            List.of(new SimpleGrantedAuthority("USER")))
            );
        }

        log.info("JWT parsed data: {}", user);


        chain.doFilter(request, response);
    }
}
