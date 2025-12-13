package com.webone.quiosq.controller.request;

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
    private Integer clienteId;
    private List<ItemDTO> items;
    private PaymentDTO payment;
    private BigDecimal total;

}
