package com.clubes.imagencentral.clubes.Fragments;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.clubes.imagencentral.clubes.R;
import com.clubes.imagencentral.clubes.adapters.JSONAdaptadorNoticias;
import com.clubes.imagencentral.clubes.NoticiaDetalle;

import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.clubes.imagencentral.clubes.tools.Json;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**jrvm**/
public class FragmentListadoNoticias extends ListFragment {

    JSONAdaptadorNoticias adaptador;

    // para indicar que vamos a buscar noticias
    protected String tipoContenido="0";
    protected String club;

    // constructor
    public FragmentListadoNoticias() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // atrapar los datos con los que llamar a la API
        club=Integer.toString(getArguments().getInt("club"));

        // traer los datos
        traerDatos(club, tipoContenido, null);

    }


    /** para el menu de la actionbar **/
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_listado_noticias, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            // para el buscador
            case R.id.boton_buscador_noticias: {

                // mostrar el fragmento con el buscador
                FragmentBuscador buscador=new FragmentBuscador();
                buscador.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
                buscador.show(getActivity().getSupportFragmentManager(), "Buscador");
                return true;
            }
            // caso default
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }
    /***/


    /** para cambiar a la actividad NoticiaDetalle al hacer click **/
    @Override
    public void onListItemClick(ListView list, View view, int position, long id) {
        super.onListItemClick(list, view, position, id);

        JSONAdaptadorNoticias adaptador=(JSONAdaptadorNoticias) list.getAdapter();
        try {

            if(!adaptador.getItem(position).getString("idreal").equals("0")) {

                // ir a la siguiente actividad
                Intent intent=new Intent(getActivity(), NoticiaDetalle.class);
                intent.putExtra("club", String.valueOf(club));
                intent.putExtra("idcontenido", adaptador.getItem(position).getString("idreal"));
                startActivity(intent);

            }

        } catch (JSONException e) {
            // TODO en caso de que falle el json
            e.printStackTrace();
        }

    }
    /***/


    /** funcion que trae los datos de la API **/
    public void traerDatos(String club, String tipoContenido, String cadena) {
        BuscaDatos buscador=new BuscaDatos();
        buscador.execute(club, tipoContenido, cadena);
    }

    private class BuscaDatos extends AsyncTask<String, Void, JSONArray> {

        // traer los datos y enviarlos a onPostExecute
        protected JSONArray doInBackground(String... params) {

            JSONArray items=new JSONArray();

            //armar la url
            String url=getActivity().getString(R.string.base_url) + "api/content/listContent?club=" + params[0] + "&type=" + params[1];
            if(params[2]!=null && !params[2].equals("")) {
                try {
                    url+="&search="+URLEncoder.encode(params[2], HTTP.UTF_8);
                } catch (UnsupportedEncodingException e) {
                    // TODO en caso de que falle la codificacion
                }
            }

            // traer loa datos
            JSONObject datos=Json.getJson(url, 2, null);

            try {

                // atrapar el codigo de la respuesta
                String code=datos.getJSONObject("meta").getString("code");

                // si trajo resultados correctamente
                if(code.equals("200")) {

                    // si hubo resultados
                    if(datos.getJSONObject("response").getJSONArray("data").length()>0) {

                        //extraer los items
                        items=datos.getJSONObject("response").getJSONArray("data");

                    // si NO hubo resultados
                    } else {
                        JSONObject item=new JSONObject();
                        item.put("idreal", "0");
                        item.put("title", getString(R.string.no_hubo_resultados));
                        item.put("intro", "");
                        item.put("photo", "1/sto54e77ec5c5266.jpg");
                        items.put(item);
                    }

                    // en caso de que haya habido un error
                } else {

                    // mandar el mensaje de error
                    JSONObject error=new JSONObject();
                    error.put("idreal", "0");
                    error.put("title", getString(R.string.error));
                    error.put("intro", datos.getJSONObject("meta").getString("detail"));
                    error.put("photo", "1/sto54e77ec5c5266.jpg");
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

            Log.i("items", items.toString());
            adaptador=new JSONAdaptadorNoticias(getActivity(), items);
            setListAdapter(adaptador);

        }

    }
    /***/


    /** para actualizar la informacion con la cadena recibida del buscador **/
    public void actualizaNoticias(String cadena) {
        traerDatos(club, tipoContenido, cadena);
    }
    /***/


}
