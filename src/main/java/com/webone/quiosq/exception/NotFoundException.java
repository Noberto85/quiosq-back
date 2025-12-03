package com.webone.quiosq.exception;

public class NotFoundException extends BaseException {


    public NotFoundException(CodeErro erro) {
        super(erro.getMessage(), erro.getCod(),
            erro.getDetails(), erro.getHttpStatus());
    }
}
