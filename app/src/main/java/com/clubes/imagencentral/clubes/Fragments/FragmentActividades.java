package com.clubes.imagencentral.clubes.Fragments;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.clubes.imagencentral.clubes.ActividadDetalleActivity;
import com.clubes.imagencentral.clubes.R;
import com.clubes.imagencentral.clubes.adapters.ActividadesArrayAdapter;
import com.clubes.imagencentral.clubes.data.DataActividades;
import com.clubes.imagencentral.clubes.tools.ImagenReal;
import com.clubes.imagencentral.clubes.tools.Json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lau on 17/02/2015.
 */
public class FragmentActividades extends ListFragment {
    public List<DataActividades> dataactividades = new ArrayList<DataActividades>();
    String url_api;
    String url_img;
    String idclub;
    public static final String ACTIVIDAD_BUNDLE = "ACTIVIDAD_BUNDLE";
    private static final int REQUEST_CODE = 1001;
    public FragmentActividades(){

    }
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        url_api = getString(R.string.url_api);
        Bundle b = getArguments();
        if(b!=null){
            idclub=b.getString("CLUB");
        }
        new CargaActividades().execute(url_api + "listActivities?club="+idclub);
    }
    class CargaActividades extends AsyncTask<String, Void, Object> {
        JSONObject actividadesjson;
        @Override
        protected void onPreExecute(){

        }
        @Override
        protected Object doInBackground(String... urls){

            try
            {
                actividadesjson=(JSONObject) Json.getJson(urls[0], Json.HTTPMethods.HttpGet, null);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return 1;
        }
        @Override
        protected void onPostExecute(Object result){

            try
            {
                ImagenReal imagenapi= new ImagenReal();
                String url_img=getString(R.string.url_img);
                JSONArray array = actividadesjson.getJSONObject("response").getJSONArray("data");
                for(int i = 0; i < array.length(); i++)
                {
                    JSONObject object = array.getJSONObject(i);
                    dataactividades.add(new DataActividades(
                            object.getString("idreal"),
                            object.getString("name"),
                            "",
                            url_img+imagenapi.cambiaImagen(object.getString("photo"),"mini"),
                            ""

                    ));
                }
                ActividadesArrayAdapter adapter= new ActividadesArrayAdapter(
                        getActivity(),
                        R.layout.actividades_listitem,
                        dataactividades
                );
                setListAdapter(adapter);
            }
            catch (JSONException e)
            {
                //String mensaje=Buscador.this.getString(R.string.no_result_pais);
                //AlertUtil.messageAlert(Buscador.this,null,mensaje);
                e.printStackTrace();
            }
        }


    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        DataActividades actividad = dataactividades.get(position);
        Log.d("Item type was", actividad.toString());
        Bundle b = actividad.toBundle();
        b.putString("CLUB",idclub);
        Intent intent = new Intent(getActivity(),ActividadDetalleActivity.class);
        intent.putExtra(ACTIVIDAD_BUNDLE,b);
        intent.putExtra("title",b.getString("nombreactividad"));
        startActivityForResult(intent,REQUEST_CODE);
    };
}
