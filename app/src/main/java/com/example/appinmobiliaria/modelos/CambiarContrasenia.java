package com.example.appinmobiliaria.modelos;

import com.example.appinmobiliaria.util.ResultadoValidacion;
import com.example.appinmobiliaria.util.TipoErrorValidacion;

import java.io.Serializable;

public class CambiarContrasenia implements Serializable {
    private String claveActual;
    private String claveNueva;

    public CambiarContrasenia(String claveActual, String claveNueva) {
        this.claveActual = claveActual;
        this.claveNueva = claveNueva;
    }

    public String getClaveNueva() {
        return claveNueva;
    }

    public void setClaveNueva(String claveNueva) {
        this.claveNueva = claveNueva;
    }

    public String getClaveActual() {
        return claveActual;
    }

    public void setClaveActual(String claveActual) {
        this.claveActual = claveActual;
    }

    public static ResultadoValidacion validarClaveActual(String claveActual) {
        if (claveActual == null || claveActual.isEmpty())
            return new ResultadoValidacion("La clave actual no puede estar vacía", TipoErrorValidacion.CAMPO_VACIO);

        return new ResultadoValidacion(null, TipoErrorValidacion.SIN_ERROR);
    }

    public static ResultadoValidacion validarClaveNueva(String claveNueva) {
        if (claveNueva == null || claveNueva.isEmpty()) {
            return new ResultadoValidacion("La clave nueva no puede estar vacía", TipoErrorValidacion.CAMPO_VACIO);
        }

        return new ResultadoValidacion(null, TipoErrorValidacion.SIN_ERROR);
    }
}
