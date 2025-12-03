package com.webone.quiosq.controller;


import com.webone.quiosq.controller.request.QuiosqueRequest;
import com.webone.quiosq.controller.response.QuiosqueResponse;
import com.webone.quiosq.service.QuiosqueService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/quiosque")
@AllArgsConstructor
public class QuiosqueController {

    private final QuiosqueService service;

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody QuiosqueRequest request) {
        service.save(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuiosqueResponse> findById(@PathVariable("id") final Long id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<QuiosqueResponse>> findAllPageable() {
        return new ResponseEntity<>(
            service.findAll(),
            HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") final Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
