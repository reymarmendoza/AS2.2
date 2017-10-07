package corp.kingsea.reymar.chat.contactlist;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import corp.kingsea.reymar.chat.contactlist.events.ContactListEvent;
import corp.kingsea.reymar.chat.domain.FirebaseHelper;
import corp.kingsea.reymar.chat.entities.User;
import corp.kingsea.reymar.chat.lib.EventBus;
import corp.kingsea.reymar.chat.lib.GreenRobotEventBus;

/**
 * Created by reyma on 25/06/2016.
 */
public class ContactListRepositoryImpl implements ContactListRepository {

    private EventBus eventBus;
    private FirebaseHelper helper;
    private ChildEventListener contactEventListener;

    public ContactListRepositoryImpl(){
        this.helper = FirebaseHelper.getInstance();
        this.eventBus = GreenRobotEventBus.getInstance();
    }

    @Override
    public void signOff() {
        helper.signOff();
    }

    @Override
    public String getCurrentUserEmail() {
        return helper.getAuthUserEmail();
    }

    @Override
    public void removeContact(String email) {
        String currentUserEmail = helper.getAuthUserEmail();
        helper.getOneContactReference(currentUserEmail, email).removeValue();
        helper.getOneContactReference(email, currentUserEmail).removeValue();
    }

    @Override
    public void subscribeToContactListEvents() {
        if(contactEventListener == null){
            contactEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    handleContact(dataSnapshot,ContactListEvent.onContactAdded);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    handleContact(dataSnapshot,ContactListEvent.onContactChanged);
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    handleContact(dataSnapshot,ContactListEvent.onContactRemoved);
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {}

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            };
        }
        helper.getMyContactsReference().removeEventListener(contactEventListener);
    }

    private void handleContact(DataSnapshot dataSnapshot, int type) {
        String email = dataSnapshot.getKey();
        email = email.replace("_",".");
        boolean online = ((Boolean)dataSnapshot.getValue()).booleanValue();
        User user = new User();
        user.setEmail(email);
        user.setOnline(online);
        post(type, user);
    }

    private void post(int type, User user) {
        ContactListEvent event = new ContactListEvent();
        event.setEventType(type);
        event.setUser(user);
        eventBus.post(event);
    }

    @Override
    public void unsubscribeToContactListEvents() {
        if(contactEventListener != null){
            helper.getMyContactsReference().removeEventListener(contactEventListener);
        }
    }

    @Override
    public void destroyListener() {
        contactEventListener = null;
    }

    @Override
    public void changeConnectionStatus(boolean online) {

    }
}
