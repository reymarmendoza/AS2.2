package corp.kingsea.reymar.facebookrecipes.recipemain.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import corp.kingsea.reymar.facebookrecipes.FacebookRecipesApp;
import corp.kingsea.reymar.facebookrecipes.R;
import corp.kingsea.reymar.facebookrecipes.recipelist.ui.RecipeListActivity;
import corp.kingsea.reymar.facebookrecipes.di.RecipeMainComponent;
import corp.kingsea.reymar.facebookrecipes.entities.Recipe;
import corp.kingsea.reymar.facebookrecipes.libs.base.ImageLoader;
import corp.kingsea.reymar.facebookrecipes.recipemain.RecipeMainPresenter;

public class RecipeMainActivity extends AppCompatActivity implements RecipeMainView, SwipeGestureListener {

    @Bind(R.id.imgRecipe)
    ImageView imgRecipe;
    @Bind(R.id.imgDismiss)
    ImageButton imgDismiss;
    @Bind(R.id.imgKeep)
    ImageButton imgKeep;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.layoutContainer)
    RelativeLayout layoutContainer;
    //al crear este metodo lo instancio para que me permita guardar un objeto el cual es una interface a la que
    //tendiria acceso a implementar sus metodos
    private RecipeMainPresenter presenter;//despues de declararlo lo creo en el constructor
    private Recipe currentRecipe;
    private ImageLoader imageLoader;
    private RecipeMainComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_main);
        ButterKnife.bind(this);
        setupInjection();
        setupImageLoader();
        setupGestureDetection();
        presenter.onCreate();//creo el objeto
        presenter.getNextRecipe();//obtengo una receta
    }

    private void setupGestureDetection() {
        final GestureDetector gestureDetector = new GestureDetector(this, new SwipeGestureDetection(this));
        View.OnTouchListener gestureOnTouchListener = new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent){
                return gestureDetector.onTouchEvent(motionEvent);
            }
        };
        imgRecipe.setOnTouchListener(gestureOnTouchListener);
    }

    private void setupImageLoader() {
        RequestListener glideRequestListener = new RequestListener() {//metodo de la libreria de glide
            @Override//me sirve para mostrar un mensaje de error
            public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
                presenter.imageError(e.getLocalizedMessage());//muestra un mensaje de error de acuerdo a geolocalizacion
                return false;
            }

            @Override//me sirve paraocultar la barra de prograeso y mostrar los elementos de UI
            public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
                presenter.imageReady();
                return false;
            }
        };
        imageLoader.setOnFinishedImageLoadingListener(glideRequestListener);
    }

    @Override
    protected void onDestroy() {//se sobreescriben las calases porque pertenecen al padre,en este caso(support.v7....)
        presenter.onDestroy(); //uso la clase propia del padre(ondestroy) para aplicarla a mi objeto
        super.onDestroy();
    }

    @Override//en el 4 video sobrecargo los metodos del menu
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflo el layout con el menu que cree en res/menu
        getMenuInflater().inflate(R.menu.menu_recipes_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();//asigno a id el item que fue seleccionado
        if(id == R.id.action_list){//si presiona el boton de la barra de item(3 puntso creo)
            navigateToListScreen();
        }else if (id == R.id.action_logout){
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FacebookRecipesApp app = (FacebookRecipesApp)getApplication();
        app.onLogout();
    }

    private void navigateToListScreen() {//inicio una actividad que llama a un nuevo intent(sin ninguna seleccion)
        startActivity(new Intent(this, RecipeListActivity.class));
    }

    private void setupInjection() {
        FacebookRecipesApp app = (FacebookRecipesApp)getApplication();
        component = app.getRecipeMainComponent(this, this);
        imageLoader = getImageLoader();
        presenter = getPresenter();
    }

    private RecipeMainPresenter getPresenter() {
        return component.getPresenter();
    }

    private ImageLoader getImageLoader() {
        return component.getImageLoader();
    }

    @Override//se sobreescriben xq pertenecen al implement
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showUIElements() {
        imgKeep.setVisibility(View.VISIBLE);
        imgDismiss.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideUIElements() {
        imgKeep.setVisibility(View.GONE);
        imgDismiss.setVisibility(View.GONE);
    }

    private void clearImage(){
        imgRecipe.setImageResource(0);//se deja en 0 para que no muestr nada
    }

    @Override
    public void saveAnimation() {
        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.save_animation);
        anim.setAnimationListener(getAnimationListener());
        imgRecipe.startAnimation(anim);
    }

    @Override
    public void dismissAnimation() {
        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.dismiss_animation);
        anim.setAnimationListener(getAnimationListener());
        imgRecipe.startAnimation(anim);
    }

    private Animation.AnimationListener getAnimationListener() {
        return new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                clearImage();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
    }

    @OnClick(R.id.imgKeep)//esta anotacion de btrknf me permite manejar el evento click
    @Override//se agrega para que reciba los gestos
    public void onKeep(){//le asigno este metodo al evento click
        if(currentRecipe != null){//valido que el objeto tipo RECIPE no este vacio, es decir que la consulta a la BD != null
            presenter.saveRecipe(currentRecipe);//si tiene contenido entonces lo guardo
        }
    }

    @OnClick(R.id.imgDismiss)
    @Override//se agrega para que reciba los gestos
    public void onDismiss(){
        presenter.dismissRecipe();
    }

    @Override
    public void onRecipeSaved(){//si se guarda correctamente muestro mesaje por medio del snackbar
        Snackbar.make(layoutContainer,R.string.recipemain_notice_saved,Snackbar.LENGTH_SHORT).show();
        //defino donde lo muesto , mensaje a mostrar , duracion . y le digo mostrar
    }

    @Override
    public void setRecipe(Recipe recipe) {
        this.currentRecipe = recipe;
        //por medio de la interface imgRecipe llamo al metodo load que me pide un imageView(Marco de la imagen que muestro)
        //y tambien me pide el recurso para mostrarla en este caso una URL, la cual obtengo de los atributos del objeto
        //recipe que guarda la consulta de la bd
        imageLoader.load(imgRecipe,recipe.getImageURL());//debo de tener un listener config(final video 3)
    }

    @Override
    public void onGetRecipeError(String error) {
        //como recipemain_error tiene %s para mostrar el error le debo dar formato
        String msgError = String.format(getString(R.string.recipemain_error), error);
        //le digo que el formato va a ser una cadena y le anexo el error para mostrarlo en un solo mensaje
        Snackbar.make(layoutContainer,msgError,Snackbar.LENGTH_SHORT).show();
    }
}
