package com.advocacychat.backend.service;

import com.advocacychat.backend.enums.StatusReuniao;
import com.advocacychat.backend.exceptions.BusinessRuleException;
import com.advocacychat.backend.exceptions.NotFindObjectByIdentifierException;
import com.advocacychat.backend.model.AdvogadoModel;
import com.advocacychat.backend.model.ClienteModel;
import com.advocacychat.backend.model.ReuniaoModel;
import com.advocacychat.backend.repository.AdvogadoRepository;
import com.advocacychat.backend.repository.ClienteRepository;
import com.advocacychat.backend.repository.ReuniaoRepository;
import com.advocacychat.backend.response.ReuniaoResponse;
import com.advocacychat.backend.request.ReuniaoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReuniaoService {

    private final ReuniaoRepository reuniaoRepository;
    private final ClienteRepository clienteRepository;
    private final AdvogadoRepository advogadoRepository;

    /* ===================== CREATE ===================== */

    public Optional<ReuniaoResponse> criarReuniao(ReuniaoRequest request) {

        ClienteModel cliente = clienteRepository.findById(request.clienteId())
                .orElseThrow(() ->
                        new NotFindObjectByIdentifierException(
                                "Cliente com id " + request.clienteId() + " nao existe."
                        )
                );

        AdvogadoModel advogado = advogadoRepository.findById(request.advogadoId())
                .orElseThrow(() ->
                        new NotFindObjectByIdentifierException(
                                "Advogado com id " + request.advogadoId() + " nao existe."
                        )
                );

        boolean conflito = reuniaoRepository
                .existsByAdvogadoModel_IdAndDataHoraAndStatusNot(
                        advogado.getId(),
                        request.dataHora(),
                        StatusReuniao.CANCELADA
                );

        if (conflito) {
            throw new BusinessRuleException(
                    "Ja existe uma reuniao agendada para este advogado neste horario."
            );
        }

        ReuniaoModel reuniao = ReuniaoModel.builder()
                .clienteModel(cliente)
                .advogadoModel(advogado)
                .dataHora(request.dataHora())
                .duracaoMinutos(request.duracaoMinutos())
                .status(StatusReuniao.PENDENTE)
                .build();

        ReuniaoModel salva = reuniaoRepository.save(reuniao);

        return Optional.of(ReuniaoResponse.fromModel(salva));
    }

    /* ===================== READ ===================== */

    public Optional<ReuniaoResponse> buscarReuniaoPorId(Long id) {
        ReuniaoModel reuniao = reuniaoRepository.findById(id)
                .orElseThrow(() ->
                        new NotFindObjectByIdentifierException(
                                "Reuniao com id " + id + " nao existe."
                        )
                );

        return Optional.of(ReuniaoResponse.fromModel(reuniao));
    }

    /* ===================== UPDATE ===================== */

    public Optional<ReuniaoResponse> cancelarReuniao(Long id) {
        ReuniaoModel reuniao = reuniaoRepository.findById(id)
                .orElseThrow(() ->
                        new NotFindObjectByIdentifierException(
                                "Reuniao com id " + id + " nao existe."
                        )
                );

        reuniao.setStatus(StatusReuniao.CANCELADA);

        ReuniaoModel atualizada = reuniaoRepository.save(reuniao);

        return Optional.of(ReuniaoResponse.fromModel(atualizada));
    }
}