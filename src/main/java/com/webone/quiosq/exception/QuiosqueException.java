package com.webone.quiosq.exception;

public class QuiosqueException extends BaseException {


    public QuiosqueException(CodeErro erro) {
        super(erro.getMessage(), erro.getCod(),
            erro.getDetails(), erro.getHttpStatus());
    }
}
