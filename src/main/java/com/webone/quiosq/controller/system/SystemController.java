package com.webone.quiosq.controller.system;

import com.webone.quiosq.dto.EstastisticasDto;
import com.webone.quiosq.service.SystemService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/system")
@AllArgsConstructor
public class SystemController {

    private final SystemService systemService;

    @GetMapping("/load-dash")
    public ResponseEntity<EstastisticasDto> loadDash(
    ) {
        return new ResponseEntity<>(
            systemService.loadDash(),
            HttpStatus.OK);

    }
}
