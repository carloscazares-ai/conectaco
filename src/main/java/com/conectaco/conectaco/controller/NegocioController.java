package com.conectaco.conectaco.controller;

import com.conectaco.conectaco.dto.NegocioDTO;
import com.conectaco.conectaco.service.NegocioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// Los negocios se crean con POST /v1/usuarios (tipoUsuario = Negocio). Aqui solo se consultan.
@RestController
@RequestMapping("/v1")
public class NegocioController {

    private final NegocioService negocioService;

    public NegocioController(NegocioService negocioService) {
        this.negocioService = negocioService;
    }

    @GetMapping("/negocios")
    public List<NegocioDTO> findAll(){
        return negocioService.findAll();
    }
}
