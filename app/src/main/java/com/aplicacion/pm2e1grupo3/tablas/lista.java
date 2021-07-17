package com.aplicacion.pm2e1grupo3.tablas;

public class lista {

    private Integer ID;
    private String nombre;
    private String latitud;
    private String longitud;
    private byte [] image;

    public lista(){}

    public lista(Integer ID, String nombre, String latitud, String longitud, byte[] image) {
        this.ID = ID;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.image = image;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
