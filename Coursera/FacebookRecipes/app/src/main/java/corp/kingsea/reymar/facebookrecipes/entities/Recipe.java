package corp.kingsea.reymar.facebookrecipes.entities;

import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import corp.kingsea.reymar.facebookrecipes.db.RecipesDatabase;

/**
 * esta clase me va a mostrar una receta almacenada
 **/

//las anotaciones solo tienen sentido para el interprete cuando se indica que pertenece a una tabla
//indico que los datos de la clase que sigue a esta anotacion pertenecen a una tabla que esta
//alojada en una BD a la cual hara conexion la clase indicada

@Table(database = RecipesDatabase.class)
public class Recipe extends BaseModel{//basemodel pertenece a dbf para que genere metodos necesatios para el orm

    @SerializedName("recipe_id")//el nombre que tiene en la base de datos
    @PrimaryKey private String recipeId;//determino con la anotacion que esta variable sera la llave primaria

    @Column private String title;

    @SerializedName("image_url")
    @Column private String imageURL;

    @SerializedName("source_url")
    @Column private String sourceURL;

    @Column private boolean favorite;

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getSourceURL() {
        return sourceURL;
    }

    public void setSourceURL(String sourceURL) {
        this.sourceURL = sourceURL;
    }

    public boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public boolean equals(Object obj){
        boolean equal = false;
        if(obj instanceof Recipe){//si el objeto es una receta
            Recipe recipe = (Recipe)obj;//se convierte el obj a una receta
            //comparo si el identificador de la clase principal es igual al de mi objeto
            equal = this.recipeId.equals(recipe.getRecipeId());
        }//si la comparacion es true asigno el identificador  y es el que devuelvo de lo contratio es false
        return equal;
    }
}
