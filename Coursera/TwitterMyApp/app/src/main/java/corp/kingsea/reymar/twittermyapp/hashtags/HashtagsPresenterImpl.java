package corp.kingsea.reymar.twittermyapp.hashtags;

import org.greenrobot.eventbus.Subscribe;

import corp.kingsea.reymar.twittermyapp.hashtags.events.HashtagsEvent;
import corp.kingsea.reymar.twittermyapp.hashtags.ui.HashtagsView;
import corp.kingsea.reymar.twittermyapp.lib.base.EventBus;

/**
 * Created by reyma on 4/07/2016.
 */
public class HashtagsPresenterImpl implements HashtagsPresenter {

    private EventBus eventBus;
    private HashtagsView view;
    private HashtagsInteractor interactor;

    public HashtagsPresenterImpl(HashtagsView view, EventBus eventBus, HashtagsInteractor interactor) {
        this.eventBus = eventBus;
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void onResume() {
        eventBus.register(this);
    }

    @Override
    public void onPause() {
        eventBus.unregister(this);
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void getHashtagsTweets() {
        if(view != null){
            view.hideImages();
            view.showProgress();
        }
        interactor.execute();
    }

    @Override
    @Subscribe
    public void onEventMainThread(HashtagsEvent event) {
        String errorMsg = event.getError();
        if(view != null){
            view.showImages();
            view.hideProgress();
            if(errorMsg != null){
                view.onError(errorMsg);
            }else{
                view.setContent(event.getHashtags());
            }
        }
    }

}
