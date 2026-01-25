package com.webone.quiosq.service.impl;

import com.webone.quiosq.controller.response.DashboardResponse;
import com.webone.quiosq.dto.TotalVendidoDto;
import com.webone.quiosq.entity.enums.StatusPedidoEnum;
import com.webone.quiosq.repository.ClienteRepository;
import com.webone.quiosq.repository.ItemPedidoRepository;
import com.webone.quiosq.repository.PedidoRepository;
import com.webone.quiosq.service.DashboardService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private PedidoRepository repository;
    private ClienteRepository clienteRepository;
    private ItemPedidoRepository itemPedidoRepository;

    @Override
    public DashboardResponse load(UUID quiosqueId) {

        // Hoje
        LocalDate hoje = LocalDate.now();
        LocalDateTime inicioDia = hoje.atStartOfDay();
        LocalDateTime fimDia = hoje.atTime(LocalTime.MAX);

        // Primeiro e último dia do mês atual
        LocalDate primeiroDiaMes = hoje.withDayOfMonth(1);
        LocalDateTime inicioMes = primeiroDiaMes.atStartOfDay();

        LocalDate ultimoDiaMes = hoje.withDayOfMonth(hoje.lengthOfMonth());
        LocalDateTime fimMes = ultimoDiaMes.atTime(LocalTime.MAX);

        // Últimos pedidos do dia
        var ultimosPedidos = repository.getUltimosPedidos(quiosqueId, inicioDia, fimDia);

        // Receita do dia (usando intervalo do dia)
        BigDecimal receitaDoDia = repository.calcularReceitaDoDia(quiosqueId, inicioDia, fimDia,
            StatusPedidoEnum.ENTREGUE).orElse(new BigDecimal("0"));

        BigDecimal receitaDoMes = repository.calcularReceitaDoDia(quiosqueId, inicioMes, fimMes,
            StatusPedidoEnum.ENTREGUE).orElse(new BigDecimal("0"));

        // Últimos clientes cadastrados no mês
        var ultimosCliCad = clienteRepository.getUltimosClientesCadastrados(quiosqueId, inicioMes,
            fimMes);

        var maisVendidos = itemPedidoRepository.findMaisVendidos(
            quiosqueId).stream().map(TotalVendidoDto::new).toList();

        return DashboardResponse.builder()
            .qtdUltimosClientes(ultimosCliCad)
            .qtdUltimodPedido(ultimosPedidos)
            .receitaDoDia(receitaDoDia)
            .receitaDoMes(receitaDoMes)
            .maisVendidos(maisVendidos)
            .build();
    }
}
