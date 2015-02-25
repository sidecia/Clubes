package com.clubes.imagencentral.clubes;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.clubes.imagencentral.clubes.Fragments.ActivityDetailFragment;
import com.clubes.imagencentral.clubes.Fragments.FragmentActividades;


public class ActividadDetalleActivity extends ActionBarActivity {
    String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_detalle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState == null) {
            //Create the fragment, set its args, add it to the detail container
            title=getIntent().getStringExtra("title");
            setTitle(title);
            ActivityDetailFragment fragment = new ActivityDetailFragment();
            Bundle b=getIntent().getBundleExtra(FragmentActividades.ACTIVIDAD_BUNDLE);
            fragment.setArguments(b);
            getFragmentManager().beginTransaction()
                    .add(R.id.detalleActividad, fragment)
                    .commit();
        }
    }

    //  Returns to the list activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_actividad_detalle, menu);
        return true;
    }

}
