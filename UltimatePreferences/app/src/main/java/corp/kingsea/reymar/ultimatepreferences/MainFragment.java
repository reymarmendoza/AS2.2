package corp.kingsea.reymar.ultimatepreferences;

import android.Manifest;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

/*
 * Cuando se crea la actividad debo validar los valores de las variables para mostrar la app
 * de acuerdo a la ultima configuracion que hizo el usuario
 */

public class MainFragment extends Fragment /*implements GoogleApiClient.OnConnectionFailedListener*/ {
    String logtag = "Vas Bien!";
    SharedPreferences preferences;
    MediaPlayer player;
    //ImageView image;
    //GoogleApiClient apiClient;
    TextView textLatitud;
    TextView textLongitud;

    private static final int REQUEST_CODE = 123;//llave para identificar la peticion del permiso

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        //apiLocationBuild();

        preferences = getActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        textLatitud = (TextView) view.findViewById(R.id.textLatitud);
        textLongitud = (TextView) view.findViewById(R.id.textLongitud);

        validateGPSPermission();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        searchLocation();
        playMusic();
    }

    @Override
    public void onPause() {
        stopMusic();
        super.onPause();
    }

    private void validateGPSPermission(){
        //las versiones superiores a lollipop piden el permiso en tiempo de ejecucion
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissionToUser();
            }
        }else{
            //versiones anteriores a lollipop el permiso se otorga en la instalacion
            preferences.edit().putBoolean(KeyValue.check_key, true).apply();
        }
    }

    //muestra una ventana predefinida del sistema donde pide el permiso al gps
    private void requestPermissionToUser() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode) {
            case REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // OK Do something with..
                    preferences.edit().putBoolean(KeyValue.check_key, true).apply();
                } else {//PERMISSION_DENIED
                    //true if the app has requested this permission previously and the user denied the request
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION)) {
                        //Show permission explanation dialog...
                        DialogFragment dialog = new UserDialogFragment();
                        dialog.setArguments(stringsToShowInWindow());
                        dialog.show(getFragmentManager(), "UserDialogFragmentAdvice");
                    } else {
                        //Never ask again selected, or device policy prohibits the app from having that permission.
                        //So, disable that feature, or fall back to another situation...
                    }
                }
            }
        }
    }

    //añade la data necesaria en el bundle para reusar el dialogfragment cambiando sus strings
    private Bundle stringsToShowInWindow() {
        Bundle bundle = new Bundle();
        ArrayList<Integer> stringList = new ArrayList<>();
        stringList.add(R.string.gps_warn);//title
        stringList.add(R.string.gps_desactivated);//message
        stringList.add(R.string.permission_dialog_fragment_yes);
        stringList.add(R.string.permission_dialog_fragment_no);
        bundle.putIntegerArrayList("dialogWindow", stringList);
        return bundle;
    }

    private void searchLocation(){
        if(preferences.getBoolean(KeyValue.check_key, KeyValue.check_value)){
            //si esta activo busco por medio de gps
        }
    }
/*
    private void apiLocationBuild() {
        apiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(MainFragment.this,
                        this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
    }
*/
    private void updateUI(Location loc) {
        if (loc != null) {
            textLatitud.setText("Latitud: " + String.valueOf(loc.getLatitude()));
            textLongitud.setText("Longitud: " + String.valueOf(loc.getLongitude()));
        } else {
            textLatitud.setText("Latitud: (desconocida)");
            textLongitud.setText("Longitud: (desconocida)");
        }
    }

    //valida si esta activada la opcion de musica y reproduce la cancion seleccionada
    private void playMusic() {
        if(preferences.getBoolean(KeyValue.switch_key, KeyValue.switch_value)){
            player = MediaPlayer.create(getActivity(),
                    preferences.getInt(KeyValue.listResouce_key, KeyValue.listResource_value));
            player.setLooping(true);
            player.start();
        }
    }

    //detiene la musica y libera los recursos de mediaplayer
    private void stopMusic(){
        if(player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }
    /*
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    */
}