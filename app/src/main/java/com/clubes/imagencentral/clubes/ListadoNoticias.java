package com.clubes.imagencentral.clubes;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tools.Json;

/**jrvm**/
public class ListadoNoticias extends ListFragment {

    // para indicar que vamos a buscar noticias
    protected int tipoContenido=0;
    protected int club;

    // constructor
    public ListadoNoticias() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // atrapar los datos con los que llamar a la API
        club=getArguments().getInt("club");

        // traer los datos
        traerDatos(club, tipoContenido);

    }


    /**funcion que trae los datos de internet**/
    public void traerDatos(int club, int tipoContenido) {
        BuscaDatos buscador=new BuscaDatos();
        buscador.execute(club, tipoContenido);
    }

    private class BuscaDatos extends AsyncTask<Integer, Void, JSONArray> {

        // traer los datos y enviarlos a onPostExecute
        protected JSONArray doInBackground(Integer... params) {

            JSONArray items;

            //armar la url
            String url="http://192.168.0.103:80/mobile/club/api/content/listContent?club=" + params[0] + "&type=" + params[1];

            // traer los datos de la API como JSONObject
            Json json=new Json();
            JSONObject datos=json.getJson(url, 2, null);

            try {

                //extraer los items
                 items=datos.getJSONObject("response").getJSONArray("data");

            } catch(JSONException e) {
                // TODO enviar mensaje de falla en la traduccion del JSON
                items=null;
            }

            return items;
        }

        // enviar los datos a la vista principal
        protected void onPostExecute(JSONArray items) {

            // las claves para el adaptador
            String[] from={"title"};
            int[] to={R.id.titulo_item_noticias};

            // crear el adaptador
            //ListAdapter adaptadorListado=new JSONArrayAdapter(getActivity(), items, R.layout.item_listado_noticias, from, to, "idreal");
            //setListAdapter(adaptadorListado);

            JSONAdapter adaptador=new JSONAdapter(getActivity(), items);
            setListAdapter(adaptador);

        }

    }
    /***/


}
