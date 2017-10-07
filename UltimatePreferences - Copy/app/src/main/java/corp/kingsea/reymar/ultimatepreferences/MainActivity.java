package corp.kingsea.reymar.ultimatepreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import static android.R.attr.fragment;

/*
 * http://www.sgoliver.net/blog/localizacion-geografica-en-android-1/
 * para implementar los servicios de localizacion se llama el permiso en el manifest y se usa la
 * libreria de Play Services
 * http://desarrollador-android.com/desarrollo/formacion/empezar-formacion/crear-una-interfaz-de-usuario-dinamica-con-fragments/comunicarse-con-otros-fragments/
 * http://www.cristalab.com/tutoriales/material-design-en-versiones-anteriores-a-lollipop-c114485l/
 */

public class MainActivity extends AppCompatActivity/*FragmentActivity*/
        implements GoogleApiClient.OnConnectionFailedListener,
                   GoogleApiClient.ConnectionCallbacks,
                   SendMessage{

    SharedPreferences preferences;
    GoogleApiClient apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.activity_main, new MainFragment(), "mainFragment").commit();
        }
        preferences = this.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        if (preferences.getBoolean(KeyValue.check_key, KeyValue.check_value)){
            //(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            apiLocationBuild();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.preferences_menu:
                getFragmentManager().beginTransaction()
                        .replace(R.id.activity_main, new PreferencesFragment(), "mainFragment")
                        .addToBackStack(null).commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void apiLocationBuild() {
        apiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,
                        this/*onconectionfailedlistener manejador error de conexion en THIS clase*/)
                .addConnectionCallbacks(this)//implementa los metodos conected y conectedfailed para la conexion al API
                .addApi(LocationServices.API)
                .build();
    }
    //GoogleApiClient.OnConnectionFailedListener
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        //Se ha producido un error que no se puede resolver automáticamente
        //y la conexión con los Google Play Services no se ha establecido.

        Log.e("Vas Bien!", "Error grave al conectar con Google Play Services");
    }
    //GoogleApiClient.ConnectionCallbacks method implemented
    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }
    //GoogleApiClient.ConnectionCallbacks method implemented
    @Override
    public void onConnectionSuspended(int i) {

    }
    //SendMessage method implemented
    @Override
    public void textToShow(String msg) {

    }
}

/*adicionar data al fragmento//no sirve porque siempre envia la data, solo la necesito en el else(so far), ubicado en oncreate
    MainFragment fragment = new MainFragment();
    Bundle data = new Bundle();
    data.putString("data","Debe activar GPS en el menu de preferencias");
    fragment.setArguments(data);
    getFragmentManager().beginTransaction().add(R.id.activity_main, fragment).commit();
/*final de añadir data  http://www.htcmania.com/showthread.php?t=1123307  */
