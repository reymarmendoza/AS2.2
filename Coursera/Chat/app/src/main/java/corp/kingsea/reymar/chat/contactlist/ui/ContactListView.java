package corp.kingsea.reymar.chat.contactlist.ui;

import corp.kingsea.reymar.chat.entities.User;

/**
 * Created by reyma on 24/06/2016.
 */
public interface ContactListView {
    void onContactAdded(User user);
    void onContactChanged(User user);
    void onContactRemoved(User user);
}
