package com.webone.quiosq.exception;

public class ClienteException extends BaseException {


    public ClienteException(CodeErro erro) {
        super(erro.getMessage(), erro.getCod(),
            erro.getDetails(), erro.getHttpStatus());
    }
}
