package com.webone.quiosq.exception;

public class TokenExpiradoException extends RuntimeException {

    public TokenExpiradoException(String mensagem) {
        super(mensagem);
    }

    public TokenExpiradoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
