package corp.kingsea.reymar.facebookrecipes.api;

import java.util.List;

import corp.kingsea.reymar.facebookrecipes.entities.Recipe;

/**
 * Created by reyma on 5/07/2016.
 */
public class RecipeSearchResponse {
    private int count;
    private List<Recipe> recipes;

    public List<Recipe> getRecipes(){
        return recipes;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public Recipe getFirstRecipe(){//obtener la primera receta del listado
        Recipe first = null;
        if(!recipes.isEmpty()){
            first = recipes.get(0);
        }
        return first;//este metodo se llama a partir de la respuesta que recibe del servicio recipeservices
        //que esta construido a su vez por el cliente
    }
}
