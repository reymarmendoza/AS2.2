package corp.kingsea.reymar.twittermyapp.images.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import corp.kingsea.reymar.twittermyapp.R;
import corp.kingsea.reymar.twittermyapp.entities.Image;
import corp.kingsea.reymar.twittermyapp.lib.base.ImageLoader;

/**
 * Created by reyma on 3/07/2016.
 */
public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder> {

    private List<Image> dataset;
    private ImageLoader imageloader;
    private OnItemClickListener clickListener;

    public ImagesAdapter(List<Image> dataset, ImageLoader imageloader, OnItemClickListener clickListener) {
        this.dataset = dataset;
        this.imageloader = imageloader;
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_images, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Image imageTweet = dataset.get(position);
        holder.setOnClickListener(imageTweet, clickListener);
        holder.txtTweet.setText(imageTweet.getTweetText());
        imageloader.load(holder.imgMedia, imageTweet.getImageURL());
    }

    public void setItems(List<Image> newItems){
        dataset.addAll(newItems);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.imgMedia)
        ImageView imgMedia;
        @Bind(R.id.txtTweet)
        TextView txtTweet;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.view = itemView;
        }

        public void setOnClickListener(final Image image, final OnItemClickListener listener) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(image);
                }
            });
        }
    }
}
