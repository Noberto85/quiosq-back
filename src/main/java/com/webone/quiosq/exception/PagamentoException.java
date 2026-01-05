package com.webone.quiosq.exception;

public class PagamentoException extends BaseException {

    public PagamentoException(CodeErro erro) {
        super(erro.getMessage(), erro.getCod(),
            erro.getDetails(), erro.getHttpStatus());
    }
}
