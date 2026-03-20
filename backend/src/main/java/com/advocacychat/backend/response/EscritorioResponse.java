package com.advocacychat.backend.response;

import com.advocacychat.backend.model.EscritorioModel;

import java.time.LocalDateTime;

public record EscritorioResponse(
        Long id,
        String nomeEscritorio,
        String nomeDono,
        String cnpj,
        Long numeroAdvogados,
        LocalDateTime dataCriacao,
        LocalDateTime dataAtualizacao
) {

    public static EscritorioResponse fromModel(EscritorioModel model) {
        return new EscritorioResponse(
                model.getId(),
                model.getNomeEscritorio(),
                model.getNomeDono(),
                model.getCnpj(),
                model.getNumeroAdvogados(),
                model.getDataCriacao() != null ? model.getDataCriacao() : null,
                model.getDataAtualizacao() != null ? model.getDataAtualizacao() : null
        );
    }
}

