package corp.kingsea.reymar.photofeed.main.ui;

import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import corp.kingsea.reymar.photofeed.Manifest;
import corp.kingsea.reymar.photofeed.PhotoFeedApp;
import corp.kingsea.reymar.photofeed.PhotoListFragment;
import corp.kingsea.reymar.photofeed.PhotoMapFragment;
import corp.kingsea.reymar.photofeed.R;
import corp.kingsea.reymar.photofeed.login.ui.LoginActivity;
import corp.kingsea.reymar.photofeed.main.MainPresenter;
import corp.kingsea.reymar.photofeed.main.adapters.MainSectionPagerAdapter;
import corp.kingsea.reymar.photofeed.main.events.MainEvent;

public class MainActivity extends AppCompatActivity implements MainView,
                                                        GoogleApiClient.ConnectionCallbacks,
                                                        GoogleApiClient.OnConnectionFailedListener{

    //esta clase es la parte logica del layout activity_main
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tabs)
    TabLayout tabs;
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    //estos van a ser inyectados
    MainPresenter presenter;
    MainSectionPagerAdapter adapter;
    SharedPreferences sharedPreferences;//es un api
    //para las implementaciones
    private PhotoFeedApp app;
    private Location lastKnownLocation;
    private GoogleApiClient apiClient;//me permite consultarlo para obtener la ubicacion

    //private static final int PERMISSIONS_REQUEST_LOCATION = 1; borre una parte main onconcected video
    private  boolean resolvingError = false;
    private static final int REQEST_RESOLVE_ERROR = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        app = (PhotoFeedApp) getApplication();//fue lo ultimo que se coloco
        setupInjection();
        setupNavigation();//configurar tabs y toolbar
        setupGoogleAPIClient();

        presenter.onCreate();//despues de que todo este listo
    }
//ESTOS SON METODOS DE LAS IPLEMENTACIONES
    private void setupGoogleAPIClient() {
        if(apiClient == null){//si es null necesita inicializacion:
            apiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    private void setupNavigation() {
        //defin el nombre del appbar del layout, con el correo o el nombre del app si el correo es nulo(no deberia)
        String email = sharedPreferences.getString(app.getEmailKey(), getString(R.string.app_name));
        toolbar.setTitle(email);
        setSupportActionBar(toolbar);//defino el actionbar con el titulo

        viewPager.setAdapter(adapter);//viewpager permite hacer el swipe,cuando lo hace le asigno un adapter
        tabs.setupWithViewPager(viewPager);//me paro debajo del appbar y uno el viewpager al layout
    }

    private void setupInjection() {
        String[] titles = new String[]{getString(R.string.main_title_list),getString(R.string.main_title_map)};
        Fragment[] fragments = new Fragment[]{ new PhotoListFragment(), new PhotoMapFragment()};

        adapter = new MainSectionPagerAdapter(getSupportFragmentManager(), titles, fragments);
        sharedPreferences = getSharedPreferences(app.getSharedPreferencesName(), MODE_PRIVATE);
        presenter = new MainPresenter() {
            @Override
            public void onCreate() {

            }

            @Override
            public void onDestroy() {

            }

            @Override
            public void logout() {

            }

            @Override
            public void uploadPhoto(Location location, String path) {

            }

            @Override
            public void onEventMainThread(MainEvent event) {

            }
        };
    }
//HASTA AQUI SON METODOS DE LAS IPLEMENTACIONES
//conexion al api
    @Override
    protected void onStart() {//cuando inicia la actividad padre por eso es override
        super.onStart();
        apiClient.connect();//conecto el apiclient
    }

    @Override
    protected void onStop() {
        super.onStop();
        apiClient.disconnect();
    }
//finaliza metodos de conexion al api
    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
    //cree un menu en xml y lo inflo con esta opcion propia del ide
    //este menu se llama solo una unica vez, si lo quiero cabiar lo debo actualizar con el metodo siguiente PREPARE
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);//simplificado
        return true;//le digo que si lo muestre
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();//si hay pulsacion en un item entonces cuentelo "1"
        //si esa pulsacion es sobre logout:
        if(id == R.id.action_logout){
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        presenter.logout();
        //SharedPreferences.edit().clear().commit();
        Intent intent = new Intent(this, LoginActivity.class);//llamo a loginactivity, porque ya cerre sesion
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                            | Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);//implemento LoginActivity, la ejecuto
    }
//ESTOS SON METODOS DE LAS IPLEMENTACIONES
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if(LocationServices.FusedLocationApi.getLocationAvailability(apiClient).isLocationAvailable()) {//si hay una ubicacion disponible
            lastKnownLocation = LocationServices.FusedLocationApi.getLastLocation(apiClient);//asignamos
            Snackbar.make(viewPager, lastKnownLocation.toString(), Snackbar.LENGTH_SHORT).show();
        }else{
            Snackbar.make(viewPager, R.string.main_error_location_nonavailable, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {//si por alguna razon se suspende la conexion
        apiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if(resolvingError){
            return;
        }else if(connectionResult.hasResolution()){//si hay forma de resolverse el error
            resolvingError = true;
            try {
                connectionResult.startResolutionForResult(this, REQEST_RESOLVE_ERROR);//debo enviar una vista y un coidgo
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        }else{//muestro un mensaje del error
            resolvingError = true;
            GoogleApiAvailability.getInstance().getErrorDialog(this, connectionResult.getErrorCode(), REQEST_RESOLVE_ERROR).show();//this hace referencia a la actividad
        }
    }
//este lo implemento por onConnectionFailed
    @Override//me permite manejar la respuesta del dialogo que muestro onConnectionFailed
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQEST_RESOLVE_ERROR){//no ha sido resuelto
            resolvingError = false;
            if(resultCode == RESULT_OK){//ya fue resuelto
                if(!apiClient.isConnecting() && !apiClient.isConnected()){//pero no esta conectado
                    apiClient.connect();//lo envio a conectar
                }
            }
        }
    }

    @Override
    public void onUploadInit() {

    }

    @Override
    public void onUploadComplete() {

    }

    @Override
    public void onUploadError(String error) {

    }

}
