package com.webone.quiosq.service.impl;

import com.webone.quiosq.controller.response.QrCodeGeneratePDFDto;
import com.webone.quiosq.service.JasperReportService;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

@Service
@AllArgsConstructor
@Slf4j
public class JasperReportImpl implements JasperReportService {

    private static final String QR_CODE = "classpath:jasper/qrcode/";
    private static final String ARQUIVO_JRXML = "qrcode.jrxml";
    private static final String DESTINO_PDF = "c:\\jasper-report\\";


    public void gerar() throws FileNotFoundException {

        String base64String = "iVBORw0KGgoAAAANSUhEUgAAAMgAAADIAQAAAACFI5MzAAADVElEQVR4Xu3WPdKkKBgAYEzwCpjA1SDBK2ACkuAVINGrSYJX0ATm7f6qdumpYTTdrSbk6bLx/RNUWgv9vvHP+spXXusr/2nJCMvecT3EI86KI/NETBIrEiGCODLmR8LHsqwESU4HjyR+KDsLmWgRY3BPRSJMNULjjqVIz8QkrczltFkWPx6f79MSiM6/6/e4/VnA2BK2XY8Z98fPzp3k3k0z4gpJqiRGyjyQYr2xvoPwsJUFT55Ipnjc4rXzaWZxZe6RYBN3BGcmJjrJttfevUB0OJeTpxhDcT2SafPdFc+peBZCWJ5IOewyExOyRnQIIZkHkvtVD+sY1iEpRTF7//pOiDSOK3FSSVHnxCORtN/sSbvgqQlrXYltMYnCceFhuPMc00fSndOSCcSIq3FGdUSbUmIyu1jizpGiSrNHspvLXtAdkIQN0vBEMksc/n/WnF0xhLqqmlJOwpEcglcc+rh+07ZkGF9ej8sMvag10k+k7Gy7ygUHXoct+CoLfxEIvaaYSDxcTtVd8heJGcOLDuEqJxLpIwtN8cjM0CbQ9ZpQVFVVW7LUBkbyrtkGh9YPBcop2lWKxZZV1hFtSnF6evUJTMttpvqRZI76HcYXghwo8lGjbekzUdPmWFnZ5oefeXkjJYlz2nKXNIE2rr8YbcmihJMdNiZ2jn7c3r++EciXRGPiYsUai1znpy1wbhvj+cobhUZ5JPa49ncXC9/PvH6ftqwwjel0ROuknOL23rsTa0OZucYUpvP008B3knsIqB8yhoakLNZfzabAdIGZf0JXWTeVVH+zmpIVUZjAsIzbKrtcV0hboHrhcykcxVpO11w9rSmlXHm4rHVk2Fa2xPfWndgluAlSLgeviKi7vil5ukrisH8lCpcI9v71neDOpvEUh4OvDMd19bZFEc7KqQdoergG1PlpStkhnlR4pboruPpm15aM9bBEL+zJnORDPf3b0sXU72JHVGuKEK+e1hQwZqF0NXQIXG3qSd6UjPrjyBLJcVUaf3R9W+A2iOB+oscjaaqn196t8BGGrJhhKr/unvUJ/iYwWiEBKyVUie3jaU2RwuvBdTZuy0nrLmmLSSwsXpMhifx5S2sLwqTb4aIGXYLlWFdvU/64vvKV1/rK/09+Ae6UcAatA4mWAAAAAElFTkSuQmCC";
        byte[] decodedBytes = Base64.getDecoder().decode(base64String);
        InputStream inputStream = new ByteArrayInputStream(decodedBytes);
        var qe1 = new QrCodeItem(1, inputStream);
        var qe2 = new QrCodeItem(2, inputStream);
        Map<String, Object> params = new HashMap<>();
        params.put("numero", 1);
        params.put("qrcode", inputStream);

        String caminhoAbsoluto = getCaminhoAbsoluto();
        log.info("CAMINHO ABSOLUTO {}", caminhoAbsoluto);

        String dirDestino = getDiretorioSavea("certificados-salvos");
        try {
            final JasperReport jasperReport = JasperCompileManager.compileReport(caminhoAbsoluto);
            final JasperPrint print = JasperFillManager.fillReport(jasperReport, params,
                new JREmptyDataSource());
            log.info("Jasper print");
            JasperExportManager.exportReportToPdfFile(print, dirDestino);
            log.info("JCONCLUIDO");
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }

    public void gerar2() throws FileNotFoundException {

        List<String> listaBase64 = List.of(
            "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"
            // pode repetir ou adicionar outros
        );

        List<QrCodeItem> lista = new ArrayList<>();

        int contador = 1;
        for (String base64 : listaBase64) {
            byte[] decodedBytes = Base64.getDecoder().decode(base64);
            InputStream inputStream = new ByteArrayInputStream(decodedBytes);

            lista.add(new QrCodeItem(contador++, inputStream));
        }

        String caminhoAbsoluto = getCaminhoAbsoluto();
        log.info("CAMINHO ABSOLUTO {}", caminhoAbsoluto);

        String dirDestino = getDiretorioSavea("certificados-salvos");

        try {
            JRBeanCollectionDataSource dataSource =
                new JRBeanCollectionDataSource(lista);

            Map<String, Object> params = new HashMap<>();

            JasperReport jasperReport =
                JasperCompileManager.compileReport(caminhoAbsoluto);

            JasperPrint print =
                JasperFillManager.fillReport(jasperReport, params, dataSource);

            JasperExportManager.exportReportToPdfFile(print, dirDestino);

            log.info("CONCLUÍDO");
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }

    private String getCaminhoAbsoluto() throws FileNotFoundException {
        return ResourceUtils.getFile(QR_CODE + ARQUIVO_JRXML).getAbsolutePath();
    }

    public String getDiretorioSavea(String nome) {
        createDir(DESTINO_PDF);
        return DESTINO_PDF + nome.concat(".pdf");
    }

    public void createDir(String name) {
        File dir = new File(name);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }


    @SneakyThrows
    @Override
    public byte[] buildPfd(List<QrCodeGeneratePDFDto> listDtos) {
        List<QrCodeItem> qrCodeItems = listDtos.stream().map(QrCodeItem::new).toList();
        String caminhoAbsoluto = getCaminhoAbsoluto();
        log.info("CAMINHO ABSOLUTO {}", caminhoAbsoluto);

        try {
            JRBeanCollectionDataSource dataSource =
                new JRBeanCollectionDataSource(qrCodeItems);

            Map<String, Object> params = new HashMap<>();

            JasperReport jasperReport =
                JasperCompileManager.compileReport(caminhoAbsoluto);

            JasperPrint print =
                JasperFillManager.fillReport(jasperReport, params, dataSource);
            return JasperExportManager.exportReportToPdf(print);


        } catch (JRException e) {
            throw new RuntimeException(e);
        }

    }
}
