package com.webone.quiosq.controller.response;

import com.webone.quiosq.entity.Mesa;
import lombok.Data;

@Data
public class QrCodeGeneratePDFDto {

    private byte[] url;
    private Integer numero;

    public QrCodeGeneratePDFDto(QrCodeGenerateResponse response, Mesa mesa) {
        this.url = response.getUrl();
        this.numero = mesa.getNumero();
    }
}
