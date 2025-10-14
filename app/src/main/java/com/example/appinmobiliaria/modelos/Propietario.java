package com.example.appinmobiliaria.modelos;

import java.util.Objects;

import com.example.appinmobiliaria.util.ResultadoValidacion;
import com.example.appinmobiliaria.util.TipoErrorValidacion;

public class Propietario extends Login {
    private int id;
    private String nombre;
    private String apellido;
    private String dni;
    private String telefono;

    public Propietario() { }

    public Propietario(int id, String nombre, String apellido, String dni, String telefono, String email) {
        super(email, null);
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
    }

    public Propietario(String nombre, String apellido, String dni, String telefono, String email) {
        super(email, null);
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
    }

    public Propietario(String nombre, String apellido, String dni, String telefono, String email, String clave) {
        super(email, clave);
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
    }

    public Propietario(int id, String nombre, String apellido, String dni, String telefono, String email, String clave) {
        super(email, clave);
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Propietario that = (Propietario) o;
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
}
