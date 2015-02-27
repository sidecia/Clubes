package com.clubes.imagencentral.clubes.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.clubes.imagencentral.clubes.R;
import com.clubes.imagencentral.clubes.data.DataHorarios;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;
import java.util.TreeSet;

/**
 * Created by Lau on 19/02/2015.
 */
public class HorariosArrayAdapter extends ArrayAdapter<DataHorarios>{
    private Context context;
    private List<DataHorarios> objects;
    private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();
    private ImageLoader imageLoader=ImageLoader.getInstance();
    DisplayImageOptions options=new DisplayImageOptions.Builder().build();

    //ImageLoader imageLoader = ImageLoader.getInstance();
    //DisplayImageOptions options;
    public HorariosArrayAdapter(Context context, int resource, List<DataHorarios> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DataHorarios horario = objects.get(position);
        View view;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if(horario.getIdactividad().equals(context.getResources().getString(R.string.noitems))){
            view = inflater.inflate(R.layout.noresults, null);
        }else {

            if (horario.getIsheader()) {
                view = inflater.inflate(R.layout.horarios_listitem_sectiondia, null);
                TextView tvEncabezado = (TextView) view.findViewById(R.id.tvEncabezadoHorarioDia);
                tvEncabezado.setText(horario.getNombreactividad());
            } else {
                view = inflater.inflate(R.layout.horarios_listitem, null);
                ImageView image = (ImageView) view.findViewById(R.id.ivActividad);
                imageLoader.displayImage(horario.getIconoactividad(), image, options);
                TextView tvNombreActividad = (TextView) view.findViewById(R.id.tvNombreActividad);
                tvNombreActividad.setText(horario.getNombreactividad());

                TextView tvInicioActividad = (TextView) view.findViewById(R.id.tvInicioActividad);
                tvInicioActividad.setText(horario.getInicioactividad());

                TextView tvFinActividad = (TextView) view.findViewById(R.id.tvFinActividad);
                tvFinActividad.setText(horario.getFinactividad());

                TextView tvZonaActividad = (TextView) view.findViewById(R.id.tvZonaActividad);
                tvZonaActividad.setText(horario.getZonaactividad());
            }
        }
        return view;
    }
}
