package corp.kingsea.reymar.photofeed.login.di;

import javax.inject.Singleton;

import corp.kingsea.reymar.photofeed.PhotoFeedAppModule;
import corp.kingsea.reymar.photofeed.di.DomainModule;
import corp.kingsea.reymar.photofeed.libs.di.LibsModule;
import corp.kingsea.reymar.photofeed.login.ui.LoginActivity;
import dagger.Component;

/**
 * Created by reyma on 8/07/2016.
 */
@Singleton
@Component(modules = {LoginModule.class, DomainModule.class, LibsModule.class, PhotoFeedAppModule.class})
public interface LoginComponent {

    void inject(LoginActivity activity);//este es el target

}
