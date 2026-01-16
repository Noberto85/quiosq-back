package com.webone.quiosq.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SqlException extends BaseException {


    public SqlException(CodeErro erro) {
        super(erro.getMessage(), erro.getCod(),
            erro.getDetails(), erro.getHttpStatus());
    }

}
