package corp.kingsea.reymar.chat.chat;

/**
 * Created by reyma on 26/06/2016.
 */
public interface ChatInteractor {
    void sendMessage(String msg);
    void setChatRecipient(String recipient);

    void subscribe();
    void unsubscribe();
    void destroyListener();
}
