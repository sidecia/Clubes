package com.clubes.imagencentral.clubes.Fragments;

import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.clubes.imagencentral.clubes.R;
import com.clubes.imagencentral.clubes.adapters.HorarioActividadArrayAdapter;
import com.clubes.imagencentral.clubes.data.DataHorarioActividad;
import com.clubes.imagencentral.clubes.tools.Json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lau on 19/02/2015.
 */
public class FragmentHorariosActividad  extends ListFragment {
    public List<DataHorarioActividad> datahorarios = new ArrayList<DataHorarioActividad>();
    String url_api;
    String url_img;
    String idclub;
    String idactividad;
    public FragmentHorariosActividad(){

    }
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        url_api = getString(R.string.url_api);
        Bundle b = getArguments();
        for (String key : b.keySet())
        {
            Log.d("Bundle Debug", key + " = \"" + b.get(key) + "\"");
        }
        if(b!=null){
            idclub=b.getString("CLUB");
            idactividad=b.getString("idactividad");
        }
        new CargaHorariosActividad().execute(url_api + "listActivitiesSchedule?club="+idclub+"&aid="+idactividad);
    }
    class CargaHorariosActividad extends AsyncTask<String, Void, Object> {
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
                    String labeledad=object.getString("label");
                    Log.v("LABELEDAD",labeledad);
                    datahorarios.add(new DataHorarioActividad(
                            idactividad,
                            labeledad,
                            "",
                            "",
                            "",
                            "2"
                    ));
                    JSONArray items = object.getJSONArray("days");
                    for(int j = 0; j < items.length();j++){
                        JSONObject objectitem = items.getJSONObject(j);
                        JSONArray objectitemhour = objectitem.getJSONArray("hours");
                        String labeldia=objectitem.getString("label");
                        Log.v("LABELDIA",labeldia);
                        datahorarios.add(new DataHorarioActividad(
                                idactividad,
                                labeldia,
                                "",
                                "",
                                "",
                                "1"
                        ));
                        for(int l = 0; l < objectitemhour.length();l++){
                            JSONObject data = objectitemhour.getJSONObject(l);
                            datahorarios.add(new DataHorarioActividad(
                                    idactividad,
                                    "",
                                    data.getString("zone"),
                                    data.getString("begin"),
                                    data.getString("end"),
                                    "0"
                            ));
                        }


                    }

                }
                HorarioActividadArrayAdapter adapter= new HorarioActividadArrayAdapter(
                        getActivity(),
                        R.layout.horarioactividad_listitem,
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
