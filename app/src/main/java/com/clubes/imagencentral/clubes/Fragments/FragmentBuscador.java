package com.clubes.imagencentral.clubes.Fragments;


import android.app.Activity;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.clubes.imagencentral.clubes.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentBuscador extends DialogFragment {


    // instancia de la interfaz
    interfazBuscador interfaz;


    public FragmentBuscador() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_buscador, container, false);

        // devolver la vista
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {

        /**enviar la informacion por medio de la interfaz al hacer click**/
        // atrapar la informacion de la vista
        Button botonBuscador=(Button) getView().findViewById(R.id.boton_buscador);
        final TextView inputBuscador=(TextView) getView().findViewById(R.id.input_buscador);

        botonBuscador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cadena=inputBuscador.getText().toString();

                // enviar la cadena por medio de la interfaz
                interfaz.traeCadena(cadena);

                // cerrar el fragmento con el dialog
                getDialog().dismiss();

            }
        });
        /***/

    }


    /**definicion de la interfaz para enviar los datos**/
    public interface interfazBuscador {
        public void traeCadena(String cadena);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            interfaz=(interfazBuscador) activity;
        } catch(ClassCastException cce) {
            throw new ClassCastException(activity.toString()+" debe implementar la interfaz interfazBuscador");
        }

    }
    /***/


}
