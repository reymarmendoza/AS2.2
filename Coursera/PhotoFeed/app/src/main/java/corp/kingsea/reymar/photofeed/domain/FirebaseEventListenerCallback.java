package corp.kingsea.reymar.photofeed.domain;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

/**
 * Created by reyma on 8/07/2016.
 */
public interface FirebaseEventListenerCallback {

    void onChildAdded(DataSnapshot snapshot);
    void onChildRemoved(DataSnapshot snapshot);
    void onCancelled(DatabaseError error);

}
