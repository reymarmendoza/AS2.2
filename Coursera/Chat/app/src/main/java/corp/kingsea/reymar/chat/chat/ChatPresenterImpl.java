package corp.kingsea.reymar.chat.chat;

import org.greenrobot.eventbus.Subscribe;

import corp.kingsea.reymar.chat.chat.events.ChatEvent;
import corp.kingsea.reymar.chat.chat.ui.ChatView;
import corp.kingsea.reymar.chat.entities.User;
import corp.kingsea.reymar.chat.lib.EventBus;
import corp.kingsea.reymar.chat.lib.GreenRobotEventBus;

/**
 * Created by reyma on 26/06/2016.
 */
public class ChatPresenterImpl implements ChatPresenter {

    private EventBus eventBus;
    private ChatView view;
    private ChatInteractor chatInteractor;
    private ChatSessionInteractor sessionInteractor;

    public ChatPresenterImpl(ChatView view){
        this.view = view;
        this.eventBus = GreenRobotEventBus.getInstance();
        this.chatInteractor = new ChatInteractorImpl();
        this.sessionInteractor = new ChatSessionInteractorImpl();
    }

    @Override
    public void onPaused() {
        chatInteractor.unsubscribe();
        sessionInteractor.changeConectionStatus(User.OFFLINE);
    }

    @Override
    public void onResume() {
        chatInteractor.unsubscribe();
        sessionInteractor.changeConectionStatus(User.ONLINE);    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        chatInteractor.destroyListener();
        view = null;
    }

    @Override
    public void setChatRecipient(String recipient) {
        chatInteractor.setChatRecipient(recipient );
    }

    @Override
    public void sendMessage(String msg) {
        chatInteractor.sendMessage(msg);
    }

    @Override
    @Subscribe
    public void onEventMainThread(ChatEvent event) {
        if(view != null){
            view.onMessageReceived(event.getMessage());
        }
    }
}
