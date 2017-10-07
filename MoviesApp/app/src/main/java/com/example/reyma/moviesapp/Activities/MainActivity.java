package com.example.reyma.moviesapp.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.reyma.moviesapp.Fragments.NowPlayingFragment;
import com.example.reyma.moviesapp.Fragments.UpcomingMoviesFragment;
import com.example.reyma.moviesapp.R;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        showFragment(NowPlayingFragment.class);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        // se inicializa la clase que va a mostrar los fragmentos
        Class fragment = null;
        // se gestiona el evento click donde se asigna el fragmento que se va a mostrar
        if (id == R.id.menu_item_drawer_now_playing) {
            fragment = NowPlayingFragment.class;
            showFragment(fragment);
        } else if (id == R.id.menu_item_drawer_upcoming) {
            fragment = UpcomingMoviesFragment.class;
            showFragment(fragment);
        } else if (id == R.id.menu_item_drawer_logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showFragment(Class fragment) {

        Fragment frag = null;

        try {
            frag = (Fragment) fragment.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.flContent, frag).commit();

    }
}


/**
 * para iniciar con la construccion de la ui, se inicio con el diseño predefinido que incluye el FAB, se eliminaron los botones por defecto que incluia el
 * appbar main, se cambio por upcoming y nowplaying, se puede programar el evento de los botones ya que el codigo ya esta añadido dentro de la plantilla,
 * al hacer click sobre alguno de ellos dos se va ejecutar el fragmento asociado y se mostrara dentro del activitymain, se creo item_movie tipo cardview
 * que se usara para llenar el recyclerview, se creo la carpeta models con la clase movie que va a contener los atributos de movie
 */
