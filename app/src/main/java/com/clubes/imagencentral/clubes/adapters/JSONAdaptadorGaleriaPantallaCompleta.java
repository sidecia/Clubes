package com.clubes.imagencentral.clubes.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.clubes.imagencentral.clubes.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;

import com.clubes.imagencentral.clubes.tools.ImagenReal;

/**
 * Created by julio v on 20/02/2015. ******
 */
public class JSONAdaptadorGaleriaPantallaCompleta extends PagerAdapter {

    private Context context;
    private JSONArray items;
    LayoutInflater inflater;


    /***/
    // instanciar UIL
    private ImageLoader imageLoader=ImageLoader.getInstance();
    DisplayImageOptions opciones=new DisplayImageOptions.Builder().build();
    /***/


    public JSONAdaptadorGaleriaPantallaCompleta(Context context, JSONArray items) {
        this.context=context;
        this.items=items;
    }

    public int getCount() {
        return items.length();
    }

    public Object instantiateItem(ViewGroup container, int position) {

        // atrapar la vista
        ImageView imagenItemGaleriaPantallaCompleta;
        inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView=inflater.inflate(R.layout.item_galeria_pantalla_completa, container, false);
        imagenItemGaleriaPantallaCompleta=(ImageView) itemView.findViewById(R.id.imagen_item_galeria_pantalla_completa);

        // poner la imagen en la vista
        try {

            String imagenReal = new ImagenReal().cambiaImagen(items.getJSONObject(position).getString("photo"), "normal");
            imageLoader.displayImage("http://192.168.0.103/mobile/club/recursos/img/"+imagenReal, imagenItemGaleriaPantallaCompleta, opciones);

            ((ViewPager) container).addView(itemView);

        } catch(JSONException e) {
            // TODO en caso de que falle el json
            e.printStackTrace();
        }

        return itemView;

    }

    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

}
