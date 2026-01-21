package com.webone.quiosq.service.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.webone.quiosq.config.JwtTokenService;
import com.webone.quiosq.controller.response.QrCodeGeneratePDFDto;
import com.webone.quiosq.controller.response.QrCodeGenerateResponse;
import com.webone.quiosq.entity.Mesa;
import com.webone.quiosq.exception.CodeErro.QuiosqueError;
import com.webone.quiosq.exception.NotFoundException;
import com.webone.quiosq.repository.MesaRepository;
import com.webone.quiosq.service.QrCodeService;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class QrCodeServiceImpl implements QrCodeService {

    private final MesaRepository mesaRepository;
    private final JwtTokenService jwtService;

    private static final int WIDTH = 200;
    private static final int HEIGHT = 200;
    private static final String PNG = "PNG";

    private final String frontUrl;

    public QrCodeServiceImpl(MesaRepository mesaRepository, JwtTokenService jwtService,
        @Value("${web-front}") String frontUrl) {
        this.mesaRepository = mesaRepository;
        this.jwtService = jwtService;
        this.frontUrl = frontUrl;
    }

    @Override
    public QrCodeGenerateResponse generateAndUploadQrCode(UUID quisoqueId, Integer numero)
        throws WriterException, IOException {
        var mesa = mesaRepository.getID(quisoqueId, numero).orElseThrow(() -> new NotFoundException(
            QuiosqueError.MESA_NAO_ENCONTRADO.getCodeErro()));
        return generateAndUploadQrCode(String.format("login/%s",
            jwtService.generateClientWithoutExpiration(quisoqueId, numero, mesa)));
    }

    @Override
    public List<QrCodeGeneratePDFDto> generateAndUploadQrCode(List<Long> ids) {
        return mesaRepository.findAllById(ids)
            .stream().map(this::getQrCodeGeneratePDFDto)
            .collect(Collectors.toList());

    }

    private QrCodeGeneratePDFDto getQrCodeGeneratePDFDto(Mesa mesa) {
        try {

            return new QrCodeGeneratePDFDto(
                generateAndUploadQrCode(String.format("login/%s",
                    jwtService.generateClientWithoutExpiration(mesa.getQuiosque().getId(),
                        mesa.getNumero(), mesa.getId()))), mesa);
        } catch (WriterException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private QrCodeGenerateResponse generateAndUploadQrCode(String text)
        throws WriterException, IOException {
        String message = String.format("%s/%s", frontUrl, text);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(message, BarcodeFormat.QR_CODE, WIDTH, HEIGHT);
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, PNG, pngOutputStream);
        byte[] pngQrCodeData = pngOutputStream.toByteArray();

        return new QrCodeGenerateResponse(pngQrCodeData, message);
    }
}
