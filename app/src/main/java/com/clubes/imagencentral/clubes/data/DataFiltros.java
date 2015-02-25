package com.clubes.imagencentral.clubes.data;

/**
 * Created by Marco on 24/02/2015.
 */
public class DataFiltros {
    //	constants for field references
    public static final String ID = "id";
    public static final String LABEL = "label";

    //	private fields
    private String id;
    private String label;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public DataFiltros(String id, String label) {
        this.id = id;
        this.label = label;
    }

    @Override
    public String toString() {
        return "DataFiltros{" +
                "id='" + id + '\'' +
                ", label='" + label + '\'' +
                '}';
    }
}
