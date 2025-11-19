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
        dto.setClienteId(model.getClienteModel() != null ? model.getClienteModel().getId() : null);
        dto.setPlanoId(model.getPlano() != null ? model.getPlano().getId() : null);
        dto.setGateway(model.getGateway());
        dto.setGatewayCustomerId(model.getGatewayCustomerId());
        dto.setPaymentMethodToken(model.getPaymentMethodToken());
        dto.setReferenciaGateway(model.getReferenciaGateway());
        dto.setStatusPagamento(model.getStatusPagamento());
        dto.setDataPagamento(model.getDataPagamento());
        dto.setDataProximaCobranca(model.getDataProximaCobranca());
        dto.setValor(model.getValor());
        dto.setCriadoEm(model.getCriadoEm());

        return dto;
    }

    public static PagamentoModel dtoToModel(PagamentoDTO dto) {
        if (dto == null) return null;

        PagamentoModel model = new PagamentoModel();
        model.setId(dto.getId());

        if (dto.getClienteId() != null) {
            ClienteModel c = new ClienteModel();
            c.setId(dto.getClienteId());
            model.setClienteModel(c);
        }

        if (dto.getPlanoId() != null) {
            PlanoAssinaturaModel p = new PlanoAssinaturaModel();
            p.setId(dto.getPlanoId());
            model.setPlano(p);
        }

        model.setGateway(dto.getGateway());
        model.setGatewayCustomerId(dto.getGatewayCustomerId());
        model.setPaymentMethodToken(dto.getPaymentMethodToken());
        model.setReferenciaGateway(dto.getReferenciaGateway());
        model.setStatusPagamento(dto.getStatusPagamento());
        model.setDataPagamento(dto.getDataPagamento());
        model.setDataProximaCobranca(dto.getDataProximaCobranca());
        model.setValor(dto.getValor());
        model.setCriadoEm(dto.getCriadoEm());

        return model;
    }
}
