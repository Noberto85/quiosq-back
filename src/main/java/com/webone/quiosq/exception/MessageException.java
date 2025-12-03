package com.webone.quiosq.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Builder
@Getter
@JsonInclude(Include.NON_NULL)
public class MessageException {
    private final Integer cod;
    private final String message;
    private final String details;
    private HttpStatusCode status;

}
