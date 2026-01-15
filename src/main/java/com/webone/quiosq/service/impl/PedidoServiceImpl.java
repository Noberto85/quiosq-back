package com.webone.quiosq.service.impl;

import com.webone.quiosq.controller.request.PedidoRequest;
import com.webone.quiosq.controller.response.PedidoResponse;
import com.webone.quiosq.dto.ItemDTO;
import com.webone.quiosq.dto.PagamentoResponse;
import com.webone.quiosq.dto.PageableDto;
import com.webone.quiosq.entity.Pagamento;
import com.webone.quiosq.entity.Pedido;
import com.webone.quiosq.entity.enums.StatusPedidoEnum;
import com.webone.quiosq.exception.CodeErro.PedidoError;
import com.webone.quiosq.exception.CodeErro.RoleError;
import com.webone.quiosq.exception.NotFoundException;
import com.webone.quiosq.exception.SqlException;
import com.webone.quiosq.handler.PagamentoHandle;
import com.webone.quiosq.itg.response.PagamentoApiResponse;
import com.webone.quiosq.projection.PedidoProjection;
import com.webone.quiosq.repository.PedidoRepository;
import com.webone.quiosq.service.MercadoPagoTokenService;
import com.webone.quiosq.service.PagamentoService;
import com.webone.quiosq.service.PedidoService;
import com.webone.quiosq.utils.DateUtils;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Log4j2
public class PedidoServiceImpl implements PedidoService {

    private final List<PagamentoHandle> pagamentoHandle;
    private final PedidoRepository pedidoRepository;
    private final MercadoPagoTokenService mercadoPagoTokenService;
    private final PagamentoService pagamentoService;

    @Override
    @Transactional
    public PagamentoResponse createPedido(PedidoRequest request) {

        try {

            Optional<PedidoProjection> pedido = pedidoRepository.createPedido(request.getNome(),
                request.getQuiosqueId(), request.getMesa(),
                request.getClienteId(), buildItensList(request.getItems()));
            if (pedido.isEmpty()) {
                throw new NotFoundException(PedidoError.PEDIDO_ERROR.getCodeErro());
            }
            request.setPedidoId(pedido.get().getPedidoId());
            request.setCodePedido(pedido.get().getCodigoPedido());
            String accessToken = mercadoPagoTokenService.getAccessToken(request.getQuiosqueId());
            Iterator<PagamentoHandle> iterator = pagamentoHandle.iterator();
            PagamentoApiResponse pagamentoResponse;

            if (iterator.hasNext()) {
                PagamentoHandle next = iterator.next();
                pagamentoResponse = next.handleRequest(request, accessToken);
                Optional<Pedido> byId = pedidoRepository.findById(pedido.get().getPedidoId());
                var pag = pagamentoService.create(buildPagamento(pagamentoResponse, byId.get()));
                return new PagamentoResponse(pagamentoResponse, pag.getId());
            }
            return null;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new NotFoundException(RoleError.PERFIL_NAO_ENCONTRADO.getCodeErro());
        }
    }

    @Override
    public List<PedidoResponse> findPedido(UUID quisoqueID, Integer mesa) {
        List<Pedido> pedido = pedidoRepository.findPedido(quisoqueID, mesa);
        return pedido.stream().map(PedidoResponse::new).collect(Collectors.toList());
    }

    @Override
    public List<PedidoResponse> findByClienteId(UUID quiosqueId, String telefone) {
        return pedidoRepository.findByClienteId(quiosqueId, telefone,
                LocalDateTime.now().minusHours(12),
                LocalDateTime.now().plusHours(12)).stream().map(PedidoResponse::new)
            .collect(Collectors.toList());
    }

    @Override
    public PageableDto<PedidoResponse> findAllByPageableSpec(Specification<Pedido> spec,
        Integer page, Integer size, String orderBy, String direction) {
        final var pageRequest = PageRequest.of(page, size,
            Sort.by(Sort.Direction.valueOf(direction), orderBy));
        Page<PedidoResponse> maplis = pedidoRepository.findAll(spec, pageRequest)
            .map(PedidoResponse::new);
        return new PageableDto<>(maplis);
    }

    @Override
    public void updateStatus(Long id, StatusPedidoEnum status) {
        final Pedido pedido = findById(id);
        pedido.setStatus(status);
        if (status.equals(StatusPedidoEnum.ENTREGUE)){
            pedido.setDataFim(LocalDateTime.now());
        }
        pedidoRepository.save(pedido);
    }

    private Pedido findById(Long id) {
        return pedidoRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(PedidoError.PEDIDO_ERROR.getCodeErro()));
    }

    private static String buildItensList(List<ItemDTO> itens) {
        return itens.stream()
            .map(i -> "\"(" + i.getId() + "," + i.getQuantidade() + ")\"")
            .collect(Collectors.joining(",", "{", "}"));
    }

    private Pagamento buildPagamento(PagamentoApiResponse pagamentoResponse, Pedido pedido) {
        return Pagamento.builder()
            .mpPagId(pagamentoResponse.getId())
            .moeda(pagamentoResponse.getCurrencyId())
            .pedido(pedido)
            .status(pagamentoResponse.getStatus())
            .tipo(pagamentoResponse.getPaymentTypeId())
            .valor(pagamentoResponse.getTransactionAmount())
            .metodo(pagamentoResponse.getPaymentMethodId())
            .dataCriacao(DateUtils.convert(pagamentoResponse.getDateCreated()))
            .dataExpiracao(DateUtils.convert(pagamentoResponse.getDateOfExpiration()))
            .externalReference(pagamentoResponse.getExternalReference())
            .statusDetail(pagamentoResponse.getStatusDetail())
            .build();

    }
}
