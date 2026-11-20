package com.wilfredo.courier_logistics_api.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.wilfredo.courier_logistics_api.dto.QuoteRequest;
import com.wilfredo.courier_logistics_api.dto.QuoteResponse;
import com.wilfredo.courier_logistics_api.service.TariffEngineService;


@RestController
@RequestMapping("/api/v1/quotes")
public class QuoteController {
    private final TariffEngineService tariffEngineService;


    public QuoteController(TariffEngineService tariffEngineService) {
        this.tariffEngineService = tariffEngineService;
    }

    @PostMapping
    public ResponseEntity<QuoteResponse> calculateQuote(@Valid @RequestBody QuoteRequest request) {
        QuoteResponse response = tariffEngineService.calculateQuote(request);
        return ResponseEntity.ok(response);
    }
}