package com.webone.quiosq.service;

import com.webone.quiosq.controller.request.ClienteRequest;
import com.webone.quiosq.controller.response.ClienteResponse;
import com.webone.quiosq.entity.Cliente;
import java.util.UUID;

public interface ClienteService {

    void save(ClienteRequest request);

    ClienteResponse findByTelefoneAndQuiosqueId(String telefone, UUID quiosqueId);

    Cliente findByTelefoneAndQuiosqueIdOpt(String telefone, UUID quiosqueId);
}
