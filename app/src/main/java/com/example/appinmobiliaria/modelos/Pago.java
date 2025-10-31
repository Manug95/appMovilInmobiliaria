package com.example.appinmobiliaria.modelos;

import java.time.LocalDate;
import java.util.Objects;

public class Pago {
    private int id;
    private LocalDate fecha;
    private double importe;
    private String detalle;
    private String tipo;
    private int IdContrato;
    private Contrato contrato;

    public Pago() {}

    public Pago(int id, LocalDate fecha, double importe, String detalle, String tipo, int IdContrato) {
        this.id = id;
        this.fecha = fecha;
        this.importe = importe;
        this.detalle = detalle;
        this.tipo = tipo;
        this.IdContrato = IdContrato;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getIdContrato() {
        return IdContrato;
    }

    public void setIdContrato(int IdContrato) {
        this.IdContrato = IdContrato;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pago pago = (Pago) o;
        return id == pago.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
