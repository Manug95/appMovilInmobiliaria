package com.example.appinmobiliaria.modelos;

public class EditarDisponible {
    //private int id;
    private boolean estado;

    public EditarDisponible() {}

    public EditarDisponible(boolean estado) {
        this.estado = estado;
    }

    /*public EditarDisponible(int id, boolean disponible) {
        this.id = id;
        this.disponible = disponible;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }*/

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
