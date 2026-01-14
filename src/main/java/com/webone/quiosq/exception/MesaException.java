package com.webone.quiosq.exception;

public class MesaException extends BaseException {

    public MesaException(CodeErro erro, Integer numero) {
        super(format(erro.getMessage(),numero), erro.getCod(),
            erro.getDetails(), erro.getHttpStatus());
    }

    public MesaException(CodeErro erro) {
        super(erro.getMessage(), erro.getCod(),
            erro.getDetails(), erro.getHttpStatus());
    }

    private static String format(String value, Integer numero){
        return String.format(value,numero);
    }
}
