package com.clubes.imagencentral.clubes.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.clubes.imagencentral.clubes.R;
import com.clubes.imagencentral.clubes.data.DataHorarioActividad;

import java.util.List;

/**
 * Created by Lau on 20/02/2015.
 */
public class HorarioActividadArrayAdapter extends ArrayAdapter<DataHorarioActividad> {

    private Context context;
    private List<DataHorarioActividad> objects;
    public HorarioActividadArrayAdapter(Context context, int resource, List<DataHorarioActividad> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        DataHorarioActividad horario = objects.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if(horario.getTipoheader().equals("2")){
            view = inflater.inflate(R.layout.horarios_listitem_sectionage, null);
            TextView tvEncabezado = (TextView) view.findViewById(R.id.tvEncabezadoHorarioAge);
            tvEncabezado.setText(horario.getLabelactividad());
        }
        else if(horario.getTipoheader().equals("1")){
            view = inflater.inflate(R.layout.horarios_listitem_sectiondia, null);
            TextView tvEncabezado = (TextView) view.findViewById(R.id.tvEncabezadoHorarioDia);
            tvEncabezado.setText(horario.getLabelactividad());
        }else {
            view = inflater.inflate(R.layout.horarioactividad_listitem, null);

            TextView tvInicioActividad = (TextView) view.findViewById(R.id.tvInicioActividad);
            tvInicioActividad.setText(horario.getInicioactividad());

            TextView tvFinActividad = (TextView) view.findViewById(R.id.tvFinActividad);
            tvFinActividad.setText(horario.getFinactividad());

            TextView tvZonaActividad = (TextView) view.findViewById(R.id.tvZonaActividad);
            tvZonaActividad.setText(horario.getZonaactividad());
        }
        return view;

    }

}
