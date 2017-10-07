package corp.kingsea.reymar.twittermyapp.hashtags.di;

import javax.inject.Singleton;

import corp.kingsea.reymar.twittermyapp.hashtags.HashtagsPresenter;
import corp.kingsea.reymar.twittermyapp.hashtags.ui.HashtagsFragment;
import corp.kingsea.reymar.twittermyapp.lib.di.LibsModule;
import dagger.Component;

/**
 * Created by reyma on 3/07/2016.
 */
@Singleton @Component(modules = {LibsModule.class, HashtagsModule.class})
public interface HashtagsComponent {
    void inject(HashtagsFragment fragment);
    HashtagsPresenter getPresenter();
}
