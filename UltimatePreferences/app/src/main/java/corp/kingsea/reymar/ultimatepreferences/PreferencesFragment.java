package corp.kingsea.reymar.ultimatepreferences;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by reyma on 1/13/2017.
 */

public class PreferencesFragment extends PreferenceFragment
        implements SharedPreferences.OnSharedPreferenceChangeListener{

    SharedPreferences pref;
    CheckBoxPreference checkBox;
    EditTextPreference editText;
    ListPreference list;
    SwitchPreference switc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        addPreferencesFromResource(R.xml.fragment_preferences);
        getPreferencesFile();
        getPreferenceUIElements();
        setSummaryByOptionSelected();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        validateSettedPreference();
    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    private void getPreferencesFile() {
        //obtengo el archivo de preferencias, si no existe, lo crea
        pref = getActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
    }

    private void getPreferenceUIElements() {
        //obtiene la referencia a los elementos de la ui
        checkBox = (CheckBoxPreference) findPreference(KeyValue.check_key);
        editText = (EditTextPreference) findPreference(KeyValue.edit_key);
        list = (ListPreference) findPreference(KeyValue.list_key);
        switc = (SwitchPreference) findPreference(KeyValue.switch_key);
    }

    private void validateSettedPreference() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED){
                if(checkPermission()){
                    checkBox.setEnabled(true);
                    checkBox.setSummary(R.string.permission_granted);

                    if (pref.getBoolean(KeyValue.check_key, KeyValue.check_value)) {
                        //checkVSedit(true);
                    } else {
                        //checkVSedit(false);
                    }
                }else{

                    checkBox.setEnabled(false);
                    checkBox.setSummary(R.string.permission_not_granted);
                }
            }
        }else{
            //no es necesario, cuando valida la version
            //checkBoxSummary();
            //checkVSedit(pref.getBoolean(KeyValue.check_key, KeyValue.check_value));
            //setSummaryByOptionSelected();
        }
    }

    private boolean checkPermission() {
        boolean version = (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP);
        boolean permission = (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                     == PackageManager.PERMISSION_GRANTED);
        return (version && permission);
    }

    private void setSummaryByOptionSelected(){
        //muestra el item seleccionado por el usuario
        checkBoxSummary();
        //checkBox.setSummary(booleanSummary(KeyValue.check_key, KeyValue.check_value));
        //editText.setSummary(pref.getString(KeyValue.edit_key, KeyValue.edit_value));
        list.setSummary(pref.getString(KeyValue.list_key, KeyValue.list_value));
        switc.setSummary(booleanSummary(KeyValue.switch_key, KeyValue.switch_value));
    }

    //boolean solo tienen dos estados; cuando se interactua con el, cambia de estado, por eso devuelvo el estado contrario
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        //sharedPreferences solo maneja la interaccion del usuario, los metodos escriben sobre la preferencia global
        switch (key){
            case KeyValue.check_key:
                checkBoxSummary();
                //boolean state = sharedPreferences.getBoolean(key, KeyValue.check_value);
                //checkChange(state);
                //changeSummaryAccordingPriority();
                //checkVSedit(state);
                break;
            case KeyValue.edit_key:
                editChange(sharedPreferences.getString(key , KeyValue.edit_value));
                break;
            case KeyValue.list_key:
                songFromList(sharedPreferences.getString(key, KeyValue.list_value));
                break;
            case KeyValue.switch_key:
                switchChange(sharedPreferences.getBoolean(key, KeyValue.switch_value));
                break;
        }
    }

    private String booleanSummary(String key, boolean value){
        String estado;
        if(pref.getBoolean(key, value)){
            estado = "Activado";
        }else{
            estado = "Desactivado";
        }
        return estado;
    }

    //el estado del checkbox afecta al edittext ya que la ubicacion se da por uno de los dos con prioridad para el check
    private void checkBoxSummary() {
        String messageCheck;
        String messageEditText;
        boolean check = pref.getBoolean(KeyValue.check_key, KeyValue.check_value);
        boolean newStateCheck = !check;
        if(check) {
            messageCheck = "GPS activo";
            messageEditText = "Ubicacion definida por GPS";
        }else{
            messageCheck = "Escribe tu ciudad en Definir Ciudad";
            messageEditText = "";
        }
        checkBox.setSummary(messageCheck);
        editText.setSummary(messageEditText);
        pref.edit().putBoolean(KeyValue.check_key, newStateCheck).apply();
    }

    //sobreescribe los cambios de edittext
    private void editChange(String text) {
        pref.edit().putString(KeyValue.edit_key, text).apply();
        editText.setSummary(text);
    }

    //obtiene los id de los recursos raw, convierte la sel del user al raw que necesito reproducir
    private void songFromList(String nameSong) {
        //String[] values = getResources().getStringArray(R.array.value); obtiene los valores del array pero no permite usarlos en switch
        int songToPlay = 0;
        switch(nameSong){//list es el numero de la cancion en el <array-string> values
            case "Hep cats":
                songToPlay = R.raw.hep_cats;
                break;
            case "In your arms":
                songToPlay = R.raw.in_your_arms;
                break;
            case "Spy glass":
                songToPlay = R.raw.spy_glass;
                break;
        }
        listChange(nameSong, songToPlay);
    }

    private void listChange(String nameSong, int songToPlay) {
        //guarda el nombre de la cancion para ser manejado posteriormente cuando se active onResume de MainFragment
        pref.edit().putString(KeyValue.list_key, nameSong).apply();
        //guarda el recurso al que se accedio segun el nombre de la cancion seleccionada
        pref.edit().putInt(KeyValue.listResouce_key, songToPlay).apply();
        //coloca el nombre de la cancion escogida en un microtexto bajo el nombre del listpreferece
        findPreference(KeyValue.list_key).setSummary(nameSong);
    }

    //sobreescribe los cambios del switch
    private void switchChange(boolean switcc) {
        pref.edit().putBoolean(KeyValue.switch_key, switcc).apply();
        switc.setSummary(booleanSummary(KeyValue.switch_key, switcc));
    }

}

/*
llenar el entryValues dinamicamente, solo sirve para cadenas
    private void setListViewValues(){
        ListPreference lista = (ListPreference) findPreference(KeyValue.list_key);
        CharSequence[] entryValues = {R.raw.hep_cats, R.raw.in_your_arms, R.raw.spy_glass};
        lista.setEntryValues(entryValues);
    }
*/
