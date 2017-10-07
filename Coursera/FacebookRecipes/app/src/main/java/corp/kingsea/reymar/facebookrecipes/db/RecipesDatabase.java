package corp.kingsea.reymar.facebookrecipes.db;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * esta clase me va a permitir config la base de datos que voy a ausar con DBFLOW(trabaja por encima de sqlite)
 **/
@Database(name = RecipesDatabase.NAME, version = RecipesDatabase.VERSION)
public class RecipesDatabase {
    public static final int VERSION = 1;
    public static final String NAME = "Recipes";
}
