package com.webone.quiosq.service.impl;

import com.webone.quiosq.controller.response.QrCodeGeneratePDFDto;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QrCodeItem {

    private String numero;
    private InputStream qrcode;

    public QrCodeItem(QrCodeGeneratePDFDto dto) {
        this.numero = dto.getNumero();
        this.qrcode = new ByteArrayInputStream(dto.getUrl());
    }
}
