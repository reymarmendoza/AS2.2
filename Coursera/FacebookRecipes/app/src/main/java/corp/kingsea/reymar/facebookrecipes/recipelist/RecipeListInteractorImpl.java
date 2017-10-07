package corp.kingsea.reymar.facebookrecipes.recipelist;

/**
 * Created by reyma on 7/07/2016.
 */
public class RecipeListInteractorImpl implements RecipeListInteractor {

    private RecipeListRepository repository;

    public RecipeListInteractorImpl(RecipeListRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute() {
        repository.getSavedRecipes();
    }
}
