package com.webone.quiosq.service;

import com.webone.quiosq.controller.request.ClienteCreateRequest;
import com.webone.quiosq.controller.request.ClienteRequest;
import com.webone.quiosq.controller.response.ClienteResponse;
import com.webone.quiosq.dto.RecoveryJwtTokenDto;
import com.webone.quiosq.entity.Cliente;
import java.util.UUID;

public interface ClienteService {

    void createCliente(ClienteCreateRequest request);


}
