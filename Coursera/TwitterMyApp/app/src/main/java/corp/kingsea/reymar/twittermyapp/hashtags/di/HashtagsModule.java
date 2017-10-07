package corp.kingsea.reymar.twittermyapp.hashtags.di;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterSession;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import corp.kingsea.reymar.twittermyapp.api.CustomTwitterApiClient;
import corp.kingsea.reymar.twittermyapp.entities.Hashtag;
import corp.kingsea.reymar.twittermyapp.hashtags.HashtagsInteractor;
import corp.kingsea.reymar.twittermyapp.hashtags.HashtagsInteractorImpl;
import corp.kingsea.reymar.twittermyapp.hashtags.HashtagsPresenter;
import corp.kingsea.reymar.twittermyapp.hashtags.HashtagsPresenterImpl;
import corp.kingsea.reymar.twittermyapp.hashtags.HashtagsRepository;
import corp.kingsea.reymar.twittermyapp.hashtags.HashtagsRepositoryImpl;
import corp.kingsea.reymar.twittermyapp.hashtags.ui.HashtagsView;
import corp.kingsea.reymar.twittermyapp.hashtags.ui.adapters.OnItemClickListener;
import corp.kingsea.reymar.twittermyapp.hashtags.ui.adapters.HashtagsAdapter;
import corp.kingsea.reymar.twittermyapp.lib.base.EventBus;

@Module
public class HashtagsModule {
    private HashtagsView view;
    private OnItemClickListener clickListener;

    public HashtagsModule(HashtagsView view, OnItemClickListener clickListener) {
        this.view = view;
        this.clickListener = clickListener;
    }

    @Provides
    @Singleton
    HashtagsAdapter provideAdapter(List<Hashtag> dataset, OnItemClickListener clickListener) {
        return new HashtagsAdapter(dataset, clickListener);
    }

    @Provides
    @Singleton
    OnItemClickListener provideClickListener() {
        return this.clickListener;
    }

    @Provides
    @Singleton
    List<Hashtag> provideItems() {
        return new ArrayList<Hashtag>();
    }

    @Provides
    @Singleton
    HashtagsPresenter provideHashtagsPresenter(HashtagsView view, HashtagsInteractor interactor, EventBus eventBus) {
        return new HashtagsPresenterImpl(view, eventBus, interactor);
    }

    @Provides
    @Singleton
    HashtagsView provideHashtagsView() {
        return this.view;
    }

    @Provides
    @Singleton
    HashtagsInteractor provideHashtagsInteractor(HashtagsRepository repository) {
        return new HashtagsInteractorImpl(repository);
    }

    @Provides
    @Singleton
    HashtagsRepository provideHashtagsRepository(CustomTwitterApiClient client, EventBus eventBus) {
        return new HashtagsRepositoryImpl(eventBus, client);
    }

    @Provides
    @Singleton
    CustomTwitterApiClient provideTwitterApiClient(TwitterSession session) {
        return new CustomTwitterApiClient(session);
    }

    @Provides
    @Singleton
    TwitterSession provideTwitterSession() {
        return Twitter.getSessionManager().getActiveSession();
    }
}