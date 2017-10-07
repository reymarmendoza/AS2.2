package corp.kingsea.reymar.chat.chat.events;

import corp.kingsea.reymar.chat.entities.ChatMessage;

/**
 * Created by reyma on 26/06/2016.
 */
public class ChatEvent {
    ChatMessage message;

    public ChatMessage getMessage() {
        return message;
    }

    public void setMessage(ChatMessage message) {
        this.message = message;
    }
}
