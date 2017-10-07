package corp.kingsea.reymar.facebookrecipes.recipelist;

import corp.kingsea.reymar.facebookrecipes.entities.Recipe;

/**
 * Created by reyma on 7/07/2016.
 */
public interface StoredRecipesInteractor {

    void executeUpdate(Recipe recipe);
    void executeDelete(Recipe recipe);

}
