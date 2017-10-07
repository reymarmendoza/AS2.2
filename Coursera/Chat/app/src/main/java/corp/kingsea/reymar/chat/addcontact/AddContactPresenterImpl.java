package corp.kingsea.reymar.chat.addcontact;

import corp.kingsea.reymar.chat.addcontact.events.AddContactEvent;
import corp.kingsea.reymar.chat.addcontact.ui.AddContactView;
import corp.kingsea.reymar.chat.lib.EventBus;
import corp.kingsea.reymar.chat.lib.GreenRobotEventBus;

/**
 * Created by reyma on 25/06/2016.
 */
public class AddContactPresenterImpl implements AddContactPresenter {

    private EventBus eventBus;
    private AddContactView view;
    private AddContactInteractor interactor;

    public AddContactPresenterImpl(AddContactView view) {
        this.view = view;
        this.eventBus = GreenRobotEventBus.getInstance();
        this.interactor = new AddContactInteractorImpl();
    }

    @Override
    public void onShow() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        view = null;
            eventBus.unregister(this);
    }

    @Override
    public void addContact(String email) {
        if(view != null){
            view.hideInput();
            view.showProgress();
        }
        interactor.execute(email);
    }

    @Override
    public void onEventMainThread(AddContactEvent event) {
        if(view != null){
            view.hideProgress();
            view.showInput();
            if(event.isError()){
                view.contactNotAdded();
            }else{
                view.contactAdded();
            }
        }
    }
}
