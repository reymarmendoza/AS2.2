package corp.kingsea.reymar.pruebafirebase;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by reyma on 11/07/2016.
 */
public class InicialiseFirebase extends Application {
    //inicializo firebase, el mismo hace la conexion gracias al arhivo json
    @Override
    public void onCreate() {
        super.onCreate();
        setupFireBase();
    }
    //debo agregar el permiso de internet en el manifest y agregar este archivo
    private void setupFireBase() {
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
    //con esto ya tengo conexion a firebase
}
