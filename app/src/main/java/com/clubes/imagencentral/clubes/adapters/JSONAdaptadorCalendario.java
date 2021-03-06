package com.clubes.imagencentral.clubes.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.clubes.imagencentral.clubes.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.clubes.imagencentral.clubes.tools.ImagenReal;

/**
 * Created by julio v on 17/02/2015.
 */
public class JSONAdaptadorCalendario extends BaseAdapter implements ListAdapter {

    final Activity activity;
    public JSONArray jsonArray;
    final String base_url;

    public JSONAdaptadorCalendario(Activity activity, JSONArray jsonArray) {
        assert activity!=null;
        assert jsonArray!=null;

        this.jsonArray=jsonArray;
        this.activity=activity;
        this.base_url=activity.getString(R.string.base_url);
    }


    /***/
    // instanciar UIL
    private ImageLoader imageLoader=ImageLoader.getInstance();
    DisplayImageOptions opciones=new DisplayImageOptions.Builder().build();
    /***/


    /***/
    // definir clase ViewHolder
    static class ViewHolderItem {
        TextView fechaItemCalendario;
        TextView horaItemCalendario;
        TextView tituloItemCalendario;
        TextView descriptionItemCalendario;
        ImageView imagenItemCalendario;
    }
    /***/



    @Override public int getCount() {
        if(null==jsonArray)
            return 0;
        else
            return jsonArray.length();
    }

    @Override public JSONObject getItem(int position) {
        if(null==jsonArray) return null;
        else
            return jsonArray.optJSONObject(position);
    }

    @Override public long getItemId(int position) {
        JSONObject jsonObject=getItem(position);

        return jsonObject.optLong("id");
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {

        // instanciar viewHolder
        ViewHolderItem viewHolder;

        if(convertView==null) {

            // inflar vista
            convertView=activity.getLayoutInflater().inflate(R.layout.item_listado_calendario, null);

            // preparar el viewHolder
            viewHolder=new ViewHolderItem();
            viewHolder.fechaItemCalendario=(TextView) convertView.findViewById(R.id.fecha_item_calendario);
            viewHolder.horaItemCalendario=(TextView) convertView.findViewById(R.id.hora_item_calendario);
            viewHolder.tituloItemCalendario=(TextView) convertView.findViewById(R.id.titulo_item_calendario);
            viewHolder.descriptionItemCalendario=(TextView) convertView.findViewById(R.id.descripcion_item_calendario);
            viewHolder.imagenItemCalendario=(ImageView) convertView.findViewById(R.id.imagen_item_calendario);

            // guardar el viewHolder
            convertView.setTag(viewHolder);

        } else {
            viewHolder=(ViewHolderItem) convertView.getTag();
        }

        // agregar los datos a la vista
        try {

            // atrapar el item
            JSONObject item=getItem(position);

            // cambiar el color de los items que ya no están vigentes
            convertView.setBackgroundColor((item.get("current").equals(true)? Color.WHITE:Color.LTGRAY));

            // poner los datos en el viewHolder
            viewHolder.fechaItemCalendario.setText(item.getString("begin"));
            viewHolder.horaItemCalendario.setText(item.getString("hour"));
            viewHolder.tituloItemCalendario.setText(item.getString("title"));
            viewHolder.descriptionItemCalendario.setText(item.getString("description"));
            String imagenReal=new ImagenReal().cambiaImagen(item.get("photo").toString(), "mini");
            imageLoader.displayImage(base_url + "recursos/img/"+imagenReal, viewHolder.imagenItemCalendario, opciones);

        } catch (JSONException e) {
            //TODO excepcion de json
            e.printStackTrace();
        }

        // devolver la vista
        return convertView;
    }
}