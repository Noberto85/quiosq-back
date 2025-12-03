package com.webone.quiosq.service;


import com.webone.quiosq.controller.request.QuiosqueRequest;
import com.webone.quiosq.controller.response.QuiosqueResponse;
import com.webone.quiosq.entity.Quiosque;
import java.util.List;
import java.util.UUID;

public interface QuiosqueService {

    void save(QuiosqueRequest request);

    QuiosqueResponse findById(UUID id);

    Quiosque findByIdOpt(UUID id);

    void delete(UUID id);

    List<QuiosqueResponse> findAll();
}
