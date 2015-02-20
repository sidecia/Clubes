package com.clubes.imagencentral.clubes;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tools.ImagenReal;

/**
 * Created by julio v on 17/02/2015.
 */
class JSONAdaptadorNoticias extends BaseAdapter implements ListAdapter {

    final Activity activity;
    final JSONArray jsonArray;

    JSONAdaptadorNoticias(Activity activity, JSONArray jsonArray) {
        assert activity!=null;
        assert jsonArray!=null;

        this.jsonArray=jsonArray;
        this.activity=activity;
    }


    /***/
    // instanciar UIL
    private ImageLoader imageLoader=ImageLoader.getInstance();
    DisplayImageOptions opciones=new DisplayImageOptions.Builder().build();
    /***/


    /***/
    // definir clase ViewHolder
    static class ViewHolderItem {
        TextView tituloItemNoticias;
        TextView introItemNoticias;
        ImageView imagenItemNoticias;
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
            convertView=activity.getLayoutInflater().inflate(R.layout.item_listado_noticias, null);

            // preparar el viewHolder
            viewHolder=new ViewHolderItem();
            viewHolder.tituloItemNoticias=(TextView) convertView.findViewById(R.id.titulo_item_noticias);
            viewHolder.introItemNoticias=(TextView) convertView.findViewById(R.id.intro_item_noticias);
            viewHolder.imagenItemNoticias=(ImageView) convertView.findViewById(R.id.imagen_item_noticias);

            // guardar el viewHolder
            convertView.setTag(viewHolder);

        } else {
            viewHolder=(ViewHolderItem) convertView.getTag();
        }

        // agregar los datos a la vista
        try {

            // atrapar el item
            JSONObject item=getItem(position);

            // poner los datos en el viewHolder
            viewHolder.tituloItemNoticias.setText(item.getString("title"));
            viewHolder.introItemNoticias.setText(item.getString("intro"));
            String imagenReal=new ImagenReal().cambiaImagen(item.get("photo").toString(), "mini");
            imageLoader.displayImage("http://192.168.0.103/mobile/club/recursos/img/"+imagenReal, viewHolder.imagenItemNoticias, opciones);

        } catch (JSONException e) {
            //TODO excepcion de json
            e.printStackTrace();
        }

        // devolver la vista
        return convertView;
    }
}