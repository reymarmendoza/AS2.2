package corp.kingsea.reymar.facebookrecipes.di;

import javax.inject.Singleton;

import corp.kingsea.reymar.facebookrecipes.libs.base.ImageLoader;
import corp.kingsea.reymar.facebookrecipes.libs.di.LibsModule;
import corp.kingsea.reymar.facebookrecipes.recipemain.RecipeMainPresenter;
import corp.kingsea.reymar.facebookrecipes.recipemain.ui.RecipeMainActivity;
import dagger.Component;

/**
 * Created by reyma on 7/07/2016.
 */
@Singleton
@Component(modules = {RecipeMainModule.class, LibsModule.class})//modulos de dagger, lo que va a inyecatar la libreria
public interface RecipeMainComponent {//me va a especificar el api con el que se va a hacer la inyeccion
    //void inject(RecipeMainActivity activity);
    //para la inyeccion se van a usar 2 metodos, 1 con get que es el objeto que quiero inyectar y el otro que devulvo
    //para que los dos funcionen tengo que tener un metodo provide que devuelva exactamente el obj Iamgeloader
    ImageLoader getImageLoader();
    RecipeMainPresenter getPresenter();
}
