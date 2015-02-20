package com.clubes.imagencentral.clubes.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;

import com.clubes.imagencentral.clubes.R;
import com.clubes.imagencentral.clubes.adapters.HorariosArrayAdapter;
import com.clubes.imagencentral.clubes.data.DataHorarios;
import com.clubes.imagencentral.clubes.tools.Json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lau on 17/02/2015.
 */
public class FragmentHorarios extends ListFragment {
    public List<DataHorarios> datahorarios = new ArrayList<DataHorarios>();
    String url_api;
    String url_img;
    String idclub;
    public FragmentHorarios(){

    }
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        url_api = getString(R.string.url_api);
        Bundle b = getArguments();
        if(b!=null){
            idclub=b.getString("CLUB");
        }
        new CargaHorarios().execute(url_api + "listSchedule?club="+idclub);
    }
    class CargaHorarios extends AsyncTask<String, Void, Object> {
        JSONObject horariosjson;
        @Override
        protected void onPreExecute(){

        }
        @Override
        protected Object doInBackground(String... urls){

            try
            {
                horariosjson=(JSONObject) Json.getJson(urls[0], Json.HTTPMethods.HttpGet, null);
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
                String url_img=getString(R.string.url_img);
                JSONArray array = horariosjson.getJSONObject("response").getJSONArray("data");
                for(int i = 0; i < array.length(); i++)
                {
                    JSONObject object = array.getJSONObject(i);
                    String label=object.getString("label");
                    JSONArray items = object.getJSONArray("hours");
                    datahorarios.add(new DataHorarios(
                           "",
                            label,
                           "",
                            "",
                            "",
                            "",
                            "",
                            true
                    ));
                    for(int j = 0; j < items.length();j++){
                        JSONObject objectitem = items.getJSONObject(j);
                            datahorarios.add(new DataHorarios(
                                    objectitem.getString("aid"),
                                    objectitem.getString("name"),
                                    url_img+objectitem.getString("icon"),
                                    objectitem.getString("age"),
                                    objectitem.getString("zone"),
                                    objectitem.getString("begin"),
                                    objectitem.getString("end"),
                                    false
                            ));
                    }

                }
                HorariosArrayAdapter adapter= new HorariosArrayAdapter(
                        getActivity(),
                        R.layout.actividades_listitem,
                        datahorarios
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
}
