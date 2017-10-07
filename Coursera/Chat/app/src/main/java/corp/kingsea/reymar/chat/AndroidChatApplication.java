package corp.kingsea.reymar.chat;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * pasar del viejo firebase al nuevo
 * https://firebase.google.com/support/guides/firebase-android
 **/
public class AndroidChatApplication extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        setupFirebase();
    }

    private void setupFirebase() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
