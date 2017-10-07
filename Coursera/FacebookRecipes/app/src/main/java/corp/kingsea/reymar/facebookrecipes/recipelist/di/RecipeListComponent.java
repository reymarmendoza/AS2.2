package corp.kingsea.reymar.facebookrecipes.recipelist.di;

/**
 * Created by reyma on 7/07/2016.
 */
import javax.inject.Singleton;

import dagger.Component;
import corp.kingsea.reymar.facebookrecipes.libs.di.LibsModule;
import corp.kingsea.reymar.facebookrecipes.recipelist.RecipeListPresenter;
import corp.kingsea.reymar.facebookrecipes.recipelist.adapters.RecipesAdapter;

@Singleton
@Component(modules = {RecipeListModule.class, LibsModule.class})
public interface RecipeListComponent {
    RecipeListPresenter getPresenter();
    RecipesAdapter getAdapter();
}