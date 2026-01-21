package com.webone.quiosq.service.impl;

import com.google.zxing.WriterException;
import com.webone.quiosq.controller.request.MesaQrcodeDownload;
import com.webone.quiosq.controller.request.MesaRequest;
import com.webone.quiosq.controller.response.MesaQrcodeResponse;
import com.webone.quiosq.controller.response.MesaResponse;
import com.webone.quiosq.controller.response.QrCodeGeneratePDFDto;
import com.webone.quiosq.dto.MesaProjectionDto;
import com.webone.quiosq.dto.PageableDto;
import com.webone.quiosq.entity.Garcom;
import com.webone.quiosq.entity.Mesa;
import com.webone.quiosq.entity.Quiosque;
import com.webone.quiosq.entity.enums.StatusMesaEnum;
import com.webone.quiosq.exception.CodeErro.GeralError;
import com.webone.quiosq.exception.CodeErro.MesaError;
import com.webone.quiosq.exception.CodeErro.QuiosqueError;
import com.webone.quiosq.exception.GarcomException;
import com.webone.quiosq.exception.MesaException;
import com.webone.quiosq.exception.NotFoundException;
import com.webone.quiosq.exception.QuiosqueException;
import com.webone.quiosq.projection.MesaInfoProjection;
import com.webone.quiosq.repository.GarcomRepository;
import com.webone.quiosq.repository.MesaRepository;
import com.webone.quiosq.repository.QuiosqueRepository;
import com.webone.quiosq.service.JasperReportService;
import com.webone.quiosq.service.MesaService;
import com.webone.quiosq.service.QrCodeService;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MesaServiceImpl implements MesaService {

    private final GarcomRepository repository;
    private final MesaRepository mesaRepository;
    private final QuiosqueRepository quiosqueRepository;
    private final QrCodeService qrCodeService;
    private final JasperReportService jasperReportService;

    @Override
    public void salvar(List<Mesa> mesalist) {
        mesaRepository.saveAll(mesalist);
    }

    @Override
    public MesaProjectionDto buildMesa(UUID quiosqueId, Long mesaId) {

        MesaInfoProjection mesaInfo = mesaRepository.getMesaInfo(quiosqueId, mesaId)
            .orElseThrow(() -> new NotFoundException(
                QuiosqueError.MESA_NAO_ENCONTRADO.getCodeErro()));
        return new MesaProjectionDto(mesaInfo);

    }

    @Override
    public Long findByQuiosqueAndMesa(UUID quiosqueId, Integer numero) {
        return mesaRepository.getID(quiosqueId, numero).orElseThrow(() -> new NotFoundException(
            QuiosqueError.MESA_NAO_ENCONTRADO.getCodeErro()));

    }

    @Override
    public List<Mesa> findByGarcom(Garcom garcom) {
        return mesaRepository.findByGarcom(garcom);
    }

    @Override
    public PageableDto<MesaResponse> findAllByPageableSpec(Specification<Mesa> spec, Integer page,
        Integer size, String orderBy, String direction) {
        final var pageRequest = PageRequest.of(page, size,
            Sort.by(Sort.Direction.valueOf(direction), orderBy));
        Page<MesaResponse> maplis = mesaRepository.findAll(spec, pageRequest)
            .map(MesaResponse::new);
        return new PageableDto<>(maplis);
    }

    @Override
    public void create(MesaRequest request, UUID quiosqueID) {
        final Optional<Mesa> mesaOpt = mesaRepository.findByNumeroAndQuiosque(
            quiosqueID, request.getNumero());
        if (mesaOpt.isPresent()) {
            throw new MesaException(MesaError.NUMERO_CADASTRADO_ERROR.getCodeErro(),
                request.getNumero());
        }
        var garcomOpt = repository.findById(request.getGarcomId());
        if (garcomOpt.isEmpty()) {
            throw new GarcomException(GeralError.NAO_ENCONTRADO.getCodeErro());
        }
        final Optional<Quiosque> quiosqueOpt = quiosqueRepository.findById(quiosqueID);
        if (quiosqueOpt.isEmpty()) {
            throw new QuiosqueException(QuiosqueError.QUIOSQUE_NAO_ENCONTRADO.getCodeErro());
        }
        var quiosque = quiosqueOpt.get();
        var garcom = garcomOpt.get();
        var mesa = Mesa.builder()
            .garcom(garcom)
            .numero(request.getNumero())
            .status(StatusMesaEnum.LIVRE)
            .quiosque(quiosque)
            .build();
        mesaRepository.save(mesa);

    }

    @Override
    public void update(MesaRequest request) {
        var mesa = mesaRepository.findById(request.getId())
            .orElseThrow(() -> new MesaException(
                MesaError.NUMERO_CADASTRADO_ERROR.getCodeErro(), request.getNumero()));
        var garcomOpt = repository.findById(request.getGarcomId());
        if (garcomOpt.isEmpty()) {
            throw new GarcomException(GeralError.NAO_ENCONTRADO.getCodeErro());
        }
        mesa.setGarcom(garcomOpt.get());
        mesaRepository.save(mesa);


    }

    @Override
    public void disable(Long id) {
        var mesa = mesaRepository.findById(id)
            .orElseThrow(() -> new MesaException(
                MesaError.NUMERO_CADASTRADO_ERROR.getCodeErro()));

        mesa.setGarcom(null);
        mesa.setStatus(StatusMesaEnum.LIVRE);
        mesa.setAtivo(Boolean.FALSE);
        mesaRepository.save(mesa);
    }

    @Override
    public MesaQrcodeResponse download(MesaQrcodeDownload request) {
        try {
            final List<QrCodeGeneratePDFDto> qrcode = qrCodeService.generateAndUploadQrCode(
                request.ids());
            return new MesaQrcodeResponse(jasperReportService.buildPfd(qrcode));

        } catch (WriterException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
