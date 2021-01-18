package hu.flowacademy.iotaspring.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.Date;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final long EXPIRATION_TIME = 1000 * 60 * 30;
    // TODO get this from yaml config
    private static final String JWT_KEY = "f6cf0b0044d6f75d024aaf55a49f206be9276b9d42b6f493c229e33c9c66fb30f8f410adcc1cad4b8ac346d6d8580c73ba0ee90003b0c24faf7d15c6f2bf76a5";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            AuthData authData = objectMapper.readValue(request.getReader(), AuthData.class);
            if (!StringUtils.hasText(authData.getUsername()) ||
                    !StringUtils.hasText(authData.getPassword())) {
                throw new UsernameNotFoundException("");
            }

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authData.getUsername(),
                            authData.getPassword()
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException("error reading request body");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("current user is: {}", authResult.getPrincipal());
        Date exp = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
        Key key = Keys.hmacShaKeyFor(JWT_KEY.getBytes());
        Claims claims = Jwts.claims().setSubject(((UserDetails) authResult.getPrincipal()).getUsername());
        String token = Jwts.builder().setClaims(claims).signWith(key, SignatureAlgorithm.HS512).setExpiration(exp).compact();

        response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().printf(objectMapper.writeValueAsString(
                Token.builder().accessToken(token).build()
        ));
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class AuthData {
    private String username;
    private String password;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Token {
    private String accessToken;
}