package com.webone.quiosq.controller.request;

import com.webone.quiosq.entity.enums.StatusPedidoEnum;

public record PedidoUpdateStatusRequest(StatusPedidoEnum status) {

}
