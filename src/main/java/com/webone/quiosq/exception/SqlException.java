package com.webone.quiosq.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SqlException extends BaseException{

    private String error;
    public SqlException(CodeErro erro, String error) {
        super(erro.getMessage(), erro.getCod(),
            erro.getDetails(), erro.getHttpStatus());
       this.error = error;
    }
}
