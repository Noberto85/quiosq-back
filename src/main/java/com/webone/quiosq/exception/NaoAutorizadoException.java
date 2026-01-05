package com.webone.quiosq.exception;

public class NaoAutorizadoException extends BaseException {


    public NaoAutorizadoException(CodeErro erro) {
        super(erro.getMessage(), erro.getCod(),
            erro.getDetails(), erro.getHttpStatus());
    }
}
