package com.webone.quiosq.service.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.webone.quiosq.config.JwtTokenService;
import com.webone.quiosq.controller.response.QrCodeGenerateResponse;
import com.webone.quiosq.service.MesaService;
import com.webone.quiosq.service.QrCodeService;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class QrCodeServiceImpl implements QrCodeService {

    private final MesaService mesaService;
    private final JwtTokenService jwtService;

    private static final int WIDTH = 200;
    private static final int HEIGHT = 200;
    private static final String PNG = "PNG";

    private final String frontUrl;

    public QrCodeServiceImpl(MesaService mesaService, JwtTokenService jwtService,
        @Value("${front-url}") String frontUrl) {
        this.mesaService = mesaService;
        this.jwtService = jwtService;
        this.frontUrl = frontUrl;
    }

    @Override
    public QrCodeGenerateResponse generateAndUploadQrCode(UUID quisoqueId, Integer numero)
        throws WriterException, IOException {


        return generateAndUploadQrCode(String.format("login/%s",
            jwtService.generateClientWithoutExpiration(quisoqueId, numero,mesaService.findByQuiosqueAndMesa(quisoqueId, numero))));
    }

    @Override
    public QrCodeGenerateResponse generateAndUploadQrCode(String text)
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
