package corp.kingsea.reymar.twittermyapp.images;

import org.greenrobot.eventbus.Subscribe;

import corp.kingsea.reymar.twittermyapp.images.events.ImagesEvent;
import corp.kingsea.reymar.twittermyapp.images.ui.ImagesView;
import corp.kingsea.reymar.twittermyapp.lib.base.EventBus;

/**
 * Created by reyma on 3/07/2016.
 */
public class ImagesPresenterImpl implements ImagesPresenter {

    private EventBus eventBus;
    private ImagesView view;
    private ImagesInteractor interactor;

    public ImagesPresenterImpl(ImagesView view, EventBus eventBus, ImagesInteractor interactor) {
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
    public void getImageTweets() {
        if(view != null){
            view.hideImages();
            view.showProgress();
        }
        interactor.execute();
    }

    @Override
    @Subscribe
    public void onEventMainThread(ImagesEvent event) {
        String errorMsg = event.getError();
        if(view != null){
            view.showImages();
            view.hideProgress();
            if(errorMsg != null){
                view.onError(errorMsg);
            }else{
                view.setContent(event.getImages());
            }
        }
    }
}
