package com.example.appinmobiliaria.modelos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Contrato implements Serializable {
    private int id;
    private double montoMensual;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private LocalDate fechaTerminado;
    private int idInmueble;
    private Inmueble inmueble;
    private int idInquilino;
    private Inquilino inquilino;

    public Contrato(int id, double montoMensual, LocalDate fechaInicio, LocalDate fechaFin, LocalDate fechaTerminado, int idInmueble, Inmueble inmueble, int idInquilino, Inquilino inquilino) {
        this.id = id;
        this.montoMensual = montoMensual;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.fechaTerminado = fechaTerminado;
        this.idInmueble = idInmueble;
        this.inmueble = inmueble;
        this.idInquilino = idInquilino;
        this.inquilino = inquilino;
    }

    public Contrato(double montoMensual, LocalDate fechaInicio, LocalDate fechaFin, LocalDate fechaTerminado, int idInmueble, Inmueble inmueble, int idInquilino, Inquilino inquilino) {
        this.montoMensual = montoMensual;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.fechaTerminado = fechaTerminado;
        this.idInmueble = idInmueble;
        this.inmueble = inmueble;
        this.idInquilino = idInquilino;
        this.inquilino = inquilino;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMontoMensual() {
        return montoMensual;
    }

    public void setMontoMensual(double montoMensual) {
        this.montoMensual = montoMensual;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public LocalDate getFechaTerminado() {
        return fechaTerminado;
    }

    public void setFechaTerminado(LocalDate fechaTerminado) {
        this.fechaTerminado = fechaTerminado;
    }

    public int getIdInmueble() {
        return idInmueble;
    }

    public void setIdInmueble(int idInmueble) {
        this.idInmueble = idInmueble;
    }

    public Inmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
    }

    public int getIdInquilino() {
        return idInquilino;
    }

    public void setIdInquilino(int idInquilino) {
        this.idInquilino = idInquilino;
    }

    public Inquilino getInquilino() {
        return inquilino;
    }

    public void setInquilino(Inquilino inquilino) {
        this.inquilino = inquilino;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Contrato contrato = (Contrato) o;
        return id == contrato.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
