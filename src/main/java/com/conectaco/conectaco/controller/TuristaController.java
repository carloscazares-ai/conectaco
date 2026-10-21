package com.conectaco.conectaco.controller;

import com.conectaco.conectaco.dto.TuristaDTO;
import com.conectaco.conectaco.service.TuristaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// Los turistas se crean con POST /v1/usuarios (tipoUsuario = Turista). Aqui solo se consultan.
@RestController
@RequestMapping("/v1")
public class TuristaController {

    private final TuristaService turistaService;

    public TuristaController(TuristaService turistaService) {
        this.turistaService = turistaService;
    }

    @GetMapping("/turistas")
    public List<TuristaDTO> findAll(){
        return turistaService.findAll();
    }
}
