package corp.kingsea.reymar.photofeed.domain;

import com.google.firebase.database.DatabaseError;

/**
 * Created by reyma on 8/07/2016.
 */
public interface FirebaseActionListenerCallback {

    void onSuccess();
    void onError(DatabaseError error);

}
