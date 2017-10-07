package corp.kingsea.reymar.chat.chat;

import corp.kingsea.reymar.chat.chat.events.ChatRepository;

/**
 * Created by reyma on 26/06/2016.
 */
public class ChatSessionInteractorImpl implements ChatSessionInteractor {

    ChatRepository repository;

    public ChatSessionInteractorImpl(){
        this.repository = new ChatRepositoryImpl();
    }

    @Override
    public void changeConectionStatus(boolean online) {
        repository.changeConnectionStatus(online);
    }
}
