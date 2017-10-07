package corp.kingsea.reymar.facebookrecipes.di;

import javax.inject.Singleton;

import corp.kingsea.reymar.facebookrecipes.api.RecipeClient;
import corp.kingsea.reymar.facebookrecipes.api.RecipeService;
import corp.kingsea.reymar.facebookrecipes.libs.base.EventBus;
import corp.kingsea.reymar.facebookrecipes.recipemain.GetNextRecipeInteractor;
import corp.kingsea.reymar.facebookrecipes.recipemain.GetNextRecipeInteractorImpl;
import corp.kingsea.reymar.facebookrecipes.recipemain.RecipeMainPresenter;
import corp.kingsea.reymar.facebookrecipes.recipemain.RecipeMainPresenterImpl;
import corp.kingsea.reymar.facebookrecipes.recipemain.RecipeMainRepository;
import corp.kingsea.reymar.facebookrecipes.recipemain.RecipeMainRepositoryImpl;
import corp.kingsea.reymar.facebookrecipes.recipemain.SaveRecipeInteractor;
import corp.kingsea.reymar.facebookrecipes.recipemain.SaveRecipeInteractorImpl;
import corp.kingsea.reymar.facebookrecipes.recipemain.ui.RecipeMainView;
import dagger.Module;
import dagger.Provides;

/**
 * Created by reyma on 7/07/2016.
 */

@Module
public class RecipeMainModule {

    RecipeMainView view;

    public RecipeMainModule(RecipeMainView view){
        this.view = view;
    }

    @Provides @Singleton//provee la vista
    RecipeMainView providesRecipeMainView(){
        return this.view;
    }

    @Provides @Singleton//provee la vista
    RecipeMainPresenter providesRecipeMainPresenter(EventBus eventBus, RecipeMainView view, SaveRecipeInteractor saveInteractor, GetNextRecipeInteractor getNextRecipeInteractor){
        return new RecipeMainPresenterImpl(eventBus,view,saveInteractor,getNextRecipeInteractor);
    }

    @Provides @Singleton
    SaveRecipeInteractor providesSaveRecipeInteractor(RecipeMainRepository repository){
        return new SaveRecipeInteractorImpl(repository);
    }

    @Provides @Singleton
    GetNextRecipeInteractor providesGetNextRecipeInteractor(RecipeMainRepository repository){
        return new GetNextRecipeInteractorImpl(repository);
    }

    @Provides @Singleton
    RecipeMainRepository providesRecipeMainRepository(EventBus eventBus, RecipeService service){
        return new RecipeMainRepositoryImpl(eventBus,service);
    }

    @Provides @Singleton
    RecipeService providesRecipeService(){
        return new RecipeClient().getRecipeService();
    }
}
