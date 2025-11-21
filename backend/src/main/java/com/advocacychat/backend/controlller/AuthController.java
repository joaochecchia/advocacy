package com.advocacychat.backend.controlller;

import com.advocacychat.backend.config.TokenConfig;
import com.advocacychat.backend.mapper.UsuarioMapper;
import com.advocacychat.backend.model.UsuarioModel;
import com.advocacychat.backend.request.LoginRequest;
import com.advocacychat.backend.request.UsuarioRequest;
import com.advocacychat.backend.response.LoginResponse;
import com.advocacychat.backend.response.UsuarioResponse;
import com.advocacychat.backend.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth/")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService userService;

    private final UsuarioMapper usuarioMapper;

    private final AuthenticationManager authenticationManager;

    private final TokenConfig tokenConfig;

    @PostMapping("/register")
    public ResponseEntity<UsuarioResponse> register(@RequestBody UsuarioRequest request){
        Optional<UsuarioResponse> newUser = userService.registerUser(request);

        return ResponseEntity.ok(newUser.get());
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login (@RequestBody LoginRequest request){
        UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(request.email(), request.senha());
        Authentication authentication = authenticationManager.authenticate(userAndPass);

        UsuarioModel usuario = (UsuarioModel) authentication.getPrincipal();

        String token = tokenConfig.gerarToken(usuario);

        System.out.println("TOKEN: " + token);

        return ResponseEntity.ok(new LoginResponse(
                usuario.getEmail(),
                usuario.getPassword(),
                token
        ));
    }
}
