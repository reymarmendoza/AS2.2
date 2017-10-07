package corp.kingsea.reymar.twittermyapp.lib.di;

import android.support.v4.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import javax.inject.Singleton;

import corp.kingsea.reymar.twittermyapp.lib.GlideImageLoader;
import corp.kingsea.reymar.twittermyapp.lib.GreenRobotEventBus;
import corp.kingsea.reymar.twittermyapp.lib.base.EventBus;
import corp.kingsea.reymar.twittermyapp.lib.base.ImageLoader;
import dagger.Module;
import dagger.Provides;

/**
 * Created by reyma on 2/07/2016.
 */
@Module
public class LibsModule {

    private Fragment fragment;

    public LibsModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @Singleton
    ImageLoader providesImageLoader(RequestManager requestManager){
        return new GlideImageLoader(requestManager);
    }

    @Provides
    @Singleton
    RequestManager providesRequestManager(Fragment fragment){
        return Glide.with(fragment);
    }

    @Provides
    @Singleton
    Fragment providesFragment(){
        return this.fragment;
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
