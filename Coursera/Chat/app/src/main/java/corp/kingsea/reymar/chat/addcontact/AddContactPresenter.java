package corp.kingsea.reymar.chat.addcontact;

import corp.kingsea.reymar.chat.addcontact.events.AddContactEvent;

/**
 * Created by reyma on 25/06/2016.
 */
public interface AddContactPresenter {
    void onShow();
    void onDestroy();

    void addContact(String email);
    void onEventMainThread(AddContactEvent event);
}
