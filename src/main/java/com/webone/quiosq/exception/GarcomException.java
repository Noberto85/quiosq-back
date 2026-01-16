package com.webone.quiosq.exception;

public class GarcomException extends BaseException {


    public GarcomException(CodeErro erro) {
        super(erro.getMessage(), erro.getCod(),
            erro.getDetails(), erro.getHttpStatus());
    }
}
