package com.conectaco.conectaco.exception;

import org.springframework.http.HttpStatus;

// Error con un codigo HTTP y un mensaje para el usuario (ej. 400 "La contrasena debe...")
public class ApiException extends RuntimeException {

    private final HttpStatus status;

    public ApiException(HttpStatus status, String mensaje) {
        super(mensaje);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
