package corp.kingsea.reymar.facebookrecipes.recipelist.di;

/**
 * Created by reyma on 7/07/2016.
 */

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import corp.kingsea.reymar.facebookrecipes.entities.Recipe;
import corp.kingsea.reymar.facebookrecipes.libs.base.EventBus;
import corp.kingsea.reymar.facebookrecipes.libs.base.ImageLoader;
import corp.kingsea.reymar.facebookrecipes.recipelist.RecipeListInteractor;
import corp.kingsea.reymar.facebookrecipes.recipelist.RecipeListInteractorImpl;
import corp.kingsea.reymar.facebookrecipes.recipelist.RecipeListPresenter;
import corp.kingsea.reymar.facebookrecipes.recipelist.RecipeListPresenterImpl;
import corp.kingsea.reymar.facebookrecipes.recipelist.RecipeListRepository;
import corp.kingsea.reymar.facebookrecipes.recipelist.RecipeListRepositoryImpl;
import corp.kingsea.reymar.facebookrecipes.recipelist.StoredRecipesInteractor;
import corp.kingsea.reymar.facebookrecipes.recipelist.StoredRecipesInteractorImpl;
import corp.kingsea.reymar.facebookrecipes.recipelist.ui.RecipeListView;
import corp.kingsea.reymar.facebookrecipes.recipelist.adapters.OnItemClickListener;
import corp.kingsea.reymar.facebookrecipes.recipelist.adapters.RecipesAdapter;

@Module
public class RecipeListModule {
    RecipeListView view;
    OnItemClickListener ClickListener;

    public RecipeListModule(RecipeListView view, OnItemClickListener onItemClickListener) {
        this.view = view;
        this.ClickListener = onItemClickListener;
    }

    @Provides @Singleton
    RecipeListView provideRecipeListView() {
        return this.view;
    }

    @Provides @Singleton
    RecipeListPresenter provideRecipeListPresenter(EventBus eventBus, RecipeListView view, RecipeListInteractor listInteractor, StoredRecipesInteractor storedInteractor) {
        return new RecipeListPresenterImpl(eventBus, view, listInteractor, storedInteractor);
    }

    @Provides @Singleton
    RecipeListInteractor provideRecipeListInteractor(RecipeListRepository repository) {
        return new RecipeListInteractorImpl(repository);
    }

    @Provides @Singleton
    StoredRecipesInteractor provideStoredRecipesInteractor(RecipeListRepository repository) {
        return new StoredRecipesInteractorImpl(repository);
    }

    @Provides @Singleton
    RecipeListRepository provideRecipeListRepository(EventBus eventBus) {
        return new RecipeListRepositoryImpl(eventBus);
    }

    @Provides @Singleton
    RecipesAdapter provideRecipesAdapter(List<Recipe> recipes, ImageLoader imageLoader, OnItemClickListener onItemClickListener) {
        return new RecipesAdapter(recipes, imageLoader, onItemClickListener);
    }

    @Provides @Singleton
    OnItemClickListener provideOnItemClickListener() {
        return this.ClickListener;
    }

    @Provides @Singleton
    List<Recipe> providesEmptyList() {
        return new ArrayList<Recipe>();
    }

}