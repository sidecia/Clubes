package com.clubes.imagencentral.clubes;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.clubes.imagencentral.clubes.adapters.FiltrosActividadesAdapter;
import com.clubes.imagencentral.clubes.data.DataFiltros;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lau on 24/02/2015.
 */
public class ActividadesBuscadorFragment extends DialogFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.actividades_buscador_fragment, container,false);

        //Hacer Filtro Edades
        final Spinner edad = (Spinner) rootView.findViewById(R.id.spinnerEdad);
        List<DataFiltros> edades = new ArrayList<DataFiltros>();
        edades.add(new DataFiltros("",getString(R.string.tvEdad)));
        for(int i = 0; i < 10; i++)
            edades.add(new DataFiltros(""+i,""+i));
        edad.setAdapter(new FiltrosActividadesAdapter(getActivity(),edades));

        //Hacer Filtro Dias
        final Spinner dia = (Spinner) rootView.findViewById(R.id.spinnerDia);
        List<DataFiltros> dias = new ArrayList<DataFiltros>();
        dias.add(new DataFiltros("",getString(R.string.tvDia)));
        String[] elements_dia=getResources().getStringArray(R.array.diassemana);
        for( int j = 1; j <= elements_dia.length - 1; j++)
        {
            dias.add(new DataFiltros(""+j,elements_dia[j]));
        }
        dia.setAdapter(new FiltrosActividadesAdapter(getActivity(),dias));

        //Hacer Filtro Horas
        final Spinner hora = (Spinner) rootView.findViewById(R.id.spinnerHora);
        List<DataFiltros> horas = new ArrayList<DataFiltros>();
        horas.add(new DataFiltros("",getString(R.string.tvHora)));
        for(int k = 1; k <= 24; k++)
            if(k>9)
                horas.add(new DataFiltros(k+":00",k+":00"));
            else
                horas.add(new DataFiltros("0" + k + ":00", "0" + k + ":00"));

        hora.setAdapter(new FiltrosActividadesAdapter(getActivity(),horas));
        final Button buttonAceptar = (Button) rootView.findViewById(R.id.btnAceptar);
        buttonAceptar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String txtedad = edad.getSelectedItem().toString();
                String txtdia=dia.getSelectedItem().toString();
                String txthora=hora.getSelectedItem().toString();
                Log.e("EDAD",txtedad);
                Log.e("DIA",txtdia);
                Log.e("HORA",txthora);
            }
        });
        final Button buttonCancelar = (Button) rootView.findViewById(R.id.btnCancelar);
        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        getDialog().setTitle(getString(R.string.tituloBuscadorActividades));
        return rootView;
    }
}
