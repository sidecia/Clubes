package com.clubes.imagencentral.clubes.data;

import android.os.Bundle;

/**
 * Created by Lau on 20/02/2015.
 */
public class DataHorarioActividad {
    //	constants for field references
    public static final String ID_ACTIVIDAD = "idactividad";
    public static final String LABEL_ACTIVIDAD="labelactividad";
    public static final String ZONA_ACTIVIDAD = "zonaactividad";
    public static final String INICIO_ACTIVIDAD="inicioactividad";
    public static final String FIN_ACTIVIDAD="finactividad";
    public static final String TIPO_HEADER="tipoheader";

    //	private fields
    private String idactividad;
    private String labelactividad;
    private String zonaactividad;
    private String inicioactividad;
    private String finactividad;
    private String tipoheader;

    public String getIdactividad() {
        return idactividad;
    }

    public void setIdactividad(String idactividad) {
        this.idactividad = idactividad;
    }

    public String getLabelactividad() {
        return labelactividad;
    }

    public void setLabelactividad(String labelactividad) {
        this.labelactividad = labelactividad;
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

    public String getTipoheader() {
        return tipoheader;
    }

    public void setTipoheader(String tipoheader) {
        this.tipoheader = tipoheader;
    }

    public DataHorarioActividad(String idactividad, String labelactividad, String zonaactividad, String inicioactividad, String finactividad, String tipoheader) {
        this.idactividad = idactividad;
        this.labelactividad = labelactividad;
        this.zonaactividad = zonaactividad;
        this.inicioactividad = inicioactividad;
        this.finactividad = finactividad;
        this.tipoheader = tipoheader;
    }

    @Override
    public String toString() {
        return "DataHorarioActividad{" +
                "idactividad='" + idactividad + '\'' +
                ", labelactividad='" + labelactividad + '\'' +
                ", zonaactividad='" + zonaactividad + '\'' +
                ", inicioactividad='" + inicioactividad + '\'' +
                ", finactividad='" + finactividad + '\'' +
                ", tipoheader=" + tipoheader +
                '}';
    }
    //	Create from a bundle
    public DataHorarioActividad(Bundle b) {
        if (b != null) {
            this.idactividad = b.getString(ID_ACTIVIDAD);
            this.labelactividad=b.getString(LABEL_ACTIVIDAD);
            this.zonaactividad=b.getString(ZONA_ACTIVIDAD);
            this.inicioactividad=b.getString(INICIO_ACTIVIDAD);
            this.finactividad=b.getString(FIN_ACTIVIDAD);
            this.tipoheader=b.getString(tipoheader);
        }
    }
    public Bundle toBundle() {
        Bundle b = new Bundle();
        b.putString(ID_ACTIVIDAD, this.idactividad);
        b.putString(LABEL_ACTIVIDAD,this.labelactividad);
        b.putString(ZONA_ACTIVIDAD,this.zonaactividad);
        b.putString(INICIO_ACTIVIDAD,this.inicioactividad);
        b.putString(FIN_ACTIVIDAD,this.finactividad);
        b.putString(TIPO_HEADER, this.tipoheader);
        return b;
    }
}
