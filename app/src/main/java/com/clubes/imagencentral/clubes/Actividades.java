package com.clubes.imagencentral.clubes;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.clubes.imagencentral.clubes.Fragments.ActividadesBuscadorFragment;
import com.clubes.imagencentral.clubes.Fragments.FragmentActividades;
import com.clubes.imagencentral.clubes.Fragments.FragmentHorarios;

/**
 * Created by Lau on 17/02/2015.
 */
public class Actividades extends Fragment {
    public static final String ACTIVIDAD_BUNDLE = "ACTIVIDAD_BUNDLE";
    private static final int REQUEST_CODE = 1001;
    public static final String ACTIVIDADES="Actividades";
    public static final String HORARIOS="Horarios";
    String idclub;
    String fragemntactivo;
    Bundle b;
    public Actividades(){}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflar la vista
        View vista = inflater.inflate(R.layout.actividades, container, false);
        b = getArguments();
        idclub=b.getString("CLUB");
        CargaFragment(b, ACTIVIDADES);
        fragemntactivo=ACTIVIDADES;
        Button btnActividades = (Button) vista.findViewById(R.id.btnActividades);
        btnActividades.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CargaFragment(b,ACTIVIDADES);
            }
        });
        Button btnHorarios = (Button) vista.findViewById(R.id.btnHorarios);
        btnHorarios.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                CargaFragment(b,HORARIOS);
            }
        });
        return vista;
    }
    public void CargaFragment(Bundle args,String cual){
        if(cual.equals(ACTIVIDADES)) {
            fragemntactivo=ACTIVIDADES;
            Fragment fr = new FragmentActividades();
            fr.setArguments(args);
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_place,fr)
                    .commit();
        }else if(cual.equals(HORARIOS)){
            fragemntactivo=HORARIOS;
            Fragment fr = new FragmentHorarios();
            fr.setArguments(args);
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_place,fr)
                    .commit();
        }

    }
    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_activity_actions, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_search:
                openSearch();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void openSearch(){

        ActividadesBuscadorFragment buscador=new ActividadesBuscadorFragment();
        // crear los argumentos
        Bundle argumentos=new Bundle();
        argumentos.putString("CLUB", idclub);
        argumentos.putString("FRAGMENT", fragemntactivo);
        buscador.setArguments(argumentos);
        buscador.show(getFragmentManager(),"Dialog Fragment");
    }
}
