package com.clubes.imagencentral.clubes.Fragments;



import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.clubes.imagencentral.clubes.R;


public class FragmentBuscadorCalendario extends DialogFragment {


    // instancia de la interfaz
    interfazBuscadorCalendario interfaz;
    Spinner spinner;


    public FragmentBuscadorCalendario() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // inflar la vista
        View view=inflater.inflate(R.layout.fragment_buscador_calendario, container, false);

        // llenar el spinner con las opciones de busqueda
        spinner=(Spinner) view.findViewById(R.id.spinner_buscador_calendario);
        ArrayAdapter<CharSequence> adaptadorSpinner=ArrayAdapter.createFromResource(this.getActivity(), R.array.opciones_buscador_calendario, android.R.layout.simple_spinner_item);
        adaptadorSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adaptadorSpinner);

        // devolver la vista
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        /** enviar la informacion por medio de la interfaz al hacer click **/
        // atrapar los elementos de la vista
        Button botonBuscadorCalendario=(Button) getView().findViewById(R.id.boton_buscador_calendario);
        final TextView inputBuscadorCalendario=(TextView) getView().findViewById(R.id.input_buscador_calendario);

        botonBuscadorCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cadena=inputBuscadorCalendario.getText().toString();
                int opcion=spinner.getSelectedItemPosition();

                // enviar la cadena por medio de la interfaz
                interfaz.traeParametrosCalendario(cadena, opcion);

                // cerrar el fragmento con el dialog
                getDialog().dismiss();

            }
        });
        /***/

    }


    /** definicion de la interfaz para enviar los datos **/
    public interface interfazBuscadorCalendario {
        public void traeParametrosCalendario(String cadena, int opcion);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            interfaz=(interfazBuscadorCalendario) activity;
        } catch(ClassCastException cce) {
            throw new ClassCastException(activity.toString()+" debe implementar la interfaz interfazBuscador");
        }

    }
    /***/


}
