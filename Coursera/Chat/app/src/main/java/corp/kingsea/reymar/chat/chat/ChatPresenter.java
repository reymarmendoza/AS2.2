package corp.kingsea.reymar.chat.chat;

import corp.kingsea.reymar.chat.chat.events.ChatEvent;

/**
 * Created by reyma on 26/06/2016.
 */
public interface ChatPresenter {
    void onPaused();
    void onResume();
    void onCreate();
    void onDestroy();

    void setChatRecipient(String recipient);
    void sendMessage(String msg);
    void onEventMainThread(ChatEvent event);
}
