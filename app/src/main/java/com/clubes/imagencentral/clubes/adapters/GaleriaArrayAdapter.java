package com.clubes.imagencentral.clubes.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.clubes.imagencentral.clubes.R;
import com.clubes.imagencentral.clubes.data.DataImagenesGaleria;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Lau on 23/02/2015.
 */
public class GaleriaArrayAdapter  extends ArrayAdapter<DataImagenesGaleria>{
    private Context context;
    private List<DataImagenesGaleria> objects;
    private ImageLoader imageLoader=ImageLoader.getInstance();
    DisplayImageOptions options=new DisplayImageOptions.Builder().build();

    public GaleriaArrayAdapter(Context context, int resource, List<DataImagenesGaleria> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DataImagenesGaleria galeria = objects.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_galeria_interior, null);
        ImageView image = (ImageView) view.findViewById(R.id.imagen_item_galeria);
        imageLoader.displayImage(galeria.getFoto(), image, options);
        return view;
    }
}
