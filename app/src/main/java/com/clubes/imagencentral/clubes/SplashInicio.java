package com.clubes.imagencentral.clubes;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class SplashInicio extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_inicio);

        // referencia al listview
        ListView listado = (ListView) findViewById(R.id.splash_inicio_listview);

        /***/
        // TODO aqui debemos traer la informacion de los clubes
        // para insertarlos en el listview inicial
        // mientras tanto creamos un club ficticio con id=1
        JSONArray clubes=new JSONArray();
        JSONObject club=new JSONObject();
        try {
            club.put("id", "1");
            club.put("nombre", "Club de Prueba");
        } catch(JSONException e) {
            e.printStackTrace();
        }
        clubes.put(club);
        /***/

    }

}
