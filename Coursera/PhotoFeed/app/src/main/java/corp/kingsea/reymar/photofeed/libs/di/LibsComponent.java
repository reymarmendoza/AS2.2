package corp.kingsea.reymar.photofeed.libs.di;

import javax.inject.Singleton;

import corp.kingsea.reymar.photofeed.PhotoFeedAppModule;
import dagger.Component;

/**
 * Created by reyma on 8/07/2016.
 */
@Singleton
@Component(modules = {LibsModule.class, PhotoFeedAppModule.class})
public interface LibsComponent {
}
