package com.advocacychat.backend.service;

import com.advocacychat.backend.exceptions.*;
import com.advocacychat.backend.mapper.*;
import com.advocacychat.backend.model.*;
import com.advocacychat.backend.enums.Role;
import com.advocacychat.backend.repository.*;
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

    private final EscritorioRepository escritorioRepository;

    private final AdminRepository adminRepository;
    private final DonoRepository donoRepository;
    private final ClienteRepository clienteRepository;
    private final AdvogadoRepository advogadoRepository;

    private final AdminMapper adminMapper;
    private final DonoMapper donoMapper;
    private final ClienteMapper clienteMapper;
    private final AdvogadoMapper advogadoMapper;

    private final PasswordEncoder passwordEncoder;

    public Optional<UsuarioResponse> registerUser(UsuarioRequest request) {

        if (request.role() == Role.CLIENTE && request.clienteDTO() == null) {
            throw new NullFieldException("ClienteDTO é obrigatório para clientes.");
        }

        if (request.role() == Role.ADVOGADO && request.advogadoDTO() == null) {
            throw new BusinessRuleException("AdvogadoDTO é obrigatório para advogados.");
        }

        if (request.role() == Role.CLIENTE && request.advogadoDTO() != null) {
            throw new BusinessRuleException("Cliente não pode possuir AdvogadoDTO.");
        }

        if(usuarioRepository.existsByEmail(request.email())){
            throw new UniqueFieldException("Usuario com email: " + request.email() + " ja está cadastrado.");
        }

        UsuarioModel usuarioModel = usuarioMapper.requestToModel(request);
        usuarioModel.setSenhaHash(passwordEncoder.encode(usuarioModel.getSenhaHash()));

        UsuarioModel novoUsuario = usuarioRepository.save(usuarioModel);

        if(request.role() == Role.ADMIN){
            AdminModel admin = adminMapper.dtoToModel(request.adminDTO());
            admin.setUsuario(novoUsuario);

            adminRepository.save(admin);
        }

        if(request.role() == Role.DONO){
            DonoModel dono = donoMapper.dtoToModel(request.donoDTO());
            dono.setUsuario(novoUsuario);

            if (request.donoDTO().getEscritorioId() != null) {
                EscritorioModel escritorio = escritorioRepository.findById(request.donoDTO().getEscritorioId())
                        .orElseThrow(() -> new NotFindObjectByIdentifierException(
                                "Escritório com id " + request.donoDTO().getEscritorioId() + " não existe."
                        ));
                dono.setEscritorio(escritorio);
            }

            donoRepository.save(dono);
        }

        if (request.role() == Role.CLIENTE) {
            ClienteModel cliente = clienteMapper.dtoToModel(request.clienteDTO());
            cliente.setUsuario(novoUsuario);

            ChatModel chat = new ChatModel();
            chat.setCliente(cliente);

            List<ChatModel> chats = new ArrayList<>();
            chats.add(chat);
            cliente.setChats(chats);

            clienteRepository.save(cliente);
        }

        if (request.role() == Role.ADVOGADO) {
            AdvogadoModel advogado = advogadoMapper.dtoToModel(request.advogadoDTO());
            advogado.setUsuario(novoUsuario);

            if (request.advogadoDTO().getEscritorioId() != null) {
                EscritorioModel escritorio = escritorioRepository.findById(request.advogadoDTO().getEscritorioId())
                        .orElseThrow(() -> new NotFindObjectByIdentifierException(
                                "Escritório com id " + request.advogadoDTO().getEscritorioId() + " não existe."
                        ));
                advogado.setEscritorio(escritorio);
            }

            advogadoRepository.save(advogado);
        }

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
