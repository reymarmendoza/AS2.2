package corp.kingsea.reymar.ultimatepreferences;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

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
    /*SendMessage sendMessage;*/
    TextView textLatitud;
    TextView textLongitud;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        //apiLocationBuild();

        //image = (ImageView) view.findViewById(R.id.imageView); elimine la imagen para asignar los recursos a la ubicacion
        preferences = getActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
        textLatitud = (TextView) view.findViewById(R.id.textLatitud);
        textLongitud = (TextView) view.findViewById(R.id.textLongitud);
        return view;
    }
    /*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            sendMessage = (SendMessage)context;
        } catch(ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement OnFragmentSendText");
        }
    }
    */
    @Override
    public void onResume() {
        super.onResume();
        //hacerImagenVisible();
        validateGPSState();
        searchLocation();
        playMusic();
    }
    @Override
    public void onPause() {
        stopMusic();
        super.onPause();
    }
    private void validateGPSState(){
        if (!preferences.getBoolean(KeyValue.check_key, KeyValue.check_value)){
            messageFromActivity();
        }else{
            Log.v("Hola","GPS activo");
        }
    }
    private void searchLocation(){

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
    /*
    private void hacerImagenVisible() {
        if(preferences.getBoolean(KeyValue.check_key, KeyValue.check_value)){
            image.setVisibility(View.VISIBLE);
        }else{
            image.setVisibility(View.GONE);
        }
    }
    */
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
    public void messageFromActivity(){
        textLatitud.setText(R.string.gps_desactivated);
    }
}