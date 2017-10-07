package corp.kingsea.reymar.twittermyapp;

import android.app.Application;
import android.support.v4.app.Fragment;

import com.crashlytics.android.Crashlytics;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import corp.kingsea.reymar.twittermyapp.hashtags.di.DaggerHashtagsComponent;
import corp.kingsea.reymar.twittermyapp.hashtags.di.HashtagsComponent;
import corp.kingsea.reymar.twittermyapp.hashtags.di.HashtagsModule;
import corp.kingsea.reymar.twittermyapp.hashtags.ui.HashtagsView;
import corp.kingsea.reymar.twittermyapp.images.di.DaggerImagesComponent;
import corp.kingsea.reymar.twittermyapp.images.di.ImagesComponent;
import corp.kingsea.reymar.twittermyapp.images.di.ImagesModule;
import corp.kingsea.reymar.twittermyapp.images.ui.ImagesView;
import corp.kingsea.reymar.twittermyapp.lib.di.LibsModule;
import io.fabric.sdk.android.Fabric;

public class TwitterClientApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initFabric();
    }

    private void initFabric() {
        TwitterAuthConfig authConfig = new TwitterAuthConfig(BuildConfig.TWITTER_KEY,BuildConfig.TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig), new Crashlytics());
    }

    public ImagesComponent getImagesComponent(Fragment fragment, ImagesView view, corp.kingsea.reymar.twittermyapp.images.ui.adapters.OnItemClickListener clickListener){
        return DaggerImagesComponent
                .builder()
                .libsModule(new LibsModule(fragment))
                .imagesModule(new ImagesModule(view, clickListener))
                .build();
    }

    public HashtagsComponent getHashtagsComponent(HashtagsView view, corp.kingsea.reymar.twittermyapp.hashtags.ui.adapters.OnItemClickListener clickListener){
        return DaggerHashtagsComponent
                .builder()
                .libsModule(new LibsModule(null))
                .hashtagsModule(new HashtagsModule(view, clickListener))
                .build();
    }
}
