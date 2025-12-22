package com.advocacychat.backend.mapper;

import com.advocacychat.backend.dto.ReuniaoDTO;
import com.advocacychat.backend.model.AdvogadoModel;
import com.advocacychat.backend.model.ClienteModel;
import com.advocacychat.backend.model.ReuniaoModel;
import org.springframework.stereotype.Component;

@Component
public class ReuniaoMapper {

    /**
     * Converte entidade para DTO
     */
    public ReuniaoDTO modelToDto(ReuniaoModel model) {
        if (model == null) return null;

        ReuniaoDTO dto = new ReuniaoDTO();

        dto.setId(model.getId());
        dto.setClienteId(
                model.getClienteModel() != null ? model.getClienteModel().getId() : null
        );
        dto.setAdvogadoId(
                model.getAdvogadoModel() != null ? model.getAdvogadoModel().getId() : null
        );

        dto.setDataHora(model.getDataHora());
        dto.setDuracaoMinutos(model.getDuracaoMinutos());
        dto.setStatus(model.getStatus());

        dto.setGoogleEventId(model.getGoogleEventId());
        dto.setGoogleMeetLink(model.getGoogleMeetLink());

        dto.setCriadoEm(model.getCriadoEm());

        return dto;
    }

    /**
     * Converte DTO para entidade.
     *
     * ATENÇÃO:
     * - Este método NÃO deve ser usado para persistir diretamente.
     * - O Service é responsável por:
     *   - Buscar ClienteModel real
     *   - Buscar AdvogadoModel real
     *   - Validar conflitos
     */
    public ReuniaoModel dtoToModel(ReuniaoDTO dto) {
        if (dto == null) return null;

        ReuniaoModel model = new ReuniaoModel();

        model.setId(dto.getId());
        model.setDataHora(dto.getDataHora());
        model.setDuracaoMinutos(dto.getDuracaoMinutos());
        model.setStatus(dto.getStatus());
        model.setGoogleEventId(dto.getGoogleEventId());
        model.setGoogleMeetLink(dto.getGoogleMeetLink());

        if (dto.getClienteId() != null) {
            ClienteModel cliente = new ClienteModel();
            cliente.setId(dto.getClienteId());
            model.setClienteModel(cliente);
        }

        if (dto.getAdvogadoId() != null) {
            AdvogadoModel advogado = new AdvogadoModel();
            advogado.setId(dto.getAdvogadoId());
            model.setAdvogadoModel(advogado);
        }

        return model;
    }
}