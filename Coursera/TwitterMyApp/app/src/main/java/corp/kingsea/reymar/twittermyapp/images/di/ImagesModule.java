package corp.kingsea.reymar.twittermyapp.images.di;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Session;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import corp.kingsea.reymar.twittermyapp.api.CustomTwitterApiClient;
import corp.kingsea.reymar.twittermyapp.entities.Image;
import corp.kingsea.reymar.twittermyapp.images.ImagesInteractor;
import corp.kingsea.reymar.twittermyapp.images.ImagesInteractorImpl;
import corp.kingsea.reymar.twittermyapp.images.ImagesPresenter;
import corp.kingsea.reymar.twittermyapp.images.ImagesPresenterImpl;
import corp.kingsea.reymar.twittermyapp.images.ImagesRepository;
import corp.kingsea.reymar.twittermyapp.images.ImagesRepositoryImpl;
import corp.kingsea.reymar.twittermyapp.images.ui.ImagesView;
import corp.kingsea.reymar.twittermyapp.images.ui.adapters.ImagesAdapter;
import corp.kingsea.reymar.twittermyapp.images.ui.adapters.OnItemClickListener;
import corp.kingsea.reymar.twittermyapp.lib.base.EventBus;
import corp.kingsea.reymar.twittermyapp.lib.base.ImageLoader;
import dagger.Module;
import dagger.Provides;

/**
 * Created by reyma on 3/07/2016.
 **/

@Module
public class ImagesModule {
    private ImagesView view;
    private OnItemClickListener clickListener;

    public ImagesModule(ImagesView view, OnItemClickListener clickListener) {
        this.view = view;
        this.clickListener = clickListener;
    }

    @Provides
    @Singleton
    ImagesAdapter providesAdapter(List<Image> dataset, ImageLoader imageloader, OnItemClickListener clickListener){
        return new ImagesAdapter(dataset, imageloader, clickListener);
    }

    @Provides
    @Singleton
    OnItemClickListener providesOnItemClickListener(){
        return this.clickListener;
    }

    @Provides
    @Singleton
    List<Image> providesItemList(){
        return new ArrayList<Image>();
    }

    @Provides
    @Singleton
    ImagesPresenter providesImagesPresenter(ImagesView view, EventBus eventBus, ImagesInteractor interactor){
        return new ImagesPresenterImpl(view, eventBus, interactor);
    }

    @Provides
    @Singleton
    ImagesView providesImagesView(){
        return this.view;
    }

    @Provides
    @Singleton
    ImagesInteractor providesImagesInteractor(ImagesRepository repository){
        return new ImagesInteractorImpl(repository);
    }

    @Provides
    @Singleton
    ImagesRepository providesImagesRepository(EventBus eventBus, CustomTwitterApiClient client){
        return new ImagesRepositoryImpl(eventBus, client);
    }

    @Provides
    @Singleton
    CustomTwitterApiClient providesCustomTwitterApiClient(Session session){
        return new CustomTwitterApiClient(session);
    }

    @Provides
    @Singleton
    Session providesTwitter(){
        return Twitter.getSessionManager().getActiveSession();
    }
}
