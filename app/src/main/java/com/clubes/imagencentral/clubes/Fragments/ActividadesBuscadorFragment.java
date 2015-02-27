package com.clubes.imagencentral.clubes.Fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.clubes.imagencentral.clubes.tools.GenericDataLoader;
import com.clubes.imagencentral.clubes.tools.JSONManager;
import com.clubes.imagencentral.clubes.R;
import com.clubes.imagencentral.clubes.adapters.FiltrosActividadesAdapter;
import com.clubes.imagencentral.clubes.data.DataFiltros;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lau on 24/02/2015.
 */
public class ActividadesBuscadorFragment extends DialogFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.actividades_buscador_fragment, container,false);
        Bundle b = getArguments();
        final String idclub=b.getString("CLUB");
        final String fragmentactivo=b.getString("FRAGMENT");
        //Hacer Filtro Edades
        final Spinner edad = (Spinner) rootView.findViewById(R.id.spinnerEdad);
        final List<DataFiltros> edades = new ArrayList<DataFiltros>();
        edades.add(new DataFiltros("",getString(R.string.tvEdad)));
        new GenericDataLoader(getResources().getString(R.string.url_api)+ "listAgeRange?club="+idclub, JSONManager.HTTPMethod.Get, null, new GenericDataLoader.OnPostExecuteListener()
        {
            @Override
            public void onPostExecute(JSONObject result, Exception exception)
            {
                try
                {

                    JSONArray array = result.getJSONArray("response");
                    for(int i = 0; i < array.length(); i++)
                    {
                        JSONObject object = array.getJSONObject(i);
                        edades.add(new DataFiltros(object.getString("idreal"),object.getString("label")));
                    }
                }
                catch (JSONException e)
                {
                    //String mensaje=Buscador.this.getString(R.string.no_result_pais);
                    //AlertUtil.messageAlert(Buscador.this,null,mensaje);
                    e.printStackTrace();
                }
            }

            @Override
            public void onPreExecute() {}
        }).execute();
        edad.setAdapter(new FiltrosActividadesAdapter(getActivity(),edades));
        //Hacer Filtro Dias
        final Spinner dia = (Spinner) rootView.findViewById(R.id.spinnerDia);
        List<DataFiltros> dias = new ArrayList<DataFiltros>();
        String[] elements_dia=getResources().getStringArray(R.array.diassemana);
        for( int j = 0; j <= elements_dia.length - 1; j++)
        {
            dias.add(new DataFiltros(""+j,elements_dia[j]));
        }
        dia.setAdapter(new FiltrosActividadesAdapter(getActivity(),dias));

        //Hacer Filtro Horas
        final Spinner hora = (Spinner) rootView.findViewById(R.id.spinnerHora);
        List<DataFiltros> horas = new ArrayList<DataFiltros>();
        horas.add(new DataFiltros("",getString(R.string.tvHora)));
        for(int k = 1; k <= 24; k++)
            if(k>9)
                horas.add(new DataFiltros(k+":00",k+":00"));
            else
                horas.add(new DataFiltros("0" + k + ":00", "0" + k + ":00"));

        hora.setAdapter(new FiltrosActividadesAdapter(getActivity(),horas));
        final Button buttonAceptar = (Button) rootView.findViewById(R.id.btnAceptar);
        final EditText buscar = (EditText) rootView.findViewById(R.id.etPalabraBuscar);
        buscar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    return true;
                }
                return false;
            }
        });

        buttonAceptar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String search=buscar.getText().toString();

                DataFiltros txtedad = (DataFiltros)edad.getSelectedItem();
                String ageRange=txtedad.getId();

                DataFiltros txtdia = (DataFiltros)dia.getSelectedItem();
                String day=txtdia.getId();

                DataFiltros txthora = (DataFiltros)hora.getSelectedItem();
                String hour=txthora.getId();
                Bundle args = new Bundle();
                args.putString("CLUB", idclub);
                args.putString("FRAGMENT", fragmentactivo);
                args.putString("search", search);
                args.putString("ageRange", ageRange);
                args.putString("day", day);
                args.putString("hour", hour);

                if(fragmentactivo.equals("Actividades")) {
                    Fragment fr = new FragmentActividades();
                    fr.setArguments(args);
                    getFragmentManager().beginTransaction()
                            .replace(R.id.fragment_place, fr)
                            .commit();
                }else if(fragmentactivo.equals("Horarios")){
                    Fragment fr = new FragmentHorarios();
                    fr.setArguments(args);
                    getFragmentManager().beginTransaction()
                            .replace(R.id.fragment_place,fr)
                            .commit();
                }
                getDialog().dismiss();
            }
        });
        final Button buttonCancelar = (Button) rootView.findViewById(R.id.btnCancelar);
        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        getDialog().setTitle(getString(R.string.tituloBuscadorActividades));
        return rootView;
    }
}
