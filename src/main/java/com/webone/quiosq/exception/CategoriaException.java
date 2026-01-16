package com.webone.quiosq.exception;

public class CategoriaException extends BaseException {


    public CategoriaException(CodeErro erro) {
        super(erro.getMessage(), erro.getCod(),
            erro.getDetails(), erro.getHttpStatus());
    }
}
