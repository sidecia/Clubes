package com.clubes.imagencentral.clubes;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.clubes.imagencentral.clubes.adapters.JSONAdaptadorGaleriaInterior;
import com.clubes.imagencentral.clubes.tools.ImagenReal;
import com.clubes.imagencentral.clubes.tools.Json;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class CalendarioDetalle extends ActionBarActivity {


    /***/
    // instanciar UIL
    private ImageLoader imageLoader=ImageLoader.getInstance();
    DisplayImageOptions opciones=new DisplayImageOptions.Builder().build();
    /***/

    // referencia al boton de agendar calendario
    ImageButton agendaCalendarioDetalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // atrapar el intent
        Intent intent=getIntent();

        // preparar la vista
        setContentView(R.layout.activity_calendario_detalle);

        // traer la informacion y agregarla a la vista
        traerDetalle(intent.getStringExtra("club"), intent.getStringExtra("idcalendario"));

    }


    /** funcion que trae los datos de la API **/
    public void traerDetalle(String club, String idcalendario) {
        BuscaDetalle buscador=new BuscaDetalle();
        buscador.execute(club, idcalendario);
    }

    private class BuscaDetalle extends AsyncTask<String, Void, JSONObject> {

        // traer los datos y enviarlos a onPostExecute
        protected JSONObject doInBackground(String... params) {

            JSONObject detalle;

            // armar la url
            String url=getString(R.string.base_url)+"api/content/getEvent?club="+params[0]+"&eid="+params[1]+"";

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

            final Calendar horaEvento=Calendar.getInstance();
            final String tituloEvento;
            final String descripcionEvento;

            SimpleDateFormat formato=new SimpleDateFormat("yyyy-MM-dd HH:mm");

            try {

                // inicializar los datos para agendar el evento
                tituloEvento=detalle.getString("title");
                descripcionEvento=detalle.getString("description");

                // poner la informacion en la vista
                TextView tituloCalendarioDetalle=(TextView) findViewById(R.id.titulo_calendario_detalle);
                tituloCalendarioDetalle.setText(detalle.getString("title"));
                TextView descripcionCalendarioDetalle=(TextView) findViewById(R.id.descripcion_calendario_detalle);
                descripcionCalendarioDetalle.setText(detalle.getString("description"));
                TextView fechaCalendarioDetalle=(TextView) findViewById(R.id.fecha_calendario_detalle);
                if(detalle.has("date")) {
                    horaEvento.setTime(formato.parse(detalle.getString("date")+" "+detalle.getString("hour"))); //fecha para la agenda
                    fechaCalendarioDetalle.setText(detalle.getString("date"));
                } else {
                    horaEvento.setTime(formato.parse(detalle.getString("begin")+" "+detalle.getString("hour"))); //fecha para la agenda
                    String date="del "+detalle.getString("begin")+ " al "+detalle.getString("end");
                    fechaCalendarioDetalle.setText(date);
                }
                TextView horaCalendarioDetalle=(TextView) findViewById(R.id.hora_calendario_detalle);
                horaCalendarioDetalle.setText(detalle.getString("hour"));


                // poner la imagen
                ImageView imagenCalendarioDetalle=(ImageView) findViewById(R.id.imagen_detalle_calendario);
                String imagenReal=new ImagenReal().cambiaImagen(detalle.get("photo").toString(), "normal");
                imageLoader.displayImage(getString(R.string.base_url)+"recursos/img/"+imagenReal, imagenCalendarioDetalle, opciones);

                // poner el titulo a la actividad
                setTitle(detalle.getString("title"));

                // atrapar la galeria
                GridView galeriaCalendarioDetalle=(GridView) findViewById(R.id.galeria_calendario_detalle);

                // si trae galeria
                if(detalle.has("gallery") && !detalle.isNull("gallery")) {

                    // atrapar la galeria como string
                    // para mandarla a la galeria de pantalla completa
                    final String galeriaItems=detalle.getJSONArray("gallery").toString();

                    // poner el adaptador en la vista
                    JSONAdaptadorGaleriaInterior adaptador=new JSONAdaptadorGaleriaInterior(CalendarioDetalle.this, detalle.getJSONArray("gallery"));
                    galeriaCalendarioDetalle.setAdapter(adaptador);

                    // para cambiar a la actividad de pantalla completa
                    galeriaCalendarioDetalle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            Intent intent=new Intent(CalendarioDetalle.this, GaleriaCompleta.class);
                            intent.putExtra("galeriaItems", galeriaItems);
                            intent.putExtra("posicion", String.valueOf(position));
                            startActivity(intent);

                        }
                    });

                    //si NO trae galeria, esconderla
                } else {
                    galeriaCalendarioDetalle.setVisibility(View.GONE);
                }

                // atrapar el boton de video
                final ImageButton videoCalendarioDetalle=(ImageButton) findViewById(R.id.video_calendario_detalle);

                // si trae video
                if(detalle.has("video") && !detalle.isNull("video")) {

                    // ponerle tag al boton de video
                    videoCalendarioDetalle.setTag(detalle.getString("video"));

                    // abrir video al hacer click
                    videoCalendarioDetalle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.youtube_url) + videoCalendarioDetalle.getTag()));
                            intent.putExtra("force_fullscreen", true);
                            startActivity(intent);
                        }
                    });

                    // si NO trae video, esconder el boton
                } else {
                    videoCalendarioDetalle.setVisibility(View.GONE);
                }

                /** listener para el boton de agendar eventos **/
                agendaCalendarioDetalle=(ImageButton) findViewById(R.id.agenda_calendario_detalle);
                agendaCalendarioDetalle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Build.VERSION.SDK_INT>=14) {
                            Intent intent = new Intent(Intent.ACTION_INSERT)
                                    .setData(CalendarContract.Events.CONTENT_URI)
                                    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, horaEvento.getTimeInMillis())
                                    .putExtra(CalendarContract.Events.TITLE, tituloEvento)
                                    .putExtra(CalendarContract.Events.DESCRIPTION, descripcionEvento)
                                    .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(Intent.ACTION_EDIT);
                            intent.setType("vnd.android.cursor.item/event");
                            intent.putExtra("beginTime", horaEvento.getTimeInMillis());
                            intent.putExtra("endTime", horaEvento.getTimeInMillis()+60*60*1000);
                            intent.putExtra("title", tituloEvento);
                            startActivity(intent);
                        }
                    }
                });
                /***/

            } catch (JSONException e) {
                // TODO en caso de que falle el json
                e.printStackTrace();
            } catch (ParseException p) {
                // TODO en caso de que falle el parsing
                p.printStackTrace();
            }

        }

    }
    /***/

}
