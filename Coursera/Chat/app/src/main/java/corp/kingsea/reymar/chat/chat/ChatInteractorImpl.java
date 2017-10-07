package corp.kingsea.reymar.chat.chat;

import corp.kingsea.reymar.chat.chat.events.ChatRepository;

/**
 * Created by reyma on 26/06/2016.
 */
public class ChatInteractorImpl implements ChatInteractor {

    ChatRepository repository;

    public ChatInteractorImpl(){
        this.repository = new ChatRepositoryImpl();
    }

    @Override
    public void sendMessage(String msg) {
        repository.sendMessage(msg);
    }

    @Override
    public void setChatRecipient(String recipient) {
        repository.setRecipient(recipient);
    }

    @Override
    public void subscribe() {
        repository.subscribe();
    }

    @Override
    public void unsubscribe() {
        repository.unsubscribe();
    }

    @Override
    public void destroyListener() {
        repository.destroyListener();
    }
}
