package corp.kingsea.reymar.ultimatepreferences;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by reyma on 1/18/2017.
 */

public class UserDialogFragment extends DialogFragment{

    private static final int dialogTitle = 0;
    private static final int dialogMessage = 1;
    private static final int dialogPositive = 2;
    private static final int dialogNegative = 3;
    private static final int noInteractionButtons = 3;
    ArrayList<Integer> window;

    Bundle data = new Bundle(1);//justOneElementNeeded

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        Bundle bundle = this.getArguments();
        window = bundle.getIntegerArrayList("dialogWindow");
        //el bundle va a recibir la respuesta(ok o cancelar) del dialogfragment
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(window.get(dialogMessage)).setTitle(window.get(dialogTitle));
        //de acuerdo al setTitle voy a modificar la ejecucion de positive y negative buttons
        builder.setPositiveButton(window.get(dialogPositive), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (window.get(dialogTitle) == R.string.gps_warn) {
                    setData(true);
                    initializePreferenceFragment(data);
                }else{

                }
            }
        });
        if(window.size() > noInteractionButtons) {
            builder.setNegativeButton(window.get(dialogNegative), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    if (window.get(dialogMessage) == R.string.gps_warn) {
                        setData(false);
                        initializePreferenceFragment(data);
                    }
                }
            });
        }
        return builder.create();
    }

    private void setData(boolean selection) {
        dismiss();//cierra la ventana del dialog
        data.clear();//borra la informacion anteriormente contenida
        data.putBoolean("locationByGPS", selection);//crea una posicion locationByGPS de valor true
    }

    //crea el fragmento recibiendo la data de la consulta en el dialog
    private void initializePreferenceFragment(Bundle data) {
        Fragment fragment = new PreferencesFragment();
        fragment.setArguments(data);

        getFragmentManager().beginTransaction()
                .replace(R.id.activity_main, fragment, "PreferenceFragment")
                .addToBackStack(null).commit();
        //debe habilitar el check de la ubicacon y bloquear eledittext
    }

}
