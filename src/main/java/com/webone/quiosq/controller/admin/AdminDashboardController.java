package com.webone.quiosq.controller.admin;

import com.webone.quiosq.controller.response.DashboardResponse;
import com.webone.quiosq.service.DashboardService;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/dashboard")
@AllArgsConstructor
public class AdminDashboardController {

    private final DashboardService service;

    @GetMapping("/{quiosqueId}")
    public ResponseEntity<DashboardResponse> load(
        @PathVariable("quiosqueId") UUID quiosqueId
    ) {

        return new ResponseEntity<>(service.load(quiosqueId),
            HttpStatus.OK);

    }

}
