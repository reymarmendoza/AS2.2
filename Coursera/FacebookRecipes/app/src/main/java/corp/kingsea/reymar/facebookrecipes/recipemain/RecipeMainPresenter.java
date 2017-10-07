package corp.kingsea.reymar.facebookrecipes.recipemain;

import corp.kingsea.reymar.facebookrecipes.entities.Recipe;
import corp.kingsea.reymar.facebookrecipes.recipemain.events.RecipeMainEvent;
import corp.kingsea.reymar.facebookrecipes.recipemain.ui.RecipeMainView;

/**
 * Created by reyma on 7/07/2016.
 */
public interface RecipeMainPresenter {
    //estos metodos los implemento cuando creo una clase de tipo NOMBREDEESTAINTERFACE
    void onCreate();
    void onDestroy();

    void dismissRecipe();
    void getNextRecipe();
    void saveRecipe(Recipe recipe);
    void onEventMainThread(RecipeMainEvent event);

    void imageReady();
    void imageError(String error);

    RecipeMainView getView();//ojo ya estaba implementado en video2

}
