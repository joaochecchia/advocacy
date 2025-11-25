/**package com.advocacychat.backend.controlller;

import com.advocacychat.backend.dto.UsuarioDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UsuarioController {

    @GetMapping("getById/{id}")
    public ResponseEntity<UsuarioDTO> getUsuarioById(@PathVariable Long id){

    }
}**/
