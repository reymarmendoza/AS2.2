package corp.kingsea.reymar.chat.addcontact;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import corp.kingsea.reymar.chat.addcontact.events.AddContactEvent;
import corp.kingsea.reymar.chat.domain.FirebaseHelper;
import corp.kingsea.reymar.chat.entities.User;
import corp.kingsea.reymar.chat.lib.EventBus;
import corp.kingsea.reymar.chat.lib.GreenRobotEventBus;

/**
 * Created by reyma on 25/06/2016.
 */
public class AddContactRepositoryImpl implements AddContactRepository {

    private EventBus eventBus;
    private FirebaseHelper helper;

    public AddContactRepositoryImpl(){
        this.helper = FirebaseHelper.getInstance();
        this.eventBus = GreenRobotEventBus.getInstance();
    }

    @Override
    public void addContact(final String email) {
        final String key = email.replace(".", "_");
        DatabaseReference userReference = helper.getUserReference(email);
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if(user != null){
                    DatabaseReference myContactReference = helper.getMyContactsReference();
                    myContactReference.child(key).setValue(user.isOnline());

                    String currentUserKey = helper.getAuthUserEmail();
                    currentUserKey = currentUserKey.replace(".", "_");
                    DatabaseReference reverseContactReference = helper.getContactsReference(email);
                    reverseContactReference.child(currentUserKey).setValue(User.ONLINE);
                    postSuccess();
                } else {
                    postError();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void postSuccess() {
        post(false);
    }

    private void postError() {
        post(true);
    }

    private void post(boolean error) {
        AddContactEvent event = new AddContactEvent();
        event.setError(error);
        eventBus.post(event);
    }
}
