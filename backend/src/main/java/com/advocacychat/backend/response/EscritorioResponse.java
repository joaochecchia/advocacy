package com.advocacychat.backend.response;

import com.advocacychat.backend.model.EscritorioModel;

public record EscritorioResponse(
        Long id,
        String nomeEscritorio,
        String nomeDono,
        String cnpj,
        Long numeroAdvogados
) {

    public static EscritorioResponse fromModel(EscritorioModel model) {
        return new EscritorioResponse(
                model.getId(),
                model.getNomeEscritorio(),
                model.getNomeDono(),
                model.getCnpj(),
                model.getNumeroAdvogados()
        );
    }
}

