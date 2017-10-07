package corp.kingsea.reymar.facebookrecipes.recipemain;

import java.util.Random;

import corp.kingsea.reymar.facebookrecipes.BuildConfig;
import corp.kingsea.reymar.facebookrecipes.api.RecipeSearchResponse;
import corp.kingsea.reymar.facebookrecipes.api.RecipeService;
import corp.kingsea.reymar.facebookrecipes.entities.Recipe;
import corp.kingsea.reymar.facebookrecipes.libs.base.EventBus;
import corp.kingsea.reymar.facebookrecipes.recipemain.events.RecipeMainEvent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by reyma on 7/07/2016.
 */
public class RecipeMainRepositoryImpl implements RecipeMainRepository {

    private int recipePage;
    private EventBus eventBus;
    private RecipeService service;

    public RecipeMainRepositoryImpl(EventBus eventBus, RecipeService service) {
        this.eventBus = eventBus;
        this.service = service;
    }

    @Override
    public void getNextRecipe() {//consulta con el orm
        //llamo a RECIPESERVICE y envio los elementos que me pide, son 4, en resumen es una query con sqlite**esto es dbflow
        Call<RecipeSearchResponse> call = service.search(BuildConfig.FOOD_API_KEY,RECENT_SORT,COUNT, recipePage);
        //aqui utilizo retrofit, los metodos los implementa automaticamente
        Callback<RecipeSearchResponse> callback = new Callback<RecipeSearchResponse>() {
            @Override
            public void onResponse(Call<RecipeSearchResponse> call, Response<RecipeSearchResponse> response) {
                if(response.isSuccess()){
                    RecipeSearchResponse recipeSearchResponse = response.body();//se asigna el cuerpo de la respuesta
                    if(recipeSearchResponse.getCount() == 0){//si la respuesta es 0
                        //es decir la consulta se realizo ok pero como el numero de conteo es random puede estar vacia la bd en esa casilla de consulta
                        setRecipePage(new Random().nextInt(RECIPE_RANGE));//vuelvo a generar la misma consulta
                        getNextRecipe();//vuelvo a ejecutar
                    }else{
                        Recipe recipe = recipeSearchResponse.getFirstRecipe();
                        if(recipe != null){
                            post(recipe);
                        }else{
                            post(response.message());
                        }
                    }
                }else{
                    post(response.message());//si hay un error, se reporta, de la respuesta sabemos que salio mal
                }
            }

            @Override
            public void onFailure(Call<RecipeSearchResponse> call, Throwable t) {
                post(t.getLocalizedMessage());
            }
        };
        call.enqueue(callback);
    }

    @Override
    public void saveRecipe(Recipe recipe) {
        recipe.save();//parametro dbflow
        post();//indica que ya se guardo
    }

    @Override
    public void setRecipePage(int recipePage) {
        this.recipePage = recipePage;
    }

    private void post(String error, int type, Recipe recipe){
        RecipeMainEvent event = new RecipeMainEvent();
        event.setType(type);//tipo de evento recibido
        event.setError(error);//error recibido
        event.setRecipe(recipe);
        eventBus.post(event);
    }

    private void post(Recipe recipe){
        post(null, RecipeMainEvent.NEXT_EVENT, recipe);
    }

    private void post(String error){
        post(error, RecipeMainEvent.NEXT_EVENT, null);
    }

    private void post(){
        post(null, RecipeMainEvent.SAVE_EVENT, null);
    }

}
