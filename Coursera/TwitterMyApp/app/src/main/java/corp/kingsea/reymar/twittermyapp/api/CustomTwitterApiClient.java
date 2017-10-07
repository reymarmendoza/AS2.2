package corp.kingsea.reymar.twittermyapp.api;

import com.twitter.sdk.android.core.Session;
import com.twitter.sdk.android.core.TwitterApiClient;

/**
 * Created by reyma on 2/07/2016.
 */
public class CustomTwitterApiClient extends TwitterApiClient {
    public CustomTwitterApiClient(Session session) {
        super(session);
    }
    public TimelineService getTimeLineService(){
        return getService(TimelineService.class);
    }
}
