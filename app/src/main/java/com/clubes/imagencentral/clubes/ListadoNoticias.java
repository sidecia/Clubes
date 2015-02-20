package com.clubes.imagencentral.clubes;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

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


    /**para cambiar a la actividad NoticiaDetalle al hacer click**/
    @Override
    public void onListItemClick(ListView list, View view, int position, long id) {
        super.onListItemClick(list, view, position, id);

        JSONAdaptadorNoticias adaptador=(JSONAdaptadorNoticias) list.getAdapter();
        try {

            // ir a la siguiente actividad
            Intent intent=new Intent(getActivity(), NoticiaDetalle.class);
            intent.putExtra("club", String.valueOf(club));
            intent.putExtra("idcontenido", adaptador.getItem(position).getString("idreal"));
            startActivity(intent);

        } catch (JSONException e) {
            // TODO en caso de que falle el json
            e.printStackTrace();
        }

    }
    /***/


    /**funcion que trae los datos de la API**/
    public void traerDatos(int club, int tipoContenido) {
        BuscaDatos buscador=new BuscaDatos();
        buscador.execute(club, tipoContenido);
    }

    private class BuscaDatos extends AsyncTask<Integer, Void, JSONArray> {

        // traer los datos y enviarlos a onPostExecute
        protected JSONArray doInBackground(Integer... params) {

            JSONArray items=new JSONArray();

            //armar la url
            String url="http://192.168.0.103:80/mobile/club/api/content/listContent?club=" + params[0] + "&type=" + params[1];

            // traer los datos de la API como JSONObject
            Json json=new Json();
            JSONObject datos=json.getJson(url, 2, null);

            try {

                // atrapar el codigo de la respuesta
                String code=datos.getJSONObject("meta").getString("code");

                // si trajo resultados correctamente
                if(code.equals("200")) {

                    //extraer los items
                    items=datos.getJSONObject("response").getJSONArray("data");

                // en caso de que haya habido un error
                } else {

                    // mandar el mensaje de error
                    JSONObject error=new JSONObject();
                    error.put("idreal", "0");
                    error.put("title", "error");
                    error.put("intro", datos.getJSONObject("meta").getString("detail"));
                    items.put(error);

                }

            } catch(JSONException e) {
                // TODO enviar mensaje de falla en la traduccion del JSON
                items=null;
            }

            return items;
        }

        // enviar los datos a la vista principal
        protected void onPostExecute(JSONArray items) {

            JSONAdaptadorNoticias adaptador=new JSONAdaptadorNoticias(getActivity(), items);
            setListAdapter(adaptador);

        }

    }
    /***/


}
