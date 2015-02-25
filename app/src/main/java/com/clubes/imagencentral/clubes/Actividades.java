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

import com.clubes.imagencentral.clubes.Fragments.FragmentActividades;
import com.clubes.imagencentral.clubes.Fragments.FragmentHorarios;

/**
 * Created by Lau on 17/02/2015.
 */
public class Actividades extends Fragment {
    public static final String ACTIVIDAD_BUNDLE = "ACTIVIDAD_BUNDLE";
    private static final int REQUEST_CODE = 1001;
    String idclub;
    Bundle b;
    public Actividades(){}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
        buscador.show(getFragmentManager(),"Dialog Fragment");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflar la vista
        View vista = inflater.inflate(R.layout.actividades, container, false);
        b = getArguments();
        idclub=b.getString("CLUB");
        CargaFragment(b, "Actividades");
        Button btnActividades = (Button) vista.findViewById(R.id.btnActividades);
        btnActividades.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CargaFragment(b, "Actividades");
            }
        });
        Button btnHorarios = (Button) vista.findViewById(R.id.btnHorarios);
        btnHorarios.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CargaFragment(b, "Horarios");
            }
        });
        return vista;
    }
    public void CargaFragment(Bundle args,String cual){
        if(cual.equals("Actividades")) {
            Fragment fr = new FragmentActividades();
            fr.setArguments(args);
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_place,fr)
                    .commit();
        }else if(cual.equals("Horarios")){
            Fragment fr = new FragmentHorarios();
            fr.setArguments(args);
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_place,fr)
                    .commit();
        }

    }
}
