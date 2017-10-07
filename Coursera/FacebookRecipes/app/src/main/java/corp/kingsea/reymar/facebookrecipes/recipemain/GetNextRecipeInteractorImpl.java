package corp.kingsea.reymar.facebookrecipes.recipemain;

import java.util.Random;

/**
 * Created by reyma on 7/07/2016.
 */
public class GetNextRecipeInteractorImpl implements GetNextRecipeInteractor{

    RecipeMainRepository repository;

    public GetNextRecipeInteractorImpl(RecipeMainRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute() {
        int recipePage = new Random().nextInt(RecipeMainRepository.RECIPE_RANGE);//genero un numeor aleatorio segun rango
        repository.setRecipePage(recipePage);
        repository.getNextRecipe();
    }
}
