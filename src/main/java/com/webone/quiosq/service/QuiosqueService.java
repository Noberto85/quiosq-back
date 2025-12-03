package com.webone.quiosq.service;


import com.webone.quiosq.controller.request.QuiosqueRequest;
import com.webone.quiosq.controller.response.QuiosqueResponse;
import com.webone.quiosq.entity.Quiosque;
import java.util.List;

public interface QuiosqueService {

    void save(QuiosqueRequest request);

    QuiosqueResponse findById(Long id);

    Quiosque findByIdOpt(Long id);

    void delete(Long id);

    List<QuiosqueResponse> findAll();
}
