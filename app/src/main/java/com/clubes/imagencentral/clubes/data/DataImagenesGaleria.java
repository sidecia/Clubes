package com.clubes.imagencentral.clubes.data;

import android.os.Bundle;

/**
 * Created by Lau on 23/02/2015.
 */
public class DataImagenesGaleria {
    //	constants for field references
    public static final String FOTO = "foto";
    public static final String NOMBRE = "nombre";
    public static final String PIE="pie";

    //	private fields
    private String foto;
    private String nombre;
    private String pie;

    public DataImagenesGaleria(String foto, String nombre, String pie) {
        this.foto = foto;
        this.nombre = nombre;
        this.pie = pie;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPie() {
        return pie;
    }

    public void setPie(String pie) {
        this.pie = pie;
    }

    @Override
    public String toString() {
        return "DataImagenesGaleria{" +
                "foto='" + foto + '\'' +
                ", nombre='" + nombre + '\'' +
                ", pie='" + pie + '\'' +
                '}';
    }
    //	Create from a bundle
    public DataImagenesGaleria(Bundle b) {
        if (b != null) {
            this.foto = b.getString(FOTO);
            this.nombre = b.getString(NOMBRE);
            this.pie = b.getString(PIE);
        }
    }
    //	Package data for transfer between activities
    public Bundle toBundle() {
        Bundle b = new Bundle();
        b.putString(FOTO, this.foto);
        b.putString(NOMBRE, this.nombre);
        b.putString(PIE, this.pie);
        return b;
    }
}
