package corp.kingsea.reymar.photofeed.login.di;

import javax.inject.Singleton;

import corp.kingsea.reymar.photofeed.domain.FirebaseAPIoriginal;
import corp.kingsea.reymar.photofeed.libs.base.EventBus;
import corp.kingsea.reymar.photofeed.login.LoginInteractor;
import corp.kingsea.reymar.photofeed.login.LoginInteractorImpl;
import corp.kingsea.reymar.photofeed.login.LoginPresenter;
import corp.kingsea.reymar.photofeed.login.LoginPresenterImpl;
import corp.kingsea.reymar.photofeed.login.LoginRepository;
import corp.kingsea.reymar.photofeed.login.LoginRepositoryImpl;
import corp.kingsea.reymar.photofeed.login.ui.LoginView;
import dagger.Module;
import dagger.Provides;

/**
 * Created by reyma on 8/07/2016.
 */
@Module
public class LoginModule {

    LoginView view;

    public LoginModule(LoginView view) {
        this.view = view;
    }

    @Provides @Singleton
    LoginView providesLogInView(){
        return this.view;
    }

    @Provides @Singleton
    LoginPresenter providesLoginPresenter(EventBus eventBus, LoginView loginView, LoginInteractor loginInteractor){
        return new LoginPresenterImpl(eventBus, loginView, loginInteractor);
    }

    @Provides @Singleton
    LoginInteractor providesLoginInteractor(LoginRepository repository){
        return new LoginInteractorImpl(repository);
    }

    @Provides @Singleton
    LoginRepository providesLoginRepository(EventBus eventBus, FirebaseAPIoriginal firebase){
        return new LoginRepositoryImpl(eventBus, firebase);
    }
}
