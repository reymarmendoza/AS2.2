package corp.kingsea.reymar.twittermyapp.hashtags.events;

import java.util.List;

import corp.kingsea.reymar.twittermyapp.entities.Hashtag;

/**
 * Created by reyma on 4/07/2016.
 */
public class HashtagsEvent {
    private String error;
    private List<Hashtag> hashtags;

    public List<Hashtag> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<Hashtag> hashtags) {
        this.hashtags = hashtags;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }




}

