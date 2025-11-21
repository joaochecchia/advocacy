package com.advocacychat.backend.controlller;

import com.advocacychat.backend.dto.UsuarioDTO;
import com.advocacychat.backend.mapper.UsuarioMapper;
import com.advocacychat.backend.response.UsuarioResponse;
import com.advocacychat.backend.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/register")
    public ResponseEntity<UsuarioResponse> register(@RequestBody UsuarioDTO usuarioDTO){
        Optional<UsuarioResponse> newUser = userService.registerUser(usuarioDTO);

        return ResponseEntity.ok(newUser.get());
    }
}
