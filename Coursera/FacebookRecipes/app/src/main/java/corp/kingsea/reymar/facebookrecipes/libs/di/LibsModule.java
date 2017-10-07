package corp.kingsea.reymar.facebookrecipes.libs.di;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import javax.inject.Singleton;

import corp.kingsea.reymar.facebookrecipes.libs.GlideImageLoader;
import corp.kingsea.reymar.facebookrecipes.libs.GreenRobotEventBus;
import corp.kingsea.reymar.facebookrecipes.libs.base.EventBus;
import corp.kingsea.reymar.facebookrecipes.libs.base.ImageLoader;
import dagger.Module;
import dagger.Provides;

/**
 * Created by reyma on 2/07/2016.
 */
@Module
public class LibsModule {

    private Activity activity;

    public LibsModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @Singleton
    ImageLoader providesImageLoader(RequestManager requestManager){
        return new GlideImageLoader(requestManager);
    }

    @Provides
    @Singleton
    RequestManager providesRequestManager(Activity activity){
        return Glide.with(activity);
    }

    @Provides
    @Singleton
    Activity providesActivity(){
        return this.activity;
    }

    @Provides
    @Singleton //solo tiene una instancia
    EventBus providesEventBus(org.greenrobot.eventbus.EventBus eventBus){
        return new GreenRobotEventBus(eventBus);
    }
    //de la libreria pasa al objeto, el de abajo envia al de arriba
    @Provides
    @Singleton
    org.greenrobot.eventbus.EventBus providesLibraryEventBus(){
        return org.greenrobot.eventbus.EventBus.getDefault();
    }
}
