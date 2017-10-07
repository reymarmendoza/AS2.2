package corp.kingsea.reymar.ultimatepreferences;

import android.content.Context;
import android.preference.EditTextPreference;

/**
 * esta clase es auxiliar de preferencefragment donde va a mostrar la ventana del edittext desde codigo sin el userclick
 */

public class EditTextPreferenceOpenWindow extends EditTextPreference {

    public EditTextPreferenceOpenWindow(Context context) {
        super(context);
    }

    public void show() {
        showDialog(null);
    }
}
