package corp.kingsea.reymar.facebookrecipes.recipemain;

import org.greenrobot.eventbus.Subscribe;

import corp.kingsea.reymar.facebookrecipes.entities.Recipe;
import corp.kingsea.reymar.facebookrecipes.libs.base.EventBus;
import corp.kingsea.reymar.facebookrecipes.recipemain.events.RecipeMainEvent;
import corp.kingsea.reymar.facebookrecipes.recipemain.ui.RecipeMainView;

/**
 * Created by reyma on 7/07/2016.
 */
public class RecipeMainPresenterImpl implements RecipeMainPresenter{

    private EventBus eventBus;
    private RecipeMainView view;
    SaveRecipeInteractor saveInteractor;
    GetNextRecipeInteractor getNextRecipeInteractor;

    public RecipeMainPresenterImpl(EventBus eventBus, RecipeMainView view, SaveRecipeInteractor saveInteractor, GetNextRecipeInteractor getNextRecipeInteractor) {
        this.eventBus = eventBus;
        this.view = view;
        this.saveInteractor = saveInteractor;
        this.getNextRecipeInteractor = getNextRecipeInteractor;
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        view = null;//para destruir la vista la vuelvo nula
    }

    @Override
    public void dismissRecipe() {
        if(this.view != null){//si tengo una vista disponible
            view.dismissAnimation();
        }
        getNextRecipe();
    }

    @Override
    public void getNextRecipe() {
        if(this.view != null){
            view.hideUIElements();
            view.showProgress();
        }
        getNextRecipeInteractor.execute();
    }

    @Override
    public void saveRecipe(Recipe recipe) {
        if(this.view != null){
            view.saveAnimation();
            view.hideUIElements();
            view.showProgress();
        }
        saveInteractor.execute(recipe);
    }

    @Override
    @Subscribe//suscrito a eventbus
    public void onEventMainThread(RecipeMainEvent event) {//me permite manipular el resultado de los eventos
        if(this.view != null){
            String error = event.getError();
            if(error != null){//si existe un error
                view.hideProgress();
                view.onGetRecipeError(error);
            }else{
                if(event.getType() == RecipeMainEvent.NEXT_EVENT){//VALIDO EL TIPO DE EVENTO
                    view.setRecipe(event.getRecipe());//si es next_event quiere decir que se descarto la imagen
                }else if(event.getType() == RecipeMainEvent.SAVE_EVENT){
                    view.onRecipeSaved();//de lo contratio si es save se guarda y se pide la siguiente
                    getNextRecipeInteractor.execute();
                }
            }
        }
    }

    @Override
    public void imageReady() {
        if(this.view != null){
            view.hideProgress();
            view.showUIElements();
        }
    }

    @Override
    public void imageError(String error) {
        if(this.view != null){
            view.onGetRecipeError(error);
        }
    }

    @Override
    public RecipeMainView getView() {
        return this.view;
    }
}
