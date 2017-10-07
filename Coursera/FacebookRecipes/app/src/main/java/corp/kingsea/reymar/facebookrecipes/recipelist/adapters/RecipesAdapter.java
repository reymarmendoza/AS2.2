package corp.kingsea.reymar.facebookrecipes.recipelist.adapters;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.SendButton;
import com.facebook.share.widget.ShareButton;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import corp.kingsea.reymar.facebookrecipes.R;
import corp.kingsea.reymar.facebookrecipes.entities.Recipe;
import corp.kingsea.reymar.facebookrecipes.libs.base.ImageLoader;

/**
 * Created by reyma on 7/07/2016.
 */
public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder> {

    private List<Recipe> recipeList;//manejar dataset
    private ImageLoader imageLoader;//cargar las imagenes
    private OnItemClickListener onItemClickListener;

    public RecipesAdapter(List<Recipe> recipeList, ImageLoader imageLoader, OnItemClickListener onItemClickListener) {
        this.recipeList = recipeList;
        this.imageLoader = imageLoader;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.element_stored_recipes, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recipe currentRecipe = recipeList.get(position);
        imageLoader.load(holder.imgRecipe, currentRecipe.getImageURL());
        holder.txtRecipeName.setText(currentRecipe.getTitle());
        holder.imgFav.setTag(currentRecipe.getFavorite());//objeto asociado a un elemento, esto es para pruebas
        if(currentRecipe.getFavorite()){//si es un favorito cambio la estrella
            holder.imgFav.setImageResource(android.R.drawable.btn_star_big_on);
        }else{
            holder.imgFav.setImageResource(android.R.drawable.btn_star_big_off);
        }
        holder.setOnItemClickListener(currentRecipe, onItemClickListener);
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipeList = recipes;
        notifyDataSetChanged();
    }

    public void removeRecipe(Recipe recipe) {
        recipeList.remove(recipe);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.imgRecipe)
        ImageView imgRecipe;
        @Bind(R.id.txtRecipeName)
        TextView txtRecipeName;
        @Bind(R.id.imgFav)
        ImageButton imgFav;
        @Bind(R.id.imgDelete)
        ImageButton imgDelete;
        @Bind(R.id.fbShare)
        ShareButton fbShare;
        @Bind(R.id.fbSend)
        SendButton fbSend;

        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            ButterKnife.bind(this, view);
        }

        public void setOnItemClickListener(final Recipe currentRecipe, final OnItemClickListener onItemClickListener) {
            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    onItemClickListener.onItemClick(currentRecipe);
                }
            });
            imgFav.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    onItemClickListener.onFavClick(currentRecipe);
                }
            });
            imgDelete.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    onItemClickListener.onDeleteClick(currentRecipe);
                }
            });

            //contenido del btn de facebook
            ShareLinkContent content = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse(currentRecipe.getSourceURL()))
                    .build();
            fbShare.setShareContent(content);
            fbSend.setShareContent(content);
        }
    }

}
