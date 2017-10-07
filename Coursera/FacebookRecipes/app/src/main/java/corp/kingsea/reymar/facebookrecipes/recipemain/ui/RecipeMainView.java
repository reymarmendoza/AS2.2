package corp.kingsea.reymar.facebookrecipes.recipemain.ui;

import corp.kingsea.reymar.facebookrecipes.entities.Recipe;

/**
 * Created by reyma on 7/07/2016.
 */
public interface RecipeMainView {

    void showProgress();
    void hideProgress();
    void showUIElements();
    void hideUIElements();
    void saveAnimation();
    void dismissAnimation();

    void onRecipeSaved();//si quiero guardar la receta voy a tener una reaccion de la vista

    void setRecipe(Recipe recipe);//si quiero varias recetas recibo los datos
    void onGetRecipeError(String error);//si genera error recibo el error

}
