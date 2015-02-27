package com.clubes.imagencentral.clubes;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.clubes.imagencentral.clubes.adapters.JSONAdaptadorGaleriaInterior;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import tools.ImagenReal;
import tools.Json;


public class NoticiaDetalle extends ActionBarActivity {


    /***/
    // instanciar UIL
    private ImageLoader imageLoader=ImageLoader.getInstance();
    DisplayImageOptions opciones=new DisplayImageOptions.Builder().build();
    /***/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // atrapar el intent
        Intent intent=getIntent();

        // preparar la vista
        setContentView(R.layout.activity_noticia_detalle);

        // traer la informacion y gregarla a la vista
        traerDetalle(intent.getStringExtra("club"), intent.getStringExtra("idcontenido"));

    }


    /**funcion que trae los datos de la API**/
    public void traerDetalle(String club, String idcontenido) {
        BuscaDetalle buscador=new BuscaDetalle();
        buscador.execute(club, idcontenido);
    }

    // traer los datos y enviarlos a onPostExecute
    private class BuscaDetalle extends AsyncTask<String, Void, JSONObject> {

        protected JSONObject doInBackground(String... params) {

            JSONObject detalle;

            // armar la url
            String url=getString(R.string.base_url)+"api/content/getContent?club="+params[0]+"&cid="+params[1]+"";

            // traer los datos de la API como JSONObject
            Json json=new Json();
            JSONObject datos=json.getJson(url, 2, null);

            try {

                // atrapar el codigo de respuesta
                String code=datos.getJSONObject("meta").getString("code");

                // si trajo los datos correctamente
                if(code.equals("200")) {

                    // extraer el detalle
                    detalle=datos.getJSONObject("response");

                } else {

                    // enviar mensaje de error
                    JSONObject error=new JSONObject();
                    error.put("title", "error "+datos.getJSONObject("meta").getString("code"));
                    error.put("text", datos.getJSONObject("meta").getString("detail"));
                    detalle=error;

                }

            } catch(JSONException e) {
                //TODO en caso de que falle el json
                e.printStackTrace();
                detalle=null;
            }

            // devolver el resultado
            return detalle;

        }

        // enviar los datos a la vista principal
        protected void onPostExecute(JSONObject detalle) {

            try {

                // poner la informacion en la vista
                TextView tituloDetalleNoticia=(TextView) findViewById(R.id.titulo_detalle_noticia);
                tituloDetalleNoticia.setText(detalle.getString("title"));
                TextView textoDetalleNoticia=(TextView) findViewById(R.id.texto_imagen_noticia);
                textoDetalleNoticia.setText(detalle.getString("text"));

                // poner la imagen
                ImageView imagenNoticiaDetalle=(ImageView) findViewById(R.id.imagen_detalle_noticia);
                String imagenReal=new ImagenReal().cambiaImagen(detalle.get("photo").toString(), "normal");
                imageLoader.displayImage(getString(R.string.base_url)+"recursos/img/"+imagenReal, imagenNoticiaDetalle, opciones);

                // poner el titulo a la actividad
                setTitle(detalle.getString("title"));

                // atrapar la galeria
                GridView galeriaNoticiaDetalle=(GridView) findViewById(R.id.galeria_noticia_detalle);

                // si trae galeria
                if(detalle.has("gallery") && !detalle.isNull("gallery")) {

                    // atrapar la galeria como string
                    // para mandarla a la galeria de pantalla completa
                    final String galeriaItems=detalle.getJSONArray("gallery").toString();

                    // poner el adaptador en la vista
                    JSONAdaptadorGaleriaInterior adaptador=new JSONAdaptadorGaleriaInterior(NoticiaDetalle.this, detalle.getJSONArray("gallery"));
                    galeriaNoticiaDetalle.setAdapter(adaptador);

                    // para cambiar a la actividad de pantalla completa
                    galeriaNoticiaDetalle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            Intent intent=new Intent(NoticiaDetalle.this, GaleriaCompleta.class);
                            intent.putExtra("galeriaItems", galeriaItems);
                            intent.putExtra("posicion", String.valueOf(position));
                            startActivity(intent);

                        }
                    });

                //si NO trae galeria, esconderla
                } else {
                    galeriaNoticiaDetalle.setVisibility(View.GONE);
                }

                // atrapar el boton de video
                final ImageButton videoNoticiaDetalle=(ImageButton) findViewById(R.id.video_noticia_detalle);

                // si trae video
                if(detalle.has("video") && !detalle.isNull("video")) {

                    // ponerle tag al boton de video
                    videoNoticiaDetalle.setTag(detalle.getString("video"));

                    // abrir video al hacer click
                    videoNoticiaDetalle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.youtube_url)+videoNoticiaDetalle.getTag()));
                            intent.putExtra("force_fullscreen",true);
                            startActivity(intent);
                        }
                    });

                // si NO trae video, esconder el boton
                } else {
                    videoNoticiaDetalle.setVisibility(View.GONE);
                }

            } catch (JSONException e) {
                // TODO en caso de que falle el json
                e.printStackTrace();
            }

        }

    }
    /***/
}
