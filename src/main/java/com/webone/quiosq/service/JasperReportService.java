package com.webone.quiosq.service;

import com.webone.quiosq.controller.response.QrCodeGeneratePDFDto;
import java.util.List;

public interface JasperReportService {
    byte[] buildPfd(List<QrCodeGeneratePDFDto> listDtos);
}
