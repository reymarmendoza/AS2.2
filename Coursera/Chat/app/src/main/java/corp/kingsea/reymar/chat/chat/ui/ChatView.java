package corp.kingsea.reymar.chat.chat.ui;

import corp.kingsea.reymar.chat.entities.ChatMessage;

/**
 * Created by reyma on 26/06/2016.
 */
public interface ChatView {
    void onMessageReceived(ChatMessage msg);
}
