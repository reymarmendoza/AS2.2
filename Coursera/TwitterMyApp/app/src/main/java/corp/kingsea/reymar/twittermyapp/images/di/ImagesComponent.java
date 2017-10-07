package corp.kingsea.reymar.twittermyapp.images.di;

import javax.inject.Singleton;

import corp.kingsea.reymar.twittermyapp.images.ImagesPresenter;
import corp.kingsea.reymar.twittermyapp.images.ui.ImagesFragment;
import corp.kingsea.reymar.twittermyapp.lib.di.LibsModule;
import dagger.Component;

/**
 * Created by reyma on 3/07/2016.
 */
@Singleton @Component(modules = {LibsModule.class, ImagesModule.class})
public interface ImagesComponent {
    void inject(ImagesFragment fragment);
    ImagesPresenter getPresenter();
}
