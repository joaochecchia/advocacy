package com.advocacychat.backend.service;

import com.advocacychat.backend.enums.TipoUsuario;
import com.advocacychat.backend.exceptions.NotFindObjectByIdentifierException;
import com.advocacychat.backend.exceptions.NullFieldException;
import com.advocacychat.backend.exceptions.PasswordDontMatchException;
import com.advocacychat.backend.mapper.UsuarioMapper;
import com.advocacychat.backend.model.ChatModel;
import com.advocacychat.backend.model.ClienteModel;
import com.advocacychat.backend.model.UsuarioModel;
import com.advocacychat.backend.repository.UsuarioRepository;
import com.advocacychat.backend.request.AlterarSenhaRequest;
import com.advocacychat.backend.request.UsuarioRequest;
import com.advocacychat.backend.response.UsuarioResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioMapper usuarioMapper;

    private final UsuarioRepository usuarioRepository;

    private final ChatService chatService;

    private final PasswordEncoder passwordEncoder;

    public Optional<UsuarioResponse> registerUser(UsuarioRequest request) {
        UsuarioModel usuarioModel = usuarioMapper.requestToModel(request);
        usuarioModel.setSenhaHash(passwordEncoder.encode(usuarioModel.getSenhaHash()));

        if (request.tipoUsuario() == TipoUsuario.CLIENTE) {

            ClienteModel cliente = usuarioModel.getCliente();

            if (cliente == null) {
                throw new NullFieldException("Cliente não foi criado pelo mapper");
            }

            ChatModel chat = new ChatModel();
            chat.setClienteModel(cliente);

            List<ChatModel> chats = new ArrayList<>();
            chats.add(chat);
            cliente.setChats(chats);
        }

        UsuarioModel novoUsuario = usuarioRepository.save(usuarioModel);

        return Optional.of(
                UsuarioResponse.builder()
                        .id(novoUsuario.getId())
                        .email(novoUsuario.getEmail())
                        .build()
        );
    }

    public String alterarSenha(Long id, AlterarSenhaRequest request){

        UsuarioModel usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFindObjectByIdentifierException(
                        "Usuário com id " + id + " não existe."
                ));

        if (request.novaSenha() == null || request.novaSenha().isBlank()) {
            throw new NullFieldException("A nova senha não pode ser vazia.");
        }

        if (request.senhaAtual() != null && !passwordEncoder.matches(request.senhaAtual(), usuario.getSenhaHash())) {
            throw new PasswordDontMatchException("Senha atual incorreta.");
        }

        String regex = "^(?=.*[A-Z])(?=.*[0-9]).{8,}$";
        if (!request.novaSenha().matches(regex)) {
            throw new PasswordDontMatchException("Senha nao cumpre com os requisitos.");
        }

        usuario.setSenhaHash(passwordEncoder.encode(request.novaSenha()));
        usuarioRepository.save(usuario);

        return "Senha do usuário com id " + usuario.getId() + " alterada com sucesso.";
    }
}
