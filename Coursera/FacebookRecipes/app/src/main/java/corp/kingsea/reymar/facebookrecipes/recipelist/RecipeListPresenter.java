package corp.kingsea.reymar.facebookrecipes.recipelist;

import corp.kingsea.reymar.facebookrecipes.entities.Recipe;
import corp.kingsea.reymar.facebookrecipes.recipelist.events.RecipeListEvent;
import corp.kingsea.reymar.facebookrecipes.recipelist.ui.RecipeListView;

/**
 * Created by reyma on 7/07/2016.
 */
public interface RecipeListPresenter {

    void onCreate();
    void onDestroy();

    void getRecipes();
    void removeRecipe(Recipe recipe);
    void toggleFavorite(Recipe recipe);
    void onEventMainThread(RecipeListEvent event);

    RecipeListView getView();

}
