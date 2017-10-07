package corp.kingsea.reymar.photofeed;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by reyma on 8/07/2016.
 */
@Module
public class PhotoFeedAppModule {

    PhotoFeedApp app;

    public PhotoFeedAppModule(PhotoFeedApp app) {
        this.app = app;
    }

    @Provides @Singleton
    Context providesApplicationContext(){
        return app.getApplicationContext();//devolver sobre el app
    }

    @Provides @Singleton
    SharedPreferences providesSharedPreferences(Application application){
        return application.getSharedPreferences(app.getSharedPreferencesName(), Context.MODE_PRIVATE);//acceder de forma privada
    }

    @Provides @Singleton
    Application providesApplication(){
        return this.app;
    }

}
