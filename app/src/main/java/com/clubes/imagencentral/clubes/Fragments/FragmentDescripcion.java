package com.clubes.imagencentral.clubes.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.clubes.imagencentral.clubes.GaleriaCompleta;
import com.clubes.imagencentral.clubes.R;
import com.clubes.imagencentral.clubes.adapters.GaleriaArrayAdapter;
import com.clubes.imagencentral.clubes.data.DataActividades;
import com.clubes.imagencentral.clubes.data.DataImagenesGaleria;
import com.clubes.imagencentral.clubes.tools.ImagenReal;
import com.clubes.imagencentral.clubes.tools.YoutubeVideo;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marco on 18/02/2015.
 */
public class FragmentDescripcion extends Fragment {
    DataActividades actividades;
    public JSONObject imagenes;
    String gallery_string;
    ImageLoader imageLoader = ImageLoader.getInstance();
    DisplayImageOptions options;
    JSONArray jsonArrayImagenes;
    String video;
    public List<DataImagenesGaleria> imagenes_data = new ArrayList<DataImagenesGaleria>();
    //    Required no-args constructor
    public FragmentDescripcion() {}

    @Override
    public void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        setRetainInstance(true);
        Bundle b = getArguments();
        if(b!=null){
            gallery_string= getArguments().getString("Gallery");
            video=getArguments().getString("Video");
            try {
                imagenes = new JSONObject(gallery_string);

            } catch (JSONException e) {
                e.printStackTrace();
            }
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

            /*Informacion General*/
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

            /*Galeria*/
            try {
                jsonArrayImagenes = imagenes.getJSONArray("gallery");
                //Solo si exsiten imagenes para la galeria
                if(jsonArrayImagenes.length()>0) {
                    ImagenReal imagenapi = new ImagenReal();
                    String url_img = getString(R.string.url_img);
                    for (int i = 0; i < jsonArrayImagenes.length(); i++) {
                        try {
                            JSONObject object = jsonArrayImagenes.getJSONObject(i);
                            imagenes_data.add(new DataImagenesGaleria(
                                    url_img + imagenapi.cambiaImagen(object.getString("photo"), "mini"),
                                    object.getString("name"),
                                    object.getString("foot")
                            ));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    GridView galeriaInterior=(GridView) view.findViewById(R.id.galeria_actividad_descripcion);
                    GaleriaArrayAdapter adapter = new GaleriaArrayAdapter(
                            getActivity(),
                            R.layout.actividades_listitem,
                            imagenes_data
                    );
                    galeriaInterior.setAdapter(adapter);
                    // para cambiar a la actividad de pantalla completa
                    galeriaInterior.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getActivity(), GaleriaCompleta.class);
                            intent.putExtra("galeriaItems", jsonArrayImagenes.toString());
                            intent.putExtra("posicion", String.valueOf(position));
                            startActivity(intent);
                        }
                    });
                }

            } catch (JSONException e) {
                jsonArrayImagenes=null;
            }
            /*Video*/
            ImageView imagevideo = (ImageView) view.findViewById(R.id.ivVideoActividad);
            if(!video.isEmpty()){
                String urlvideo_thumb=getString(R.string.url_thumbnailvideo)+video+getString(R.string.url_thumbnailvideo0);
                final String idvideo=video;
                imageLoader.displayImage(urlvideo_thumb, imagevideo, options);
                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        YoutubeVideo videoyoutube= new YoutubeVideo();
                        videoyoutube.verVideo(getActivity().getApplicationContext(),idvideo);
                    }
                };
                imagevideo.setOnClickListener(listener);
            }else{
                imagevideo.setVisibility(View.GONE);
            }
        }

        return view;
    }
}
