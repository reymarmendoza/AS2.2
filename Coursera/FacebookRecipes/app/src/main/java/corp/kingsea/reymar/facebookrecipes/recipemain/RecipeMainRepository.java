package corp.kingsea.reymar.facebookrecipes.recipemain;

import corp.kingsea.reymar.facebookrecipes.entities.Recipe;

/**
 * Created by reyma on 7/07/2016.
 */
public interface RecipeMainRepository {
    //estas variables se usan por la implementacion de api
    public final static int COUNT = 1;//cuantas recetas recibo
    public final static String RECENT_SORT = "r";//la forma en la que las recibo, supongo r= random
    public final static int RECIPE_RANGE = 100000;//el numero de recetas que hay

    void getNextRecipe();//reisar las mayusculas
    void saveRecipe(Recipe recipe);
    void setRecipePage(int recipePage);//genero un numero aleatorio para buscar una receta

}
