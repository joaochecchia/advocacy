package com.advocacychat.backend.controlller;

import com.advocacychat.backend.config.TokenConfig;
import com.advocacychat.backend.enums.TipoUsuario;
import com.advocacychat.backend.model.AdvogadoModel;
import com.advocacychat.backend.model.ClienteModel;
import com.advocacychat.backend.model.UsuarioModel;
import com.advocacychat.backend.request.AlterarSenhaRequest;
import com.advocacychat.backend.request.LoginRequest;
import com.advocacychat.backend.request.UsuarioRequest;
import com.advocacychat.backend.response.LoginResponse;
import com.advocacychat.backend.response.UsuarioResponse;
import com.advocacychat.backend.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth/")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService userService;

    private final AuthenticationManager authenticationManager;

    private final TokenConfig tokenConfig;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody UsuarioRequest request){
        Optional<UsuarioResponse> newUser = userService.registerUser(request);

        Map<String, Object> response = new HashMap<>();
        response.put("Message", "Usuario registrado com sucesso.");
        response.put("Body", newUser.get());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login (@RequestBody LoginRequest request){
        UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(request.email(), request.senha());
        Authentication authentication = authenticationManager.authenticate(userAndPass);

        UsuarioModel usuario = (UsuarioModel) authentication.getPrincipal();

        String token = tokenConfig.gerarToken(usuario);

        Map<String, Object> response = new HashMap<>();
        response.put("Message", "Usuario logado com sucesso.");
        response.put("Body", new LoginResponse(
                usuario.getId(),
                usuario.getEmail(),
                token
        ));

        System.out.println("TOKEN: " + token);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/alterarSenha/{id}")
    public ResponseEntity<Map<String, Object>> alterarSenha(@PathVariable Long id, @RequestBody AlterarSenhaRequest request){
        String novaSenha = userService.alterarSenha(id, request);

        Map<String, Object> response = new HashMap<>();
        response.put("Message", novaSenha);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/teste")
    public String teste(){
        return "Teste concluido";
    }

}
