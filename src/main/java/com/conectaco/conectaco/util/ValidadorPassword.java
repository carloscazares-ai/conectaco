package com.conectaco.conectaco.util;

import java.util.ArrayList;
import java.util.List;

// Reglas para que la contrasena que elige el usuario sea segura.
public final class ValidadorPassword {

    public static final int LONGITUD_MINIMA = 8;
    public static final int LONGITUD_MAXIMA = 45;

    private ValidadorPassword() {
    }

    // Regresa la lista de reglas que NO se cumplen (vacia = contrasena valida)
    public static List<String> reglasIncumplidas(String password) {
        List<String> faltantes = new ArrayList<>();
        if (password == null || password.isEmpty()) {
            faltantes.add("escribir una contrasena");
            return faltantes;
        }
        if (password.length() < LONGITUD_MINIMA) {
            faltantes.add("tener al menos " + LONGITUD_MINIMA + " caracteres");
        }
        if (password.length() > LONGITUD_MAXIMA) {
            faltantes.add("tener maximo " + LONGITUD_MAXIMA + " caracteres");
        }
        if (password.chars().noneMatch(Character::isUpperCase)) {
            faltantes.add("tener al menos una mayuscula");
        }
        if (password.chars().noneMatch(Character::isLowerCase)) {
            faltantes.add("tener al menos una minuscula");
        }
        if (password.chars().noneMatch(Character::isDigit)) {
            faltantes.add("tener al menos un numero");
        }
        if (password.chars().allMatch(Character::isLetterOrDigit)) {
            faltantes.add("tener al menos un simbolo (por ejemplo ! # $ % & * ?)");
        }
        if (password.chars().anyMatch(Character::isWhitespace)) {
            faltantes.add("no tener espacios");
        }
        return faltantes;
    }
}
