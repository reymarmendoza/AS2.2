package corp.kingsea.reymar.twittermyapp.hashtags;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.HashtagEntity;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import corp.kingsea.reymar.twittermyapp.api.CustomTwitterApiClient;
import corp.kingsea.reymar.twittermyapp.entities.Hashtag;
import corp.kingsea.reymar.twittermyapp.hashtags.events.HashtagsEvent;
import corp.kingsea.reymar.twittermyapp.lib.base.EventBus;

/**
 * Created by reyma on 4/07/2016.
 */
public class HashtagsRepositoryImpl implements HashtagsRepository {

    private EventBus eventBus ;
    private CustomTwitterApiClient client;
    private final static int TWEET_COUNT = 100;

    public HashtagsRepositoryImpl(EventBus eventBus, CustomTwitterApiClient client) {
        this.eventBus = eventBus;
        this.client = client;
    }

    @Override
    public void getHashtags() {
        Callback<List<Tweet>> callback = new Callback<List<Tweet>>() {
            @Override
            public void success(Result<List<Tweet>> result) {
                List<Hashtag> items = new ArrayList<Hashtag>();
                for(Tweet tweet : result.data){
                    if(containsHashtags(tweet)){
                        Hashtag tweetModel = new Hashtag();

                        tweetModel.setId(tweet.idStr);
                        tweetModel.setFavoriteCount(tweet.favoriteCount);
                        tweetModel.setTweetText(tweet.text);

                        List<String> hashtags = new ArrayList<String>();
                        for(HashtagEntity hashtag : tweet.entities.hashtags){
                            hashtags.add(hashtag.text);
                        }
                        tweetModel.setHashtags(hashtags);

                        items.add(tweetModel);
                    }
                }
                Collections.sort(items, new Comparator<Hashtag>() {
                    @Override
                    public int compare(Hashtag hashtag, Hashtag t1) {
                        return t1.getFavoriteCount() -hashtag.getFavoriteCount();
                    }
                });
                post(items);
            }

            @Override
            public void failure(TwitterException exception) {
                post(exception.getLocalizedMessage());
            }
        };
        client.getTimeLineService().homeTimeline(TWEET_COUNT, true, true, true, true, callback);
    }

    private boolean containsHashtags(Tweet tweet){
        return tweet.entities != null &&
                tweet.entities.hashtags != null &&
                !tweet.entities.hashtags.isEmpty();
    }

    private void post(List<Hashtag> items){
        post(items, null);
    }

    private void post(String error){
        post(null, error);
    }

    private void post(List<Hashtag> items, String error){
        HashtagsEvent event = new HashtagsEvent();
        event.setError(error);
        event.setHashtags(items);
        eventBus.post(event);
    }
}