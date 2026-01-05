package com.webone.quiosq.exception;

import lombok.Getter;

@Getter
public class LoginException extends BaseException {
    private String mensagem;

    public LoginException(String mensagem,CodeErro erro) {
        super(erro.getMessage(), erro.getCod(),
            erro.getDetails(), erro.getHttpStatus());
        this.mensagem = mensagem;
    }
}
