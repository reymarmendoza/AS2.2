package corp.kingsea.reymar.chat.contactlist.events;

import corp.kingsea.reymar.chat.entities.User;

/**
 * Created by reyma on 24/06/2016.
 */
public class ContactListEvent {
    public final static int onContactAdded= 0;
    public final static int onContactChanged= 1;
    public final static int onContactRemoved= 2;

    private User user;
    private int eventType;

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
