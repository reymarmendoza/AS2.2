package corp.kingsea.reymar.facebookrecipes.recipelist.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import corp.kingsea.reymar.facebookrecipes.FacebookRecipesApp;
import corp.kingsea.reymar.facebookrecipes.R;
import corp.kingsea.reymar.facebookrecipes.entities.Recipe;
import corp.kingsea.reymar.facebookrecipes.recipelist.RecipeListPresenter;
import corp.kingsea.reymar.facebookrecipes.recipelist.adapters.OnItemClickListener;
import corp.kingsea.reymar.facebookrecipes.recipelist.adapters.RecipesAdapter;
import corp.kingsea.reymar.facebookrecipes.recipelist.di.RecipeListComponent;
import corp.kingsea.reymar.facebookrecipes.recipemain.ui.RecipeMainActivity;

public class RecipeListActivity extends AppCompatActivity implements RecipeListView, OnItemClickListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private RecipesAdapter adapter;
    private RecipeListPresenter presenter;
    private RecipeListComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        ButterKnife.bind(this);
        setupToolbar();
        setupInjection();
        setupRecyclerView();
        presenter.onCreate();
        presenter.getRecipes();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recipes_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();//asigno a id el item que fue seleccionado
        if(id == R.id.action_main){//si presiona el boton de la barra de item(3 puntso creo)
            navigateToMainScreen();
        }else if (id == R.id.action_logout){
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FacebookRecipesApp app = (FacebookRecipesApp)getApplication();
        app.onLogout();
    }

    private void navigateToMainScreen() {//inicio una actividad que llama a un nuevo intent(sin ninguna seleccion)
        startActivity(new Intent(this, RecipeMainActivity.class));
    }

    private void setupInjection() {
        FacebookRecipesApp app = (FacebookRecipesApp) getApplication();
        component = app.getRecipeListComponent(this, this, this);
        presenter = getPresenter();
        adapter = getAdapter();
    }

    private RecipesAdapter getAdapter() {
        return component.getAdapter();
    }

    private RecipeListPresenter getPresenter() {
        return component.getPresenter();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));//quiero dos columnas
        recyclerView.setAdapter(adapter);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
    }

    @OnClick(R.id.toolbar)
    public void onToolbarClick(){
        recyclerView.smoothScrollToPosition(0);//este en lo mas alto en el momento en el que se hace click
    }

    @Override
    public void setRecipes(List<Recipe> data) {
        adapter.setRecipes(data);
    }

    @Override
    public void recipeUpdated() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void recipeDeleted(Recipe recipe) {
        adapter.removeRecipe(recipe);
    }

    @Override
    public void onFavClick(Recipe recipe) {
        presenter.toggleFavorite(recipe);
    }

    @Override
    public void onItemClick(Recipe recipe) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(recipe.getSourceURL()));
        startActivity(intent);
    }

    @Override
    public void onDeleteClick(Recipe recipe) {
        presenter.removeRecipe(recipe);
    }
}
