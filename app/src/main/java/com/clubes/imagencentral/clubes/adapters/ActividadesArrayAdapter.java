package com.clubes.imagencentral.clubes.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.clubes.imagencentral.clubes.R;
import com.clubes.imagencentral.clubes.data.DataActividades;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.List;

/**
 * Created by Marco on 17/02/2015.
 */
public class ActividadesArrayAdapter extends ArrayAdapter<DataActividades> {
    private Context context;
    private List<DataActividades> objects;
    private ImageLoader imageLoader=ImageLoader.getInstance();
    DisplayImageOptions options=new DisplayImageOptions.Builder().build();
    public ActividadesArrayAdapter(Context context, int resource, List<DataActividades> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DataActividades actividad = objects.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.actividades_listitem, null);
        final ProgressBar spinner = (ProgressBar) view.findViewById(R.id.loading_img);
        ImageView image = (ImageView) view.findViewById(R.id.ivActividad);
        imageLoader.displayImage(actividad.getImagenActividad(),image , options, new SimpleImageLoadingListener() {
            public void onLoadingStarted(String imageUri, View view) {
                spinner.setVisibility(View.VISIBLE);
            }
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                spinner.setVisibility(View.GONE);
            }

            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                spinner.setVisibility(View.GONE);
            }
        });
        TextView tv = (TextView) view.findViewById(R.id.tvNombreActividad);
        tv.setText(actividad.getNombreActividad());
        return view;
    }
}
