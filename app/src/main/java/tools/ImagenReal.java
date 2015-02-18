package tools;

import android.util.DisplayMetrics;
import android.util.Log;

/**
 * Created by julio v on 18/02/2015.
 * hay que mandarle la imagen como viene de la API y el modo es "mini" o "normal"
 */
public class ImagenReal {

    String normal, mini;

    public String cambiaImagen(String imagen, String modo) {

        DisplayMetrics metrics=new DisplayMetrics();

        int index=imagen.lastIndexOf('/');
        String carpeta=imagen.substring(0,index);
        String nombre=imagen.substring(0, imagen.length()-4).replace(carpeta+"/", "");
        String extension=imagen.substring(imagen.length()-4);

        //Log.i("nombre", nombre);
        //Log.i("extension", extension);
        //Log.i("carpeta", carpeta);

        if(metrics.densityDpi==DisplayMetrics.DENSITY_HIGH || metrics.densityDpi==DisplayMetrics.DENSITY_MEDIUM) {
            normal="_movHNormal";
            mini="_movHMini";
        }
        else {
            normal="_movLNormal";
            mini="_movLMini";
        }

        switch (modo) {
            case "normal":
                //Log.i("imagenreal", carpeta+"/stock/"+nombre+normal+extension);
                return carpeta+"/stock/"+nombre+normal+extension;
            case "mini":
                //Log.i("imagenreal", carpeta+"/stock/"+nombre+mini+extension);
                return carpeta+"/stock/"+nombre+mini+extension;
            default:
                return null;
        }

    }

}
