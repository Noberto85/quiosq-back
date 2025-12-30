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
    public enum GeralError {

       NAO_ENCONTRADO(
            new CodeErro(-1001, "Pesquisa não encontrado!", Constants.SEM_DETALHES,
                HttpStatus.NOT_FOUND)

        );

        private final CodeErro codeErro;

        GeralError(CodeErro codeErro) {
            this.codeErro = codeErro;
        }

    }

    @Getter
    public enum QuiosqueError {

        MESA_NAO_ENCONTRADO(
            new CodeErro(-2001, "Mesa não encontrado!", Constants.SEM_DETALHES,
                HttpStatus.NOT_FOUND)

        ),
        QUIOSQUE_NAO_ENCONTRADO(
            new CodeErro(-2002, "Quiosque não encontrado", Constants.SEM_DETALHES,
                HttpStatus.NOT_FOUND)

        );

        private final CodeErro codeErro;

        QuiosqueError(CodeErro codeErro) {
            this.codeErro = codeErro;
        }

    }
    @Getter
    public enum RoleError {

            PERFIL_NAO_ENCONTRADO(
            new CodeErro(-3001, "Perfil não encontrado!", Constants.SEM_DETALHES,
                HttpStatus.NOT_FOUND)

        );

        private final CodeErro codeErro;

        RoleError(CodeErro codeErro) {
            this.codeErro = codeErro;
        }

    }

    @Getter
    public enum AuthError {

        AUTH_ERROR(
            new CodeErro(-4001, "Autenticação não realizada!", Constants.SEM_DETALHES,
                HttpStatus.UNAUTHORIZED)

        );

        private final CodeErro codeErro;

        AuthError(CodeErro codeErro) {
            this.codeErro = codeErro;
        }

    }

    @Getter
    public enum PedidoError {

        PEDIDO_ERROR(
            new CodeErro(-5001, "Pedido não encotrado", Constants.SEM_DETALHES,
                HttpStatus.UNAUTHORIZED)

        );

        private final CodeErro codeErro;

        PedidoError(CodeErro codeErro) {
            this.codeErro = codeErro;
        }

    }

    @Getter
    public enum PagamentoError {

        PAGAMENTO_ERROR(
            new CodeErro(-6001, "Pagamento Não encotrado", Constants.SEM_DETALHES,
                HttpStatus.UNAUTHORIZED)

        );

        private final CodeErro codeErro;

        PagamentoError(CodeErro codeErro) {
            this.codeErro = codeErro;
        }

    }


    private static class Constants {

        private static final String SEM_DETALHES = "Sem detalhes";
    }
}