package corp.kingsea.reymar.facebookrecipes.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by reyma on 5/07/2016.
 */
public class RecipeClient {
    private Retrofit retrofit;//objeto que puedo construir en el cliente(aqui) o puedo recibir como parametro(inyectarlo)
    private final static String BASE_URL = "http://food2fork.com/api/";

    public RecipeClient() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)//recibo la url del api
                .addConverterFactory(GsonConverterFactory.create())//convierto el valor recibido a JSON
                .build();//construyo
    }
    public RecipeService getRecipeService(){
        return this.retrofit.create(RecipeService.class);
    }
}
