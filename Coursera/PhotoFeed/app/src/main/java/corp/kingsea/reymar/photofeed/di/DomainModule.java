package corp.kingsea.reymar.photofeed.di;

import android.content.Context;
import android.location.Geocoder;

import com.google.firebase.database.DatabaseReference;

import javax.inject.Singleton;

import corp.kingsea.reymar.photofeed.domain.FirebaseAPIoriginal;
import corp.kingsea.reymar.photofeed.domain.Util;
import dagger.Module;
import dagger.Provides;

/**
 * Created by reyma on 8/07/2016.
 */
@Module
public class DomainModule {

    String  firebaseURL;

    public DomainModule(String firebaseURL) {//recibo el atributo vacio
        this.firebaseURL = firebaseURL;
    }

    @Provides
    @Singleton
    FirebaseAPIoriginal providesFirebaseAPI(DatabaseReference firebase){
        return new FirebaseAPIoriginal(firebase);
    }

    @Provides
    @Singleton
    DatabaseReference providesFirebase(String firebaseURL){
        return new DatabaseReference(firebaseURL);
    }

    @Provides
    @Singleton
    String providesFirebaseURL (){
        return this.firebaseURL;
    }

    @Provides
    @Singleton
    Util providesUtil(Geocoder geocoder){
        return new Util(geocoder);
    }

    @Provides
    @Singleton
    Geocoder providesGeocoder(Context context){
        return new Geocoder(context);
    }

}
