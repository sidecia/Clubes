package com.clubes.imagencentral.clubes.data;

import android.os.Bundle;

/**
 * Created by Lau on 17/02/2015.
 */
public class DataActividades {
    //	constants for field references
    public static final String ID_ACTIVIDAD = "idactividad";
    public static final String NOMBRE_ACTIVIDAD = "nombreactividad";
    public static final String DESCRIPCION_ACTIVIDAD = "descripcionactividad";
    public static final String ICONO_ACTIVIDAD="iconoactividad";
    public static final String IMAGEN_ACTIVIDAD="imagenactividad";

    //	private fields
    private String idactividad;
    private String nombreactividad;
    private String descripcionactividad;
    private String iconoactividad;
    private String imagenactividad;

    public String getIdActividad(){
       return idactividad;
    }
    public void setIdActividad(String idactividad){
        this.idactividad=idactividad;
    }
    public String getNombreActividad(){
       return nombreactividad;
    }
    public void setNombreActividad(String nombreActividad){
       this.nombreactividad=nombreActividad;
    }
    public String getDescripcionActividad(){
       return descripcionactividad;
    }
    public void setDescripcionActividad(String descripcionactividad){
       this.descripcionactividad=descripcionactividad;
    }
    public String getImagenActividad(){
        return imagenactividad;
    }
    public void setImagenActividad(String imagenactividad){
        this.imagenactividad=imagenactividad;
    }

    public String getIconoactividad() {
        return iconoactividad;
    }

    public void setIconoactividad(String iconoactividad) {
        this.iconoactividad = iconoactividad;
    }

    //	Used when creating the data object
    public DataActividades(String id,String nombre,String descripcion,String imagen,String icono) {
        this.idactividad=id;
        this.nombreactividad = nombre;
        this.descripcionactividad = descripcion;
        this.imagenactividad=imagen;
        this.iconoactividad=icono;
    }
    //	Create from a bundle
    public DataActividades(Bundle b) {
        if (b != null) {
            this.idactividad = b.getString(ID_ACTIVIDAD);
            this.nombreactividad = b.getString(NOMBRE_ACTIVIDAD);
            this.descripcionactividad = b.getString(DESCRIPCION_ACTIVIDAD);
            this.imagenactividad=b.getString(IMAGEN_ACTIVIDAD);
            this.iconoactividad=b.getString(ICONO_ACTIVIDAD);
        }
    }

    @Override
    public String toString() {
        return "DataActividades{" +
                "idactividad='" + idactividad + '\'' +
                ", nombreactividad='" + nombreactividad + '\'' +
                ", descripcionactividad='" + descripcionactividad + '\'' +
                ", imagenactividad='" + imagenactividad + '\'' +
                '}';
    }

    //	Package data for transfer between activities
    public Bundle toBundle() {
        Bundle b = new Bundle();
        b.putString(ID_ACTIVIDAD, this.idactividad);
        b.putString(NOMBRE_ACTIVIDAD, this.nombreactividad);
        b.putString(DESCRIPCION_ACTIVIDAD, this.descripcionactividad);
        b.putString(IMAGEN_ACTIVIDAD,this.imagenactividad);
        b.putString(ICONO_ACTIVIDAD,this.iconoactividad);
        return b;
    }
}
