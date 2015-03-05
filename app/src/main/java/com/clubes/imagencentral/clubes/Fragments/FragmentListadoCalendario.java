package com.clubes.imagencentral.clubes.Fragments;


import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.clubes.imagencentral.clubes.CalendarioDetalle;
import com.clubes.imagencentral.clubes.NoticiaDetalle;
import com.clubes.imagencentral.clubes.R;
import com.clubes.imagencentral.clubes.adapters.JSONAdaptadorCalendario;
import com.clubes.imagencentral.clubes.adapters.JSONAdaptadorNoticias;
import com.clubes.imagencentral.clubes.tools.Json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentListadoCalendario extends ListFragment implements AbsListView.OnScrollListener {

    // el adaptador para los datos
    JSONAdaptadorCalendario adaptador;

    // inicializar las variables para la busqueda
    protected String club;
    protected String search="";         // busqueda vacia por default
    protected String daysStep="0";       // intervalo de dias para buscar
    protected String rangeMax="";       // los eventos que ocurren en el periodo de tiempo seleccionado (week, month o year)
    protected String page="1";          // traer la pagina 1 por default
    protected int umbral=0;             // controla cuantos items faltan para cargar mas items
    protected boolean traerMas=true;    // dice si hay que traer mas items
    protected int NUMERO_ITEMS=5;       // dice cuantos items deben cargarse por pagina


    public FragmentListadoCalendario() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // atrapar los datos con los que llamar a la API
        club=Integer.toString(getArguments().getInt("club"));

        // traer los datos
        traerEventos(club, search, daysStep, rangeMax, page);

    }


    /** para el scroll listener **/
    @Override
    public void onActivityCreated (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        getListView().setOnScrollListener(this);
    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) { }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        int newPage=Integer.parseInt(page);

        if (scrollState==SCROLL_STATE_IDLE) {
            if (view.getLastVisiblePosition()>=view.getCount()-1-umbral) {

                // traer mas datos en caso necesario
                if(traerMas) {
                    newPage++;
                    page=String.valueOf(newPage);
                    traerMasEventos();
                }

            }
        }

    }

    public void traerMasEventos() {
        traerEventos(club, search, daysStep, rangeMax, page);
    }
    /***/


    /** para el menu de la actionbar **/
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_listado_calendario, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            // para el buscador
            case R.id.boton_buscador_calendario: {

                // mostrar el fragmento con el buscador
                FragmentBuscadorCalendario buscador=new FragmentBuscadorCalendario();
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


    /** para cambiar a la actividad CalendarioDetalle al hacer click **/
    @Override
    public void onListItemClick(ListView list, View view, int position, long id) {
        super.onListItemClick(list, view, position, id);

        JSONAdaptadorCalendario adaptador=(JSONAdaptadorCalendario) list.getAdapter();
        try {

            if (!adaptador.getItem(position).getString("idreal").equals("0")) {

                // ir a la siguiente actividad
                Intent intent=new Intent(getActivity(), CalendarioDetalle.class);
                intent.putExtra("club", String.valueOf(club));
                intent.putExtra("idcalendario", adaptador.getItem(position).getString("idreal"));
                startActivity(intent);

            }

        } catch (JSONException e) {
            // TODO en caso de que falle el json
            e.printStackTrace();
        }
    }
    /***/


    /** funcion que trae los datos de la API **/
    public void traerEventos(String club, String search, String daysStep, String rangeMax, String page) {
        BuscaEventos buscador=new BuscaEventos();
        buscador.execute(club, search, daysStep, rangeMax, page);
    }

    private class BuscaEventos extends AsyncTask<String, Void, JSONArray> {

        // variable que lleva la cuenta de la pagina en que vamos
        String pageIndexer;

        // traer los datos y enviarlos a onPostExecute
        protected JSONArray doInBackground(String... params) {

            pageIndexer=params[4];
            JSONArray items=new JSONArray();

            // armar la url para la peticion
            String url=getActivity().getString(R.string.base_url)+"api/content/listEvents?club="+params[0];

            if(params[1]!=null && !params[1].equals("")) {
                url+="&search="+params[1];
            }
            if(params[2]!=null && !params[2].equals("")) {
                url+="&daysStep="+params[2];
            }
            if(params[3]!=null && !params[3].equals("")) {
                url+="&rangeMax="+params[3];
            }
            url+="&page="+params[4];

            // traer los datos
            JSONObject datos= Json.getJson(url, 2, null);

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
                        item.put("description", "");
                        item.put("photo", "1/sto54e77ec5c5266.jpg");
                        item.put("hour", "");
                        item.put("begin", "");
                        item.put("end", "");
                        items.put(item);
                    }

                    // en caso de que haya habido un error
                } else {
                    // mandar el mensaje de error
                    JSONObject error=new JSONObject();
                    error.put("idreal", "0");
                    error.put("title", getString(R.string.error));
                    error.put("description", datos.getJSONObject("meta").getString("detail"));
                    error.put("photo", "1/sto54e77ec5c5266.jpg");
                    error.put("hour", "");
                    error.put("begin", "");
                    error.put("end", "");
                    items.put(error);
                }

            } catch(JSONException e) {
                // TODO enviar mensaje de falla en la traduccion del JSON
                items=null;
            }

            // devolver los items
            return items;

        }

        protected void onPostExecute(JSONArray items) {

            /**/
            Log.i("items", items.toString());
            /**/

            // checar si se deben buscar mas items despues
            if(items.length()<NUMERO_ITEMS) {
                traerMas=false;
            }

            // si es la primera pagina de la busqueda
            if(pageIndexer.equals("1")) {

                adaptador=new JSONAdaptadorCalendario(getActivity(), items);
                setListAdapter(adaptador);

                // si hay que agregar datos al adaptador
            } else {

                try {
                    // checar que no haya sido un mensaje de error
                    if (!items.getJSONObject(0).getString("idreal").equals("0")) {
                        // agregar los datos al adaptador
                        for (int i = 0; i < items.length(); i++) {
                            adaptador.jsonArray.put(items.getJSONObject(i));
                            adaptador.notifyDataSetChanged();
                        }
                    }
                } catch(JSONException e) {
                    // TODO en caso de que falle el JSON
                }

            }

        }

    }
    /***/


    /** para actualizar la informacion con los parametros recibidos del buscador **/
    public void actualizaListadoCalendario(String cadena, int opcion) {

        // reiniciar las variables de busqueda
        search=cadena;
        switch (opcion) {
            case 0: default:
                daysStep="0";
                break;
            case 1:
                daysStep="7";
                break;
            case 2:
                daysStep="30";
                break;
            case 3:
                daysStep="365";
                break;
        }
        page="1";
        traerMas=true;

        // traer los nuevos datos
        traerEventos(club, search, daysStep, rangeMax, page);

    }
    /***/


}
