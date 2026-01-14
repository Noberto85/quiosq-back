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

    // Constantes comuns
    private static class Constants {
        private static final String SEM_DETALHES = "Sem detalhes";
    }

    // ------------------- ERROS GERAIS -------------------
    @Getter
    public enum GeralError {
        NAO_ENCONTRADO(new CodeErro(-1001, "Recurso não encontrado", Constants.SEM_DETALHES, HttpStatus.NOT_FOUND)),
        JA_ESTA_CADASTRADO(new CodeErro(-1002, "Já existe registro cadastrado", Constants.SEM_DETALHES, HttpStatus.CONFLICT));

        private final CodeErro codeErro;
        GeralError(CodeErro codeErro) { this.codeErro = codeErro; }
    }

    // ------------------- ERROS QUIOSQUE -------------------
    @Getter
    public enum QuiosqueError {
        MESA_NAO_ENCONTRADO(new CodeErro(-2001, "Mesa não encontrada", Constants.SEM_DETALHES, HttpStatus.NOT_FOUND)),
        QUIOSQUE_NAO_ENCONTRADO(new CodeErro(-2002, "Quiosque não encontrado", Constants.SEM_DETALHES, HttpStatus.NOT_FOUND));

        private final CodeErro codeErro;
        QuiosqueError(CodeErro codeErro) { this.codeErro = codeErro; }
    }

    // ------------------- ERROS ROLE -------------------
    @Getter
    public enum RoleError {
        PERFIL_NAO_ENCONTRADO(new CodeErro(-3001, "Perfil não encontrado", Constants.SEM_DETALHES, HttpStatus.NOT_FOUND));

        private final CodeErro codeErro;
        RoleError(CodeErro codeErro) { this.codeErro = codeErro; }
    }

    // ------------------- ERROS AUTENTICAÇÃO -------------------
    @Getter
    public enum AuthError {
        AUTH_ERROR(new CodeErro(-4001, "Autenticação não realizada", Constants.SEM_DETALHES, HttpStatus.UNAUTHORIZED));

        private final CodeErro codeErro;
        AuthError(CodeErro codeErro) { this.codeErro = codeErro; }
    }

    // ------------------- ERROS PEDIDO -------------------
    @Getter
    public enum PedidoError {
        PEDIDO_ERROR(new CodeErro(-5001, "Pedido não encontrado", Constants.SEM_DETALHES, HttpStatus.NOT_FOUND));

        private final CodeErro codeErro;
        PedidoError(CodeErro codeErro) { this.codeErro = codeErro; }
    }

    // ------------------- ERROS PAGAMENTO -------------------
    @Getter
    public enum PagamentoError {
        PAGAMENTO_ERROR(new CodeErro(-6001, "Pagamento não encontrado", Constants.SEM_DETALHES, HttpStatus.NOT_FOUND));

        private final CodeErro codeErro;
        PagamentoError(CodeErro codeErro) { this.codeErro = codeErro; }
    }

    // ------------------- ERROS NÃO AUTORIZADO -------------------
    @Getter
    public enum NaoAutorizadoError {
        SUSPEITA_FRAUDE_ERROR(new CodeErro(-7001, "Suspeita de fraude", Constants.SEM_DETALHES, HttpStatus.UNAUTHORIZED));

        private final CodeErro codeErro;
        NaoAutorizadoError(CodeErro codeErro) { this.codeErro = codeErro; }
    }

    // ------------------- ERROS MESA -------------------
    @Getter
    public enum MesaError {
        NUMERO_CADASTRADO_ERROR(new CodeErro(-8001, "A mesa %s ja vinculado a outro Garcom!", Constants.SEM_DETALHES, HttpStatus.CONFLICT));

        private final CodeErro codeErro;
        MesaError(CodeErro codeErro) { this.codeErro = codeErro; }
    }
}