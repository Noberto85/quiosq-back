package com.webone.quiosq.controller;

import com.webone.quiosq.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@CrossOrigin("${notification.base}")
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class TokenController {

    private final TokenService service;

    @GetMapping("/token")
    public RedirectView authenticateUser(@RequestParam("code") String code) {
        return service.createUserQuisqu(code);

    }

}
