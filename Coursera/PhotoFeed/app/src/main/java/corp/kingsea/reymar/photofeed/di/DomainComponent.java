package corp.kingsea.reymar.photofeed.di;

import javax.inject.Singleton;

import corp.kingsea.reymar.photofeed.PhotoFeedAppModule;
import dagger.Component;

/**
 * Created by reyma on 8/07/2016.
 */
@Singleton
@Component(modules = {DomainModule.class, PhotoFeedAppModule.class})//necesito dos modulos xq el viewholder conexto me lo prevee el PhotoFeedAppModule
public interface DomainComponent {
}
