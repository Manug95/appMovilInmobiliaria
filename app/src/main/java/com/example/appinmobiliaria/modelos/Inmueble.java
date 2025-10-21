package com.example.appinmobiliaria.modelos;

import com.example.appinmobiliaria.util.ResultadoValidacion;
import com.example.appinmobiliaria.util.TipoErrorValidacion;

import java.io.Serializable;
import java.util.Objects;

public class Inmueble implements Serializable {
    private int id;
    private int idPropietario;
    private int idTipoInmueble;
    private int cantidadAmbientes;
    private String uso;
    private String calle;
    private int nroCalle;
    private double precio;
    private double latitud;
    private double longitud;
    private boolean disponible;
    private String foto;
    private Propietario propietario;
    private TipoInmueble tipoInmueble;


    public Inmueble() { }

    public Inmueble(int id, int idPropietario, int idTipoInmueble, int cantidadAmbientes, String uso, String calle, int nroCalle, double precio, double latitud, double longitud, boolean disponible) {
        this.id = id;
        this.idPropietario = idPropietario;
        this.idTipoInmueble = idTipoInmueble;
        this.cantidadAmbientes = cantidadAmbientes;
        this.uso = uso;
        this.calle = calle;
        this.nroCalle = nroCalle;
        this.precio = precio;
        this.latitud = latitud;
        this.longitud = longitud;
        this.disponible = disponible;
    }

