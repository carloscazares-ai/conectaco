package com.conectaco.conectaco.controller;

import com.conectaco.conectaco.dto.UsuarioDTO;
import com.conectaco.conectaco.enums.TipoUsuario;
import com.conectaco.conectaco.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Crear un usuario
    @PostMapping("/usuarios")
    public UsuarioDTO save(@RequestBody UsuarioDTO usuarioDTO){
        UsuarioDTO newUsuarioDTO = usuarioService.save(usuarioDTO);
        return newUsuarioDTO;
    }

    // Todos los usuarios, o filtrados: /v1/usuarios?nombre=Ana&tipoUsuario=Turista&email=ana@test.com
    @GetMapping("/usuarios")
    public List<UsuarioDTO> findAll(@RequestParam(required = false) String nombre,
                                    @RequestParam(required = false) String email,
                                    @RequestParam(required = false) TipoUsuario tipoUsuario){
        if (nombre == null && email == null && tipoUsuario == null) {
            return usuarioService.findAll();
        }
        return usuarioService.buscar(nombre, email, tipoUsuario);
    }

    // Un usuario por su id: /v1/usuarios/1
    @GetMapping("/usuarios/{idUsuario}")
    public UsuarioDTO findById(@PathVariable Integer idUsuario){
        return usuarioService.findById(idUsuario);
    }

    // Reemplazar todos los datos del usuario
    @PutMapping("/usuarios/{idUsuario}")
    public UsuarioDTO update(@PathVariable Integer idUsuario, @RequestBody UsuarioDTO usuarioDTO){
        return usuarioService.update(idUsuario, usuarioDTO);
    }

    // Cambiar solo algunos datos del usuario
    @PatchMapping("/usuarios/{idUsuario}")
    public UsuarioDTO patch(@PathVariable Integer idUsuario, @RequestBody UsuarioDTO usuarioDTO){
        return usuarioService.patch(idUsuario, usuarioDTO);
    }

    // Borrar un usuario (y su registro de Turistas o Negocios)
    @DeleteMapping("/usuarios/{idUsuario}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer idUsuario){
        usuarioService.delete(idUsuario);
    }
}
