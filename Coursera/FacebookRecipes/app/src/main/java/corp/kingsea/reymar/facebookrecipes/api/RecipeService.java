package corp.kingsea.reymar.facebookrecipes.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * me va a exponer como se van a realizar las peticiones con retrofit
 **/
public interface RecipeService {//esta interface necesita un cliente por eso se crea recipeclient
    @GET("search")//sobre el metodo del api search, se hace la llamada de search
    Call<RecipeSearchResponse> search(@Query("key") String key,//api key
                                      @Query("sort") String sort,//orden
                                      @Query("count") int count,
                                      @Query("page") int page);//numero aleatorio
}
