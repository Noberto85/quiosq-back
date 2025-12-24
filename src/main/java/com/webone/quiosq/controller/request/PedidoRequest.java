package com.webone.quiosq.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.webone.quiosq.dto.ItemDTO;
import com.webone.quiosq.dto.PaymentDTO;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class PedidoRequest {

    private UUID quiosqueId;
    private Integer mesa;
    private String clienteId;
    private List<ItemDTO> items;
    private PaymentDTO pagamento;
    private BigDecimal total;
    @JsonIgnoreProperties
    private String codePedido;
    @JsonIgnoreProperties
    private Long PedidoId;

}
