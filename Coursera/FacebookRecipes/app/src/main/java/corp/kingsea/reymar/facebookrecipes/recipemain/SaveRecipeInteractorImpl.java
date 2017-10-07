package corp.kingsea.reymar.facebookrecipes.recipemain;

import corp.kingsea.reymar.facebookrecipes.entities.Recipe;

/**
 * Created by reyma on 7/07/2016.
 */
public class SaveRecipeInteractorImpl implements SaveRecipeInteractor {

    RecipeMainRepository repository;

    public SaveRecipeInteractorImpl(RecipeMainRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(Recipe recipe) {
        repository.saveRecipe(recipe);
    }
}
