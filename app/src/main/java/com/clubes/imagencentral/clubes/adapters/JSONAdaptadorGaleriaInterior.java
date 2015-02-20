package com.clubes.imagencentral.clubes.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;

import com.clubes.imagencentral.clubes.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.clubes.imagencentral.clubes.tools.ImagenReal;

/**
 * Created by julio v on 19/02/2015.
 */
public class JSONAdaptadorGaleriaInterior extends BaseAdapter implements ListAdapter {

    final Activity activity;
    final JSONArray jsonArray;

    public JSONAdaptadorGaleriaInterior(Activity activity, JSONArray jsonArray) {
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
        ImageView imagenItemGaleria;
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
            convertView=activity.getLayoutInflater().inflate(R.layout.item_galeria_interior, null);

            // preparar el viewHolder
            viewHolder=new ViewHolderItem();
            viewHolder.imagenItemGaleria=(ImageView) convertView.findViewById(R.id.imagen_item_galeria);

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
            String imagenReal=new ImagenReal().cambiaImagen(item.get("photo").toString(), "mini");
            imageLoader.displayImage("http://192.168.0.103/mobile/club/recursos/img/"+imagenReal, viewHolder.imagenItemGaleria, opciones);

        } catch (JSONException e) {
            //TODO excepcion de json
            e.printStackTrace();
        }

        // devolver la vista
        return convertView;
    }

}
