package com.clubes.imagencentral.clubes.Fragments;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.clubes.imagencentral.clubes.R;
import com.clubes.imagencentral.clubes.data.DataActividades;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

/**
 * Created by Marco on 18/02/2015.
 */
public class FragmentDescripcion extends Fragment {
    DataActividades actividades;
    ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options;
    //    Required no-args constructor
    public FragmentDescripcion() {}

    @Override
    public void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        Bundle b = getArguments();
        if(b!=null){
            actividades = new DataActividades(b);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Load the layout

        View view = inflater.inflate(R.layout.fragment_descripcion, container, false);

        imageLoader.init(ImageLoaderConfiguration.createDefault(this.getActivity()));
        options = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading()
                .cacheOnDisc()
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        if (actividades != null) {

            //Display values and image
            TextView tvNombre = (TextView) view.findViewById(R.id.tvDNombreActividad);
            tvNombre.setText(actividades.getNombreActividad());

            TextView tvDescripcion = (TextView) view.findViewById(R.id.tvDescripcionActividad);
            tvDescripcion.setText(actividades.getDescripcionActividad());

            final ProgressBar spinner = (ProgressBar) view.findViewById(R.id.loading_img);
            ImageView image = (ImageView) view.findViewById(R.id.ivDImagenActividad);
            imageLoader.displayImage(actividades.getImagenActividad(),image , options, new SimpleImageLoadingListener() {
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

        }

        return view;
    }

}
