package com.example.appinmobiliaria.modelos;

import com.example.appinmobiliaria.util.ResultadoValidacion;
import com.example.appinmobiliaria.util.TipoErrorValidacion;

import java.io.Serializable;

public class Login implements Serializable {
    private String email;
    private String clave;

    public Login() {}

    public Login(String email, String clave) {
        this.email = email;
        this.clave = clave;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public static ResultadoValidacion validarEmail(String email){
        if (email == null || email.isEmpty())
            return new ResultadoValidacion("El email no puede estar vacío", TipoErrorValidacion.CAMPO_VACIO);
        if (email.length() > 100)
            return new ResultadoValidacion("El email no puede tener más de 100 caracteres", TipoErrorValidacion.LONGITUD_INCORRECTA);
        return new ResultadoValidacion(null, TipoErrorValidacion.SIN_ERROR);
    }

    public static ResultadoValidacion validarClave(String clave) {
        if (clave == null || clave.isEmpty())
            return new ResultadoValidacion("La clave no puede estar vacía", TipoErrorValidacion.CAMPO_VACIO);
        /*if (clave.length() < 8)
            return new ResultadoValidacion("La clave debe tener al menos 8 caracteres", TipoErrorValidacion.LONGITUD_INCORRECTA);*/
        return new ResultadoValidacion(null, TipoErrorValidacion.SIN_ERROR);
    }
}
