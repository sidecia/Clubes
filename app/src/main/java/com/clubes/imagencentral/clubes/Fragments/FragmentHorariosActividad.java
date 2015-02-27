package com.clubes.imagencentral.clubes.Fragments;

import android.app.ListFragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;

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
public class FragmentHorariosActividad  extends ListFragment implements AbsListView.OnScrollListener {
    public List<DataHorarioActividad> datahorarios = new ArrayList<DataHorarioActividad>();
    private ArrayList<String> encabezadosage = new ArrayList<String>();
    private ArrayList<String> encabezadosdia = new ArrayList<String>();
    String idclub;
    String idactividad;
    String queryurl;
    String queryurl_page;
    private boolean isloading=false;
    private boolean mMoreDataAvailable=true;
    private  int currentPage=1;
    private View mFooterView;
    public FragmentHorariosActividad(){

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Context context = getActivity();
        mFooterView = LayoutInflater.from(context).inflate(R.layout.loadfooter, null);
        datahorarios.clear();
        currentPage=1;
        queryurl_page="&page="+currentPage;
        new CargaHorariosActividad().execute(queryurl+queryurl_page);
        getListView().setOnScrollListener(this);
    }
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        String  url_api = getString(R.string.url_api);
        Bundle b = getArguments();
        queryurl=url_api;
        if(b!=null){
            idclub=b.getString("CLUB");
            idactividad=b.getString("idactividad");
            idclub=b.getString("CLUB");
            queryurl+="listActivitiesSchedule?club="+idclub+"&aid="+idactividad;
        }
    }
    class CargaHorariosActividad extends AsyncTask<String, Void, Object> {
        JSONObject horariosjson;
        @Override
        protected void onPreExecute(){
            isloading=true;
        }
        @Override
        protected Object doInBackground(String... urls){
                horariosjson=(JSONObject) Json.getJson(urls[0], Json.HTTPMethods.HttpGet, null);
                return horariosjson;
        }
        @Override
        protected void onPostExecute(Object result){

            try
            {
                getListView().addFooterView(mFooterView);
                isloading = true;
                String url_img=getString(R.string.url_img);
                JSONArray array = horariosjson.getJSONObject("response").getJSONArray("data");
                if (array.length() > 0) {
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        String labeledad = object.getString("label");
                        if (!encabezadosage.contains(labeledad)){
                            encabezadosage.add(labeledad);
                            datahorarios.add(new DataHorarioActividad(
                                    idactividad,
                                    labeledad,
                                    "",
                                    "",
                                    "",
                                    "2"
                            ));
                        }


                        JSONArray items = object.getJSONArray("days");
                        for (int j = 0; j < items.length(); j++) {
                            JSONObject objectitem = items.getJSONObject(j);
                            JSONArray objectitemhour = objectitem.getJSONArray("hours");
                            String labeldia = objectitem.getString("label");
                            if (!encabezadosdia.contains(labeledad+labeldia)) {
                                encabezadosdia.add(labeledad+labeldia);
                                datahorarios.add(new DataHorarioActividad(
                                        idactividad,
                                        labeldia,
                                        "",
                                        "",
                                        "",
                                        "1"
                                ));
                            }
                            for (int l = 0; l < objectitemhour.length(); l++) {
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
                }
                else {
                    getListView().removeFooterView(mFooterView);
                    mMoreDataAvailable = false;
                    if (currentPage == 1) {
                        datahorarios.add(new DataHorarioActividad(
                                getResources().getString(R.string.noitems),
                                "",
                                "",
                                "",
                                "0",
                                ""
                        ));
                    }
                }
                HorarioActividadArrayAdapter adapter= new HorarioActividadArrayAdapter(
                        getActivity(),
                        R.layout.horarioactividad_listitem,
                        datahorarios
                );
                if (currentPage == 1) {
                    setListAdapter(adapter);
                }
                if (currentPage != 1){
                    getListView().removeFooterView(mFooterView);
                    adapter.notifyDataSetChanged();
                }
                currentPage++;
                isloading=false;
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
    public void onScroll(AbsListView listView, int firstVisibleItem,int visibleItemCount, int totalItemCount) {
        boolean lastItem = firstVisibleItem + visibleItemCount == totalItemCount && listView.getChildAt(visibleItemCount -1) != null && listView.getChildAt(visibleItemCount-1).getBottom() <= listView.getHeight();
        if(lastItem)
        {
            if(mMoreDataAvailable && !isloading){
                queryurl_page = "&page=" + currentPage;
                new CargaHorariosActividad().execute(queryurl + queryurl_page);
            }
        }
    }
    @Override
    public void onScrollStateChanged(AbsListView listView, int scrollState) {
    }
}
