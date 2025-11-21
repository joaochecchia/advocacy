package com.advocacychat.backend.service;

import com.advocacychat.backend.mapper.UsuarioMapper;
import com.advocacychat.backend.model.UsuarioModel;
import com.advocacychat.backend.repository.UsuarioRepository;
import com.advocacychat.backend.request.UsuarioRequest;
import com.advocacychat.backend.response.UsuarioResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioMapper usuarioMapper;

    private final UsuarioRepository usuarioRepository;

    private final PasswordEncoder passwordEncoder;

    public Optional<UsuarioResponse> registerUser(UsuarioRequest request) {
        UsuarioModel usuarioModel = usuarioMapper.requestToModel(request);
        usuarioModel.setSenhaHash(passwordEncoder.encode(usuarioModel.getSenhaHash()));

        UsuarioModel novoUsuario = usuarioRepository.save(usuarioModel);

        return Optional.of(
                UsuarioResponse.builder()
                        .id(novoUsuario.getId())
                        .email(novoUsuario.getEmail())
                        .build()
        );
    }
}
