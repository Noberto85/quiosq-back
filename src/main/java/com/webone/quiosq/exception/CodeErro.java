package com.webone.quiosq.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class CodeErro {

    private final Integer cod;
    private final String message;
    private final String details;
    private final HttpStatus httpStatus;

    @Getter
    public enum ProvedorError {

        QUIOSQUE_NAO_ENCONTRADO(
            new CodeErro(-1001, "Quiosque não encontrado!", Constants.SEM_DETALHES,
                HttpStatus.NOT_FOUND));

        private final CodeErro codeErro;

        ProvedorError(CodeErro codeErro) {
            this.codeErro = codeErro;
        }

    }



    private static class Constants {

        private static final String SEM_DETALHES = "Sem detalhes";
    }
}