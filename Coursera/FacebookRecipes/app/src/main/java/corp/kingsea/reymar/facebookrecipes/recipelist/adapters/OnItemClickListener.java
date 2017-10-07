package corp.kingsea.reymar.facebookrecipes.recipelist.adapters;

import corp.kingsea.reymar.facebookrecipes.entities.Recipe;

/**
 * Created by reyma on 7/07/2016.
 */
public interface OnItemClickListener {

    void onFavClick(Recipe recipe);//favorito
    void onItemClick(Recipe recipe);//imagen
    void onDeleteClick(Recipe recipe);//eliminar
}
