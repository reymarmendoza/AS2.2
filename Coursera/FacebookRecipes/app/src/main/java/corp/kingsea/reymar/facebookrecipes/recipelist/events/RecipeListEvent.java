package corp.kingsea.reymar.facebookrecipes.recipelist.events;

import java.util.List;

import corp.kingsea.reymar.facebookrecipes.entities.Recipe;

/**
 * Created by reyma on 7/07/2016.
 */
public class RecipeListEvent {

    private int type;
    private List<Recipe> recipeList;

    public final static int READ_EVENT = 0;
    public final static int UPDATE_EVENT = 1;
    public final static int DELETE_EVENT = 2;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Recipe> getRecipeList() {
        return recipeList;
    }

    public void setRecipeList(List<Recipe> recipeList) {
        this.recipeList = recipeList;
    }
}
