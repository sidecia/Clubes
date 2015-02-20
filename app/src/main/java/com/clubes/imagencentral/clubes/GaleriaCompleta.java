package com.clubes.imagencentral.clubes;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONException;


public class GaleriaCompleta extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // atrapar el intent
        Intent intent=getIntent();
        try {

            JSONArray items=new JSONArray(intent.getStringExtra("galeriaItems"));
            int posicion=Integer.parseInt(intent.getStringExtra("posicion"));

        } catch (JSONException e) {
        // TODO en caso de que falle el json
        }

        setContentView(R.layout.activity_galeria_pantalla_completa);

    }

}
