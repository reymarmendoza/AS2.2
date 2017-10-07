package corp.kingsea.reymar.localizacion;

/*
 * http://www.sgoliver.net/blog/localizacion-geografica-en-android-1/
 * https://github.com/sgolivernet/curso-android-gms-fb/blob/master/android-localizacion-2/app/src/main/java/net/sgoliver/android/localizacion/MainActivity.java
 */

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

public class MainActivity extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener,
                   GoogleApiClient.ConnectionCallbacks,
                   LocationListener,
                   View.OnClickListener{

    TextView latitudeText;
    TextView longitudText;
    ToggleButton buttonLocation;
    GoogleApiClient apiClient;
    LocationRequest locRequest;

    private static final String LOGTAG = "android-localizacion";
    private static final int PETICION_PERMISO_LOCALIZACION = 101;
    private static final int PETICION_CONFIG_UBICACION = 201;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        latitudeText = (TextView) findViewById(R.id.latitud_text);
        longitudText = (TextView) findViewById(R.id.longitud_text);
        buttonLocation = (ToggleButton) findViewById(R.id.location_button);

        apiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)/*segundo this me hace implementar OnConnectionFailedListener*/
                .addConnectionCallbacks(this)/*implementa OnConnectionCallbacks*/
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.location_button:
                toggleLocationUpdates(buttonLocation.isChecked());
        }
    }

    private void toggleLocationUpdates(boolean enable) {
        if (enable) {
            enableLocationUpdates();
        } else {
            disableLocationUpdates();
        }
    }

    private void enableLocationUpdates() {
        //requerimiento de las opciones de ubicacion
        locRequest = new LocationRequest();//almacena las opciones de ubicación
        locRequest.setInterval(2000);//Periodicidad de actualizaciones
        locRequest.setFastestInterval(1000);//Periodicidad máxima de actualizaciones
        locRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);//Precisión

        // Specifies the types of location services the client is interested in using.
        // Settings will be checked for optimal functionality of all requested services
        LocationSettingsRequest locSettingsRequest =
                new LocationSettingsRequest.Builder()
                        .addLocationRequest(locRequest)
                        .build();

        // hace una comparacion entre los requisitos de la app y la configuracion del dispositivo
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(
                        apiClient, locSettingsRequest);
        // obtiene el resultado de la comparacion, hay 3 opciones que puede recibir como respuesta
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult locationSettingsResult) {
                final Status status = locationSettingsResult.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:

                        Log.i(LOGTAG, "Configuración correcta");
                        startLocationUpdates();
                        break;

                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            Log.i(LOGTAG, "Se requiere actuación del usuario");
                            status.startResolutionForResult(MainActivity.this, PETICION_CONFIG_UBICACION);
                        } catch (IntentSender.SendIntentException e) {
                            buttonLocation.setChecked(false);
                            Log.i(LOGTAG, "Error al intentar solucionar configuración de ubicación");
                        }
                        break;

                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i(LOGTAG, "No se puede cumplir la configuración de ubicación necesaria");
                        buttonLocation.setChecked(false);
                        break;
                }
            }
        });
    }

    // recibe el llamado de resolution_required, este metodo es manejado por el api
    // si el gps esta apgado, pide encenderlo, asi para la configuracion necesaria para que se pueda ejecutar
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PETICION_CONFIG_UBICACION) {
            switch (resultCode) {
                // realizo el cambio pedido y ya puedo proseguir con la ejecucion
                case Activity.RESULT_OK:
                    startLocationUpdates();
                    break;
                // no se logro el cambio, se apaga el toggle
                case Activity.RESULT_CANCELED:
                    Log.i(LOGTAG, "El usuario no ha realizado los cambios de configuración necesarios");
                    buttonLocation.setChecked(false);
                    break;
            }
        }
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            //Ojo: estamos suponiendo que ya tenemos concedido el permiso.
            //Sería recomendable implementar la posible petición en caso de no tenerlo.

            Log.i(LOGTAG, "Inicio de recepción de ubicaciones");

            LocationServices.FusedLocationApi.requestLocationUpdates(
                    apiClient, locRequest, this);
        }
    }

    private void disableLocationUpdates() {

        LocationServices.FusedLocationApi.removeLocationUpdates(
                apiClient, this);

    }

    @Override
    public void onLocationChanged(Location location) {

        Log.i(LOGTAG, "Recibida nueva ubicación!");

        //Mostramos la nueva ubicación recibida
        updateUI(location);
    }

    /*metodo OnConnectionFailedListener*/
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(LOGTAG, "Error grave al conectar con Google Play Services");
    }

    /*metodo OnConnectionCallbacks*/
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PETICION_PERMISO_LOCALIZACION);
        } else {

            Location lastLocation =
                    LocationServices.FusedLocationApi.getLastLocation(apiClient);

            updateUI(lastLocation);
        }
    }

    /*metodo OnConnectionCallbacks*/
    @Override
    public void onConnectionSuspended(int i) {
        Log.e(LOGTAG, "Se ha interrumpido la conexión con Google Play Services");
    }

    private void updateUI(Location loc) {
        if (loc != null) {
            latitudeText.setText(String.format(getResources().getString(R.string.latitudToShow),
                    String.valueOf(loc.getLatitude())));
            longitudText.setText(String.format(getResources().getString(R.string.longitudToShow),
                    String.valueOf(loc.getLongitude())));
        } else {
            latitudeText.setText(R.string.latitudUknown);
            longitudText.setText(R.string.longitudUknown);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

}