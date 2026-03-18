package com.advocacychat.backend.mapper;

import com.advocacychat.backend.dto.PagamentoDTO;
import com.advocacychat.backend.model.*;
import org.springframework.stereotype.Component;

@Component
public class PagamentoMapper {

    public static PagamentoDTO modelToDto(PagamentoModel model) {
        if (model == null) return null;

        PagamentoDTO dto = new PagamentoDTO();
        dto.setId(model.getId());
        dto.setAssinaturaId(model.getAssinatura() != null ? model.getAssinatura().getId() : null);
        dto.setPagarmeTransaction(model.getPagarmeTransaction());
        dto.setValor(model.getValor());
        dto.setStatus(model.getStatus());
        dto.setMetodo(model.getMetodo());
        dto.setPaidAt(model.getPaidAt());

        return dto;
    }

    public static PagamentoModel dtoToModel(PagamentoDTO dto) {
        if (dto == null) return null;

        PagamentoModel model = new PagamentoModel();
        model.setId(dto.getId());

        if (dto.getAssinaturaId() != null) {
            AssinaturaModel a = new AssinaturaModel();
            a.setId(dto.getAssinaturaId());
            model.setAssinatura(a);
        }

        model.setPagarmeTransaction(dto.getPagarmeTransaction());
        model.setValor(dto.getValor());
        model.setStatus(dto.getStatus());
        model.setMetodo(dto.getMetodo());
        model.setPaidAt(dto.getPaidAt());

        return model;
    }
}
