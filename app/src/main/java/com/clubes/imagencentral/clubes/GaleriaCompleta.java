package com.clubes.imagencentral.clubes;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.clubes.imagencentral.clubes.adapters.JSONAdaptadorGaleriaPantallaCompleta;

import org.json.JSONArray;
import org.json.JSONException;


public class GaleriaCompleta extends FragmentActivity {

    JSONArray items;
    int posicion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // atrapar el intent
        Intent intent=getIntent();
        try {

            items=new JSONArray(intent.getStringExtra("galeriaItems"));
            posicion=Integer.parseInt(intent.getStringExtra("posicion"));

        } catch (JSONException e) {
        // TODO en caso de que falle el json
        }

        setContentView(R.layout.activity_galeria_pantalla_completa);

        // encontrar el viewpager
        ViewPager paginador=(ViewPager) findViewById(R.id.galeria_pantalla_completa);
        JSONAdaptadorGaleriaPantallaCompleta adaptador=new JSONAdaptadorGaleriaPantallaCompleta(GaleriaCompleta.this, items);
        paginador.setAdapter(adaptador);
        paginador.setCurrentItem(posicion);

    }

}
