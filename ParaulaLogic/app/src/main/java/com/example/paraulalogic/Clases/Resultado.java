package com.example.paraulalogic.Clases;

public class Resultado {

    private int id;
    private String nombre;
    private int avatar;
    private int puntos;

    public Resultado() {
    }

    public Resultado(int id, String nombre, int avatar, int puntos) {
        this.id = id;
        this.nombre = nombre;
        this.avatar = avatar;
        this.puntos = puntos;
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

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getPuntos() {
        return puntos;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }
}
