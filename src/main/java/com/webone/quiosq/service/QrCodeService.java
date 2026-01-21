package com.webone.quiosq.service;

import com.google.zxing.WriterException;
import com.webone.quiosq.controller.response.QrCodeGeneratePDFDto;
import com.webone.quiosq.controller.response.QrCodeGenerateResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface QrCodeService {
    QrCodeGenerateResponse generateAndUploadQrCode(UUID quisoqueId, Integer numero) throws WriterException, IOException;
    List<QrCodeGeneratePDFDto> generateAndUploadQrCode(List<Long> ids) throws WriterException, IOException;

}
