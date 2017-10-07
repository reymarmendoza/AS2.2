package corp.kingsea.reymar.facebookrecipes.recipelist.ui;

import java.util.List;

import corp.kingsea.reymar.facebookrecipes.entities.Recipe;

/**
 * lo que puede pasar con la vista
 */
public interface RecipeListView {

    void setRecipes(List<Recipe> data);//recibo los
    void recipeUpdated();
    void recipeDeleted(Recipe recipe);

}
