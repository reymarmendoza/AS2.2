package corp.kingsea.reymar.chat.chat.events;

/**
 * Created by reyma on 26/06/2016.
 */
public interface ChatRepository {
    void sendMessage(String msg);
    void setRecipient(String recipient);

    void subscribe();
    void unsubscribe();
    void destroyListener();
    void changeConnectionStatus(boolean online);
}
