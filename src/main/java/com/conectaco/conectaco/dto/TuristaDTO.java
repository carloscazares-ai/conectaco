package com.conectaco.conectaco.dto;

public class TuristaDTO {
    private Integer idTurista;
    private String idiomaPreferido;
    private Integer nivel;
    private Integer puntosExperiencia;
    private Integer idUsuario;

    public Integer getIdTurista() {
        return idTurista;
    }

    public void setIdTurista(Integer idTurista) {
        this.idTurista = idTurista;
    }

    public String getIdiomaPreferido() {
        return idiomaPreferido;
    }

    public void setIdiomaPreferido(String idiomaPreferido) {
        this.idiomaPreferido = idiomaPreferido;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public Integer getPuntosExperiencia() {
        return puntosExperiencia;
    }

    public void setPuntosExperiencia(Integer puntosExperiencia) {
        this.puntosExperiencia = puntosExperiencia;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
}
