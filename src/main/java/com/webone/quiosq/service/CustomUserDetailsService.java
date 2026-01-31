package com.webone.quiosq.service;

import com.webone.quiosq.repository.ClienteRepository;
import com.webone.quiosq.repository.UserRepository;
import com.webone.quiosq.service.impl.ClienteDetailsImpl;
import com.webone.quiosq.service.impl.UserDetailsImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final ClienteRepository clienteRepository;
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var cliente = clienteRepository.findByTelefone(username);
        if (cliente.isPresent()) {
            return new ClienteDetailsImpl(cliente.get());
        }

        var user = userRepository.findByEmail(username);
        if (user.isPresent()) {
            return new UserDetailsImpl(user.get());
        }

        var func = userRepository.findByCpf(username);
        if (func.isPresent()) {
            return new UserDetailsImpl(func.get());
        }

        throw new UsernameNotFoundException("Usuário/Cliente não encontrado");
    }

}
