package com.example.appinmobiliaria.modelos;

import com.example.appinmobiliaria.util.ResultadoValidacion;
import com.example.appinmobiliaria.util.TipoErrorValidacion;

import java.util.Objects;

public class Inquilino {
    private int id;
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private String email;

    public Inquilino() { }

    public Inquilino(int id, String nombre, String apellido, String dni, String telefono, String email) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
        this.email = email;
    }

    public Inquilino(String nombre, String apellido, String dni, String telefono, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Inquilino that = (Inquilino) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public static ResultadoValidacion validarNombre(String nombre){
        if (nombre == null || nombre.isEmpty())
            return new ResultadoValidacion("El nombre no puede estar vacío", TipoErrorValidacion.CAMPO_VACIO);
        if (nombre.length() > 50)
            return new ResultadoValidacion("El nombre no puede tener más de 50 caracteres", TipoErrorValidacion.LONGITUD_INCORRECTA);
        return new ResultadoValidacion(null, TipoErrorValidacion.SIN_ERROR);
    }

    public static ResultadoValidacion validarApellido(String apellido){
        if (apellido == null || apellido.isEmpty())
            return new ResultadoValidacion("El apellido no puede estar vacío", TipoErrorValidacion.CAMPO_VACIO);
        if (apellido.length() > 50)
            return new ResultadoValidacion("El apellido no puede tener más de 50 caracteres", TipoErrorValidacion.LONGITUD_INCORRECTA);
        return new ResultadoValidacion(null, TipoErrorValidacion.SIN_ERROR);
    }

    public static ResultadoValidacion validarDni(String dni){
        if (dni == null || dni.isEmpty())
            return new ResultadoValidacion("El DNI no puede estar vacío", TipoErrorValidacion.CAMPO_VACIO);
        try {
            Integer.parseInt(dni);
        } catch (NumberFormatException e) {
            return new ResultadoValidacion("El DNI debe ser un número", TipoErrorValidacion.FORMATO_INCORRECTO);
        }
        if (dni.length() < 7 || dni.length() > 8)
            return new ResultadoValidacion("El DNI debe tener 7 u 8 caracteres numéricos", TipoErrorValidacion.LONGITUD_INCORRECTA);
        return new ResultadoValidacion(null, TipoErrorValidacion.SIN_ERROR);
    }

    public static ResultadoValidacion validarTelefono(String telefono){
        if (telefono == null || telefono.isEmpty())
            return new ResultadoValidacion("El teléfono no puede estar vacío", TipoErrorValidacion.CAMPO_VACIO);
        if (telefono.length() > 25)
            return new ResultadoValidacion("El teléfono no puede tener más de 25 caracteres", TipoErrorValidacion.LONGITUD_INCORRECTA);
        return new ResultadoValidacion(null, TipoErrorValidacion.SIN_ERROR);
    }

    public static ResultadoValidacion validarEmail(String email){
        if (email == null || email.isEmpty())
            return new ResultadoValidacion("El email no puede estar vacío", TipoErrorValidacion.CAMPO_VACIO);
        if (email.length() > 100)
            return new ResultadoValidacion("El email no puede tener más de 100 caracteres", TipoErrorValidacion.LONGITUD_INCORRECTA);
        return new ResultadoValidacion(null, TipoErrorValidacion.SIN_ERROR);
    }
}
