package com.example.appinmobiliaria.modelos;

import com.example.appinmobiliaria.util.ResultadoValidacion;
import com.example.appinmobiliaria.util.TipoErrorValidacion;

public class CambiarContrasenia extends Login {
    private String ClaveNueva;

    public CambiarContrasenia(String email, String clave, String claveNueva) {
        super(email, clave);
        ClaveNueva = claveNueva;
    }

    public String getClaveNueva() {
        return ClaveNueva;
    }

    public void setClaveNueva(String claveNueva) {
        ClaveNueva = claveNueva;
    }

    public static ResultadoValidacion validarClaveNueva(String claveNueva) {
        if (claveNueva == null || claveNueva.isEmpty()) {
            return new ResultadoValidacion("La clave nueva no puede estar vacía", TipoErrorValidacion.CAMPO_VACIO);
        }

        return new ResultadoValidacion(null, TipoErrorValidacion.SIN_ERROR);
    }
}
