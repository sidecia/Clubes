package com.clubes.imagencentral.clubes.Fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;

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
public class FragmentHorarios extends ListFragment implements AbsListView.OnScrollListener {
    public List<DataHorarios> datahorarios = new ArrayList<DataHorarios>();
    private ArrayList<String> encabezadosdia = new ArrayList<String>();
    String idclub;
    String search;
    String ageRange;
    String day;
    String hour;
    String queryurl;
    String queryurl_page;
    Context context;
    private boolean isloading=false;
    private boolean mMoreDataAvailable=true;
    private  int currentPage=1;
    private View mFooterView;
    public FragmentHorarios(){

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Context context = getActivity();
        mFooterView = LayoutInflater.from(context).inflate(R.layout.loadfooter, null);
        datahorarios.clear();
        currentPage=1;
        queryurl_page="&page="+currentPage;
        new CargaHorarios().execute(queryurl+queryurl_page);
        getListView().setOnScrollListener(this);
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        String  url_api = getString(R.string.url_api);
        Bundle b = getArguments();
        queryurl=url_api;
        if(b!=null){
            idclub=b.getString("CLUB");
            queryurl+="listSchedule?club="+idclub;
            if(b.containsKey("search") && !b.getString("search").isEmpty())
                queryurl+="&search="+b.getString("search");
            if(b.containsKey("ageRange") && !b.getString("ageRange").isEmpty())
                queryurl+="&ageRange="+b.getString("ageRange");
            if(b.containsKey("day") && !b.getString("day").isEmpty())
                queryurl+="&day="+b.getString("day");
            if(b.containsKey("hour") && !b.getString("hour").isEmpty())
                queryurl+="&hour="+b.getString("hour");
        }
    }
    class CargaHorarios extends AsyncTask<String, Void, Object> {
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
                        String label = object.getString("label");
                        JSONArray items = object.getJSONArray("hours");
                        if (!encabezadosdia.contains(label)) {
                            encabezadosdia.add(label);
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
                        }
                        for (int j = 0; j < items.length(); j++) {
                            JSONObject objectitem = items.getJSONObject(j);
                            datahorarios.add(new DataHorarios(
                                    objectitem.getString("aid"),
                                    objectitem.getString("name"),
                                    url_img + objectitem.getString("icon"),
                                    objectitem.getString("age"),
                                    objectitem.getString("zone"),
                                    objectitem.getString("begin"),
                                    objectitem.getString("end"),
                                    false
                            ));
                        }

                    }
                }
                else {
                    getListView().removeFooterView(mFooterView);
                    mMoreDataAvailable = false;
                    if (currentPage == 1) {
                        datahorarios.add(new DataHorarios(
                                getResources().getString(R.string.noitems),
                                "",
                                "",
                                "",
                                "",
                                "",
                                "",
                                false
                        ));
                    }
                }

            }
            catch (JSONException e)
            {
                //String mensaje=Buscador.this.getString(R.string.no_result_pais);
                //AlertUtil.messageAlert(Buscador.this,null,mensaje);
                e.printStackTrace();
            }
            HorariosArrayAdapter adapter= new HorariosArrayAdapter(
                    getActivity(),
                    R.layout.actividades_listitem,
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


    }
    @Override
    public void onScroll(AbsListView listView, int firstVisibleItem,int visibleItemCount, int totalItemCount) {
        boolean lastItem = firstVisibleItem + visibleItemCount == totalItemCount && listView.getChildAt(visibleItemCount -1) != null && listView.getChildAt(visibleItemCount-1).getBottom() <= listView.getHeight();
        if(lastItem)
        {
            if(mMoreDataAvailable && !isloading){
                queryurl_page = "&page=" + currentPage;
                new CargaHorarios().execute(queryurl + queryurl_page);
            }
        }
    }
    @Override
    public void onScrollStateChanged(AbsListView listView, int scrollState) {
    }
}
