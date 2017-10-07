package corp.kingsea.reymar.photofeed;

import android.app.Application;

import com.google.firebase.database.DatabaseReference;

import corp.kingsea.reymar.photofeed.di.DomainModule;
import corp.kingsea.reymar.photofeed.libs.di.LibsModule;
import corp.kingsea.reymar.photofeed.login.di.DaggerLoginComponent;
import corp.kingsea.reymar.photofeed.login.di.LoginComponent;
import corp.kingsea.reymar.photofeed.login.di.LoginModule;
import corp.kingsea.reymar.photofeed.login.ui.LoginView;

/**
 * Created by reyma on 8/07/2016.
 */
public class PhotoFeedApp extends Application {
    //declaro las variables que me van a permitir obtener los datos de fb, key url username
    private final static String EMAIL_KEY = "email";
    private final static String SHARED_PREFERENCES_NAME = "UserPrefs";
    private final static String FIREBASE_URL = "https://android-photo-share.firebase.com/";

    private DomainModule domainModule;
    private PhotoFeedAppModule photoFeedAppModule;

    @Override
    public void onCreate() {
        super.onCreate();
        initFirebase();
        initModules();
    }

    private void initModules() {//modulos de la inyeccion de dependencias
        photoFeedAppModule = new PhotoFeedAppModule(this);
        domainModule = new DomainModule(FIREBASE_URL);
    }

    private void initFirebase() {
        //DatabaseReference.setAndroidContext(this);
    }

    public String getEmailKey() {
        return EMAIL_KEY;
    }

    public String getSharedPreferencesName() {
        return SHARED_PREFERENCES_NAME;
    }

    public LoginComponent getLoginComponent(LoginView view){
        return DaggerLoginComponent
                .builder()
                .photoFeedAppModule(photoFeedAppModule)
                .domainModule(domainModule)
                .libsModule(new LibsModule(null))
                .loginModule(new LoginModule(view))
                .build();
    }
}
