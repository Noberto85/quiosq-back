package com.webone.quiosq.service;

import org.springframework.web.servlet.view.RedirectView;

public interface TokenService {

    RedirectView createUserQuisqu(String code);
}
