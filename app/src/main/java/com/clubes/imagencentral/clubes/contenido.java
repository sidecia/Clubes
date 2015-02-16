package com.clubes.imagencentral.clubes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * jrodolfovillasenor
 */
public class contenido extends Fragment {

    /***/
    // propiedades
    public static final String ARG_SECTION_NAME = "section_name";
    /***/


    public contenido() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // inflar la vista
        View vista = inflater.inflate(R.layout.fragment_contenido, container, false);

        // atrapar los argumentos
        Bundle argumentos = getArguments();

        // poner el texto
        TextView texto = (TextView) vista.findViewById(R.id.nombre_contenido);
        texto.setText(argumentos.getString(ARG_SECTION_NAME));

        return vista;

    }


}
