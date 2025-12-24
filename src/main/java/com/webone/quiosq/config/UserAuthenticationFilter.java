package com.webone.quiosq.config;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.webone.quiosq.dto.JwtPayload;
import com.webone.quiosq.exception.UnauthorizedException;
import com.webone.quiosq.repository.UserRepository;
import com.webone.quiosq.service.impl.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@AllArgsConstructor
@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION = "Authorization";
    private final JwtTokenService jwtTokenService;

    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        final var token = recoveryToken(request);
        if (token != null) {
            try {

                JwtPayload subject = jwtTokenService.parse(
                    token);
                if (Objects.isNull(subject.getRole())) {
                    var user = userRepository.findByEmail(subject.getSubject());

                    if (user.isEmpty()) {
                        throw new UnauthorizedException("Unauthorizad");
                    }

                    UserDetailsImpl userDetails = UserDetailsImpl.builder().user(user.get())
                        .build();

                    autentication(userDetails.getUsername(), userDetails.getAuthorities());
                }

                if (Objects.nonNull(subject.getRole())) {

                    autentication(subject.getSubject(),
                        Collections.singleton(new SimpleGrantedAuthority(subject.getRole()))
                    );
                }


            } catch (TokenExpiredException ex) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(ex.getMessage());
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private static void autentication(String userName,
        Collection<? extends GrantedAuthority> authorities) {
        Authentication authentication =
            new UsernamePasswordAuthenticationToken(userName, null,
                authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String recoveryToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }

}
