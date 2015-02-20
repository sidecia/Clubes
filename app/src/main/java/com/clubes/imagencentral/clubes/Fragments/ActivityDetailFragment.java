package com.clubes.imagencentral.clubes.Fragments;


import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.clubes.imagencentral.clubes.R;
import com.clubes.imagencentral.clubes.data.DataActividades;
import com.clubes.imagencentral.clubes.tools.ImagenReal;
import com.clubes.imagencentral.clubes.tools.Json;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Marco on 18/02/2015.
 */
public class ActivityDetailFragment extends Fragment {
    String url_api;
    String idreal_actividad;
    String idclub;
    Bundle b;
    //public List<DataActividades> dataactividades = new ArrayList<DataActividades>();
    public ActivityDetailFragment(){

    }
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Bundle argumentos = getArguments();
        idreal_actividad=argumentos.getString("idactividad");
        idclub=argumentos.getString("CLUB");
        url_api= getString(R.string.url_api);
        new CargaActividadDetalle().execute(url_api + "getActivity?club="+idclub+"&aid="+idreal_actividad);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflar la vista
        View vista = inflater.inflate(R.layout.actividad_detail_fragment, container, false);
        Button btnDescripcion = (Button) vista.findViewById(R.id.btnDetalleActividadesDescripcion);
        btnDescripcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentDescripcion fr = new FragmentDescripcion();
                fr.setArguments(b);
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_descripcion, fr)
                        .commit();
            }
        });
        Button btnDetalleHorarios = (Button) vista.findViewById(R.id.btnDetalleHorarios);
        btnDetalleHorarios.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FragmentHorariosActividad fr = new FragmentHorariosActividad();
                fr.setArguments(b);
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_descripcion,fr)
                        .commit();
            }
        });
        return vista;
    }
    class CargaActividadDetalle extends AsyncTask<String, Void, Object> {
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
                String url_img=getString(R.string.url_img);
                ImagenReal imagenapi= new ImagenReal();
                JSONObject array = actividadesjson.getJSONObject("response");
                DataActividades dataactividades;
                dataactividades = new DataActividades
                        (       idreal_actividad,
                                array.getString("name"),
                                array.getString("description"),
                                url_img+array.getString("photo"),
                                ""
                        );
                b = dataactividades.toBundle();
                b.putString("CLUB",idclub);
                FragmentDescripcion fr = new FragmentDescripcion();
                fr.setArguments(b);
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_descripcion, fr)
                        .commit();

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
