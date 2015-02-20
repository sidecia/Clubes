package com.clubes.imagencentral.clubes.data;

import android.os.Bundle;

/**
 * Created by Lau on 18/02/2015.
 */
public class DataHorarios {
    //	constants for field references
    public static final String ID_ACTIVIDAD = "idactividad";
    public static final String NOMBRE_ACTIVIDAD = "nombreactividad";
    public static final String ICONO_ACTIVIDAD="iconoactividad";
    public static final String EDAD_ACTIVIDAD="edadactividad";
    public static final String ZONA_ACTIVIDAD = "zonaactividad";
    public static final String INICIO_ACTIVIDAD="inicioactividad";
    public static final String FIN_ACTIVIDAD="finactividad";
    public static final String ISHEADER="isheader";


    //	private fields
    private String idactividad;
    private String nombreactividad;
    private String iconoactividad;
    private String edadactividad;
    private String zonaactividad;
    private String inicioactividad;
    private String finactividad;
    private Boolean isheader;

    public String getIdactividad() {
        return idactividad;
    }

    public void setIdactividad(String idactividad) {
        this.idactividad = idactividad;
    }

    public String getNombreactividad() {
        return nombreactividad;
    }

    public void setNombreactividad(String nombreactividad) {
        this.nombreactividad = nombreactividad;
    }

    public String getIconoactividad() {
        return iconoactividad;
    }

    public void setIconoactividad(String iconoactividad) {
        this.iconoactividad = iconoactividad;
    }

    public String getEdadactividad() {
        return edadactividad;
    }

    public void setEdadactividad(String edadactividad) {
        this.edadactividad = edadactividad;
    }

    public String getZonaactividad() {
        return zonaactividad;
    }

    public void setZonaactividad(String zonaactividad) {
        this.zonaactividad = zonaactividad;
    }

    public String getInicioactividad() {
        return inicioactividad;
    }

    public void setInicioactividad(String inicioactividad) {
        this.inicioactividad = inicioactividad;
    }

    public String getFinactividad() {
        return finactividad;
    }

    public void setFinactividad(String finactividad) {
        this.finactividad = finactividad;
    }

    public Boolean getIsheader() {
        return isheader;
    }

    public void setIsheader(Boolean isheader) {
        this.isheader = isheader;
    }

    public DataHorarios(String idactividad, String nombreactividad, String iconoactividad, String edadactividad, String zonaactividad, String inicioactividad, String finactividad,Boolean isheader) {
        this.idactividad = idactividad;
        this.nombreactividad = nombreactividad;
        this.iconoactividad = iconoactividad;
        this.edadactividad = edadactividad;
        this.zonaactividad = zonaactividad;
        this.inicioactividad = inicioactividad;
        this.finactividad = finactividad;
        this.isheader=isheader;
    }

    @Override
    public String toString() {
        return "DataHorarios{" +
                "idactividad='" + idactividad + '\'' +
                ", nombreactividad='" + nombreactividad + '\'' +
                ", iconoactividad='" + iconoactividad + '\'' +
                ", edadactividad='" + edadactividad + '\'' +
                ", zonaactividad='" + zonaactividad + '\'' +
                ", inicioactividad='" + inicioactividad + '\'' +
                ", finactividad='" + finactividad + '\'' +
                ", isheader=" + isheader +
                '}';
    }

    //	Create from a bundle
    public DataHorarios(Bundle b) {
        if (b != null) {
            this.idactividad = b.getString(ID_ACTIVIDAD);
            this.nombreactividad = b.getString(NOMBRE_ACTIVIDAD);
            this.iconoactividad = b.getString(ICONO_ACTIVIDAD);
            this.edadactividad=b.getString(EDAD_ACTIVIDAD);
            this.zonaactividad=b.getString(ZONA_ACTIVIDAD);
            this.inicioactividad=b.getString(INICIO_ACTIVIDAD);
            this.finactividad=b.getString(FIN_ACTIVIDAD);
            this.isheader=b.getBoolean(ISHEADER);
        }
    }
    public Bundle toBundle() {
        Bundle b = new Bundle();
        b.putString(ID_ACTIVIDAD, this.idactividad);
        b.putString(NOMBRE_ACTIVIDAD, this.nombreactividad);
        b.putString(ICONO_ACTIVIDAD, this.iconoactividad);
        b.putString(EDAD_ACTIVIDAD,this.edadactividad);
        b.putString(ZONA_ACTIVIDAD,this.zonaactividad);
        b.putString(INICIO_ACTIVIDAD,this.inicioactividad);
        b.putString(FIN_ACTIVIDAD,this.finactividad);
        b.putBoolean(ISHEADER, this.isheader);
        return b;
    }
}
