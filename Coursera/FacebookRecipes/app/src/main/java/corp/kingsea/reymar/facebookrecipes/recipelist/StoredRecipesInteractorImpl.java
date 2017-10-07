package corp.kingsea.reymar.facebookrecipes.recipelist;

import corp.kingsea.reymar.facebookrecipes.entities.Recipe;

/**
 * Created by reyma on 7/07/2016.
 */
public class StoredRecipesInteractorImpl implements StoredRecipesInteractor {

    private RecipeListRepository repository;

    public StoredRecipesInteractorImpl(RecipeListRepository repository) {
        this.repository = repository;
    }

    @Override
    public void executeUpdate(Recipe recipe) {
        repository.updateRecipe(recipe);
    }

    @Override
    public void executeDelete(Recipe recipe) {
        repository.removeRecipe(recipe);
    }
}
