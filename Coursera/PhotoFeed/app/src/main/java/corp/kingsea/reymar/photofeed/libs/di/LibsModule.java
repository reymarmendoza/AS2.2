package corp.kingsea.reymar.photofeed.libs.di;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.cloudinary.Cloudinary;
import com.cloudinary.android.Utils;

import javax.inject.Singleton;

import corp.kingsea.reymar.photofeed.libs.CloudinaryImageStorage;
import corp.kingsea.reymar.photofeed.libs.GlideImageLoader;
import corp.kingsea.reymar.photofeed.libs.GreenRobotEventBus;
import corp.kingsea.reymar.photofeed.libs.base.EventBus;
import corp.kingsea.reymar.photofeed.libs.base.ImageLoader;
import corp.kingsea.reymar.photofeed.libs.base.ImageStorage;
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

    @Provides
    @Singleton
    ImageStorage providesImageStorage(Cloudinary cloudinary){
        return new CloudinaryImageStorage(cloudinary);
    }

    @Provides
    @Singleton
    Cloudinary providesCloudinary(Context context){
        return new Cloudinary(Utils.cloudinaryUrlFromContext(context));
    }

}
