package com.webone.quiosq.controller.response;

import com.webone.quiosq.dto.TotalVendidoDto;
import com.webone.quiosq.projection.TotalVendidoProjection;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class DashboardResponse {

    private Long qtdUltimodPedido;
    private Long qtdUltimosClientes;
    private BigDecimal receitaDoDia;
    private BigDecimal receitaDoMes;
    private List<TotalVendidoDto> maisVendidos;
}
