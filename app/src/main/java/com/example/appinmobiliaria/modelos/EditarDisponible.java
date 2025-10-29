package com.example.appinmobiliaria.modelos;

public class EditarDisponible {
    private int id;
    private boolean disponible;

    public EditarDisponible() {}

    public EditarDisponible(int id, boolean disponible) {
        this.id = id;
        this.disponible = disponible;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}