    public Inmueble(int idPropietario, int idTipoInmueble, int cantidadAmbientes, String uso, String calle, int nroCalle, double precio, double latitud, double longitud, boolean disponible) {
        this.idPropietario = idPropietario;
        this.idTipoInmueble = idTipoInmueble;
        this.cantidadAmbientes = cantidadAmbientes;
        this.uso = uso;
        this.calle = calle;
        this.nroCalle = nroCalle;
        this.precio = precio;
        this.latitud = latitud;
        this.longitud = longitud;
        this.disponible = disponible;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(int idPropietario) {
        this.idPropietario = idPropietario;
    }

    public int getIdTipoInmueble() {
        return idTipoInmueble;
    }

    public void setIdTipoInmueble(int idTipoInmueble) {
        this.idTipoInmueble = idTipoInmueble;
    }

    public int getCantidadAmbientes() {
        return cantidadAmbientes;
    }

    public void setCantidadAmbientes(int cantidadAmbientes) {
        this.cantidadAmbientes = cantidadAmbientes;
    }

    public String getUso() {
        return uso;
    }

    public void setUso(String uso) {
        this.uso = uso;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getNroCalle() {
        return nroCalle;
    }

    public void setNroCalle(int nroCalle) {
        this.nroCalle = nroCalle;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    public TipoInmueble getTipoInmueble() {
        return tipoInmueble;
    }

    public void setTipoInmueble(TipoInmueble tipoInmueble) {
        this.tipoInmueble = tipoInmueble;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Inmueble inmueble = (Inmueble) o;
        return id == inmueble.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    /*public String getDireccion() {
        return calle + " " + nroCalle;
    }*/

    public static ResultadoValidacion validarPrecio(String precioStr) {
        if (precioStr.isEmpty()) {
            return new ResultadoValidacion("El precio no puede estar vacío", TipoErrorValidacion.CAMPO_VACIO);
        }
        double precio;
        try {
            precio = Double.parseDouble(precioStr);
            if (precio <= 0) {
                return new ResultadoValidacion("El precio debe ser mayor a 0", TipoErrorValidacion.VALOR_NEGATIVO);
            }
        } catch (NumberFormatException e) {
            return new ResultadoValidacion("El precio debe ser un número válido", TipoErrorValidacion.FORMATO_INCORRECTO);
        }
        return new ResultadoValidacion(null, TipoErrorValidacion.SIN_ERROR);
    }

    public static ResultadoValidacion validarUso(String uso) {
        if (uso.isEmpty()) {
            return new ResultadoValidacion("El uso no puede estar vacío", TipoErrorValidacion.CAMPO_VACIO);
        }
        if (uso.length() > 50) {
            return new ResultadoValidacion("El uso no puede tener más de 50 caracteres", TipoErrorValidacion.LONGITUD_INCORRECTA);
        }
        return new ResultadoValidacion(null, TipoErrorValidacion.SIN_ERROR);
    }

    public static ResultadoValidacion validarCalle(String calle) {
        if (calle.isEmpty()) {
            return new ResultadoValidacion("La calle no puede estar vacía", TipoErrorValidacion.CAMPO_VACIO);
        }
        if (calle.length() > 100) {
            return new ResultadoValidacion("La calle no puede tener más de 100 caracteres", TipoErrorValidacion.LONGITUD_INCORRECTA);
        }
        return new ResultadoValidacion(null, TipoErrorValidacion.SIN_ERROR);
    }

    public static ResultadoValidacion validarNroCalle(String nroCalleStr) {
        if (nroCalleStr.isEmpty()) {
            return new ResultadoValidacion("El número de calle no puede estar vacío", TipoErrorValidacion.CAMPO_VACIO);
        }
        int nroCalle;
        try {
            nroCalle = Integer.parseInt(nroCalleStr);
            if (nroCalle <= 0) {
                return new ResultadoValidacion("El número de calle debe ser mayor a 0", TipoErrorValidacion.VALOR_NEGATIVO);
            }
        } catch (NumberFormatException e) {
            return new ResultadoValidacion("El número de calle debe ser un número válido", TipoErrorValidacion.FORMATO_INCORRECTO);
        }
        return new ResultadoValidacion(null, TipoErrorValidacion.SIN_ERROR);
    }

    public static ResultadoValidacion validarLatitud(String latitudStr) {
        /*if (latitudStr.isEmpty()) {
            return new ResultadoValidacion("La latitud no puede estar vacía", TipoErrorValidacion.CAMPO_VACIO);
        }*/
        double latitud;
        try {
            latitud = Double.parseDouble(latitudStr);
            if (latitud < -90 || latitud > 90) {
                return new ResultadoValidacion("La latitud debe estar entre -90 y 90", TipoErrorValidacion.VALOR_NEGATIVO);
            }
        } catch (NumberFormatException e) {
            return new ResultadoValidacion("La latitud debe ser un número válido", TipoErrorValidacion.FORMATO_INCORRECTO);
        }
        return new ResultadoValidacion(null, TipoErrorValidacion.SIN_ERROR);
    }

    public static ResultadoValidacion validarLongitud(String longitudStr) {
        /*if (longitudStr.isEmpty()) {
            return new ResultadoValidacion("La longitud no puede estar vacía", TipoErrorValidacion.CAMPO_VACIO);
        }*/
        double longitud;
        try {
            longitud = Double.parseDouble(longitudStr);
            if (longitud < -180 || longitud > 180) {
                return new ResultadoValidacion("La longitud debe estar entre -180 y 180", TipoErrorValidacion.VALOR_NEGATIVO);
            }
        } catch (NumberFormatException e) {
            return new ResultadoValidacion("La longitud debe ser un número válido", TipoErrorValidacion.FORMATO_INCORRECTO);
        }
        return new ResultadoValidacion(null, TipoErrorValidacion.SIN_ERROR);
    }

    public static ResultadoValidacion validarDisponible(boolean disponible) {
        return new ResultadoValidacion(null, TipoErrorValidacion.SIN_ERROR);
    }

    public static ResultadoValidacion validarCantidadAmbientes(String cantidadAmbientesStr) {
        if (cantidadAmbientesStr.isEmpty()) {
            return new ResultadoValidacion("La cantidad de ambientes no puede estar vacía", TipoErrorValidacion.CAMPO_VACIO);
        }
        int cantidadAmbientes;
        try {
            cantidadAmbientes = Integer.parseInt(cantidadAmbientesStr);
            if (cantidadAmbientes <= 0) {
                return new ResultadoValidacion("La cantidad de ambientes debe ser mayor a 0", TipoErrorValidacion.VALOR_NEGATIVO);
            }
        } catch (NumberFormatException e) {
            return new ResultadoValidacion("La cantidad de ambientes debe ser un número válido", TipoErrorValidacion.FORMATO_INCORRECTO);
        }
        return new ResultadoValidacion(null, TipoErrorValidacion.SIN_ERROR);
    }

    private String direccion;
    private double valor;
    private String imagen;

    public Inmueble(String direccion, double valor, String imagen) {
        this.direccion = direccion;
        this.valor = valor;
        this.imagen = imagen;    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
