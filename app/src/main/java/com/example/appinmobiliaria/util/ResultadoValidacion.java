package com.example.appinmobiliaria.util;

public class ResultadoValidacion {
    private TipoErrorValidacion tipoError;
    private String mensajeError;
    private boolean esValido;

    public ResultadoValidacion(String mensajeError, TipoErrorValidacion tipoError) {
        this.mensajeError = mensajeError;
        this.tipoError = tipoError;
        this.esValido = tipoError == TipoErrorValidacion.SIN_ERROR;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public TipoErrorValidacion getTipoError() {
        return tipoError;
    }

    public boolean esValido() {
        return esValido;
    }
}
