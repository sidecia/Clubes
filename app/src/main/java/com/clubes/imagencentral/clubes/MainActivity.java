package com.clubes.imagencentral.clubes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.clubes.imagencentral.clubes.Fragments.FragmentBuscador;
import com.clubes.imagencentral.clubes.Fragments.FragmentListadoNoticias;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


public class MainActivity extends ActionBarActivity implements FragmentBuscador.interfazBuscador  {


    // TODO poner una funcion que lea el club de la base de datos
    protected int CLUB=1;
    PagerSlidingTabStrip tabs;
    ViewPager paginador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /** inicializar UIL **/
        if(!ImageLoader.getInstance().isInited())
        {
            ImageLoaderConfiguration config = new
                    ImageLoaderConfiguration.Builder(getApplicationContext())
                    .threadPoolSize(10)
                    .build();
            ImageLoader.getInstance().init( config );
        }
        /***/
        /** inicializar paginador y tabs **/
        paginador = (ViewPager) findViewById(R.id.paginador);
        paginador.setAdapter(new PaginadorAdaptador(getSupportFragmentManager()));
        // tabs
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(paginador);
        /***/

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

        /**/
        SparseArray<Fragment> registeredFragments=new SparseArray<Fragment>();
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }

        public Fragment getRegisteredFragment(int position) {
            return registeredFragments.get(position);
        }
        /**/
        @Override
        public Fragment getItem(int i) {

            switch(i) {

                case 0:{
                    Fragment actividades = new Actividades();
                    // crear los argumentos para el contenido
                    Bundle argumentos = new Bundle();
                    StringBuilder sb = new StringBuilder();
                    sb.append(CLUB);
                    String idClub = sb.toString();
                    argumentos.putString("CLUB",idClub);
                    argumentos.putString(contenido.ARG_SECTION_NAME,getPageTitle(i).toString());
                    // pasarle los argumentos al nuevo fragmento
                    actividades.setArguments(argumentos);
                    // devolver el fragmento creado
                    return actividades;
                }
                // para el fragmento NOTICIAS
                case 1: {

                    // crear el fragmento
                    FragmentListadoNoticias listadoNoticias=new FragmentListadoNoticias();

                    // crear los argumentos
                    Bundle argumentos=new Bundle();
                    argumentos.putInt("club", CLUB);

                    // para que el fragmento cree su menu
                    listadoNoticias.setHasOptionsMenu(true);

                    // pasar los argumentos al fragmento
                    listadoNoticias.setArguments(argumentos);

                    // devolver el listado de las noticias
                    return listadoNoticias;

                }
                case 2: case 3: {

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
    /** sobreescribir el metodo de interfazBuscador **/
    public void traeCadena(String cadena) {

        PaginadorAdaptador adaptador=(PaginadorAdaptador) paginador.getAdapter();
        FragmentListadoNoticias listadoNoticias=(FragmentListadoNoticias) adaptador.getRegisteredFragment(paginador.getCurrentItem());
        listadoNoticias.actualizaNoticias(cadena);

    }
    /***/

}
