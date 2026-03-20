package com.advocacychat.backend.dto;

import lombok.Data;

@Data
public class DonoDTO {

    Long id;

    Long usuarioId;

    Long escritorioId;

    String telefone;
}
