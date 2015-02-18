package com.clubes.imagencentral.clubes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;


public class MainActivity extends ActionBarActivity {


    // TODO poner una funcion que lea el club de la base de datos
    protected int CLUB=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /***/
        // paginador
        ViewPager paginador = (ViewPager) findViewById(R.id.paginador);
        paginador.setAdapter(new
                PaginadorAdaptador(getSupportFragmentManager()));
        // tabs
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(paginador);
        /***/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }


    /***/
    // adaptador de fragmentos para el paginador
    public class PaginadorAdaptador extends FragmentPagerAdapter {

        public PaginadorAdaptador(FragmentManager manager) {
            super(manager);
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch(position) {
                case 0: return "ACTIVIDADES";
                case 1: return "NOTICIAS";
                case 2: return "CALENDARIO";
                case 3: return "QUEJAS";
                default: return "";
            }

        }

        @Override
        public Fragment getItem(int i) {

            switch(i) {

                case 0:{
                    Fragment actividades = new Actividades();
                    // crear los argumentos para el contenido
                    Bundle argumentos = new Bundle();
                    argumentos.putString(contenido.ARG_SECTION_NAME,
                            getPageTitle(i).toString());
                    // pasarle los argumentos al nuevo fragmento
                    actividades.setArguments(argumentos);

                    // devolver el fragmento creado
                    return actividades;
                }

                case 1: case 2: case 3: {

                    // crear el nuevo fragmento
                    Fragment fragmento = new contenido();

                    // crear los argumentos para el contenido
                    Bundle argumentos = new Bundle();
                    argumentos.putString(contenido.ARG_SECTION_NAME,
                            getPageTitle(i).toString());

                    // pasarle los argumentos al nuevo fragmento
                    fragmento.setArguments(argumentos);

                    // devolver el fragmento creado
                    return fragmento;

                }

                default:
                    return null;

            } // termina switch

        }

    }
    /***/
}
