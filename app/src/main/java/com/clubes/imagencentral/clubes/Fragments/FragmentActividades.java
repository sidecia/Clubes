package com.clubes.imagencentral.clubes.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.clubes.imagencentral.clubes.ActividadDetalleActivity;
import com.clubes.imagencentral.clubes.R;
import com.clubes.imagencentral.clubes.adapters.ActividadesArrayAdapter;
import com.clubes.imagencentral.clubes.data.DataActividades;
import com.clubes.imagencentral.clubes.tools.ImagenReal;
import com.clubes.imagencentral.clubes.tools.Json;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lau on 17/02/2015.
 */
public class FragmentActividades extends ListFragment implements AbsListView.OnScrollListener {

    public static final String ACTIVIDAD_BUNDLE = "ACTIVIDAD_BUNDLE";
    private static final int REQUEST_CODE = 1001;
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
    public static final List<DataActividades> dataactividades = new ArrayList<DataActividades>();
    private  int currentPage=1;
    private View mFooterView;
    public FragmentActividades(){

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Context context = getActivity();
        mFooterView = LayoutInflater.from(context).inflate(R.layout.loadfooter, null);
        dataactividades.clear();
        currentPage=1;
        queryurl_page="&page="+currentPage;
        new CargaActividades().execute(queryurl+queryurl_page);
        getListView().setOnScrollListener(this);
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        String  url_api = getString(R.string.url_api);
        Bundle b = getArguments();
        queryurl=url_api;
        if(b!=null){
            idclub=b.getString("CLUB");
            queryurl+="listActivities?club="+idclub;
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
    class CargaActividades extends AsyncTask<String, Void, Object> {

        JSONObject actividadesjson;
        @Override
        protected void onPreExecute(){
            isloading=true;
        }
        @Override
        protected Object doInBackground(String... urls){
                    actividadesjson = (JSONObject) Json.getJson(urls[0], Json.HTTPMethods.HttpGet, null);
            return actividadesjson;
        }
        @Override
        protected void onPostExecute(Object result) {

            try {
                getListView().addFooterView(mFooterView);
                isloading = true;
                ImagenReal imagenapi = new ImagenReal();
                String url_img = getString(R.string.url_img);
                JSONArray array = actividadesjson.getJSONObject("response").getJSONArray("data");
                if (array.length() > 0) {
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        String imagen_actividad = "";
                        if(object.isNull("photo")){
                            imagen_actividad="drawable://" + R.drawable.no_foto2x;
                        }else{
                            imagen_actividad = url_img + imagenapi.cambiaImagen(object.getString("photo"), "normal");
                        }
                        dataactividades.add(new DataActividades(
                                object.getString("idreal"),
                                object.getString("name"),
                                "",
                                imagen_actividad,
                                ""
                        ));
                    }
                }
                else {
                    getListView().removeFooterView(mFooterView);
                    mMoreDataAvailable = false;
                    if (currentPage == 1) {
                        dataactividades.add(new DataActividades(
                                getResources().getString(R.string.noitems),
                                "",
                                "",
                                "",
                                ""
                        ));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            ActividadesArrayAdapter adapter = new ActividadesArrayAdapter(
                    getActivity(),
                    R.layout.actividades_listitem,
                    dataactividades
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
                new CargaActividades().execute(queryurl + queryurl_page);
            }
        }
    }
    @Override
    public void onScrollStateChanged(AbsListView listView, int scrollState) {
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        DataActividades actividad = dataactividades.get(position);
        Bundle b = actividad.toBundle();
        b.putString("CLUB",idclub);
        Intent intent = new Intent(getActivity(),ActividadDetalleActivity.class);
        intent.putExtra(ACTIVIDAD_BUNDLE,b);
        intent.putExtra("title",b.getString("nombreactividad"));
        startActivityForResult(intent,REQUEST_CODE);
    };
}
