package corp.kingsea.reymar.facebookrecipes;

import android.app.Application;
import android.content.Intent;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.raizlabs.android.dbflow.config.FlowManager;

import corp.kingsea.reymar.facebookrecipes.di.DaggerRecipeMainComponent;
import corp.kingsea.reymar.facebookrecipes.di.RecipeMainComponent;
import corp.kingsea.reymar.facebookrecipes.di.RecipeMainModule;
import corp.kingsea.reymar.facebookrecipes.libs.di.LibsModule;
import corp.kingsea.reymar.facebookrecipes.login.ui.LoginActivity;
import corp.kingsea.reymar.facebookrecipes.recipelist.adapters.OnItemClickListener;
import corp.kingsea.reymar.facebookrecipes.recipelist.di.DaggerRecipeListComponent;
import corp.kingsea.reymar.facebookrecipes.recipelist.di.RecipeListComponent;
import corp.kingsea.reymar.facebookrecipes.recipelist.di.RecipeListModule;
import corp.kingsea.reymar.facebookrecipes.recipelist.ui.RecipeListActivity;
import corp.kingsea.reymar.facebookrecipes.recipelist.ui.RecipeListView;
import corp.kingsea.reymar.facebookrecipes.recipemain.ui.RecipeMainActivity;
import corp.kingsea.reymar.facebookrecipes.recipemain.ui.RecipeMainView;

/**
 * hereda de application que es la clase R, la clase principal
 **/

public class FacebookRecipesApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initFacebook();//inicializo la conexion con el api
        initDB();//inicializo dbflow
    }

    @Override
    public void onTerminate(){
        super.onTerminate();
        DBTearDown();//metodo para completar la destruccion
    }

    private void DBTearDown() {
        FlowManager.destroy();//se destruye la actividad
    }

    private void initDB() {
        FlowManager.init(this);//ademas de inicializarlo debo terminarlo onterminate()
    }

    private void initFacebook() {
        FacebookSdk.sdkInitialize(this);
    }

    public void onLogout() {
        LoginManager.getInstance().logOut();//sintaxis de la api de facebook para cerrar sesion
        Intent intent = new Intent(this, LoginActivity.class);//una vez cierre me va a llevar a la pagina principal
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                |Intent.FLAG_ACTIVITY_NEW_TASK
                |Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public RecipeMainComponent getRecipeMainComponent(RecipeMainActivity activity, RecipeMainView view){
        return DaggerRecipeMainComponent
                .builder()
                .libsModule(new LibsModule(activity))
                .recipeMainModule(new RecipeMainModule(view))
                .build();
    }

    public RecipeListComponent getRecipeListComponent(RecipeListActivity activity, RecipeListView view, OnItemClickListener clickListener){
        return DaggerRecipeListComponent
                .builder()
                .libsModule(new LibsModule(activity))
                .recipeListModule(new RecipeListModule(view, clickListener))
                .build();
    }
}
