package com.webone.quiosq.service.impl;

import com.webone.quiosq.controller.response.QrCodeGeneratePDFDto;
import com.webone.quiosq.service.JasperReportService;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class JasperReportImpl implements JasperReportService {

    private static final String REPORT_PATH = "/jasper/qrcode/qrcode.jrxml";

    /**
     * Obtém o InputStream do arquivo JRXML dentro do classpath.
     */
    private InputStream getReportStream() {
        InputStream stream = getClass().getResourceAsStream(REPORT_PATH);
        if (stream == null) {
            throw new IllegalStateException("Relatório não encontrado em: " + REPORT_PATH);
        }
        return stream;
    }

    /**
     * Cria diretório se não existir.
     */
    private void createDir(String name) {
        File dir = new File(name);
        if (!dir.exists() && !dir.mkdirs()) {
            throw new IllegalStateException("Não foi possível criar diretório: " + name);
        }
    }

    @Override
    @SneakyThrows
    public byte[] buildPfd(List<QrCodeGeneratePDFDto> listDtos) {
        List<QrCodeItem> qrCodeItems = listDtos.stream()
            .map(QrCodeItem::new)
            .toList();

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(qrCodeItems);
        Map<String, Object> params = new HashMap<>();

        try (InputStream reportStream = getReportStream()) {
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            JasperPrint print = JasperFillManager.fillReport(jasperReport, params, dataSource);
            return JasperExportManager.exportReportToPdf(print);
        } catch (JRException e) {
            throw new RuntimeException("Erro ao gerar relatório Jasper", e);
        }
    }
}
