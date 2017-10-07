package corp.kingsea.reymar.chat.login;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import corp.kingsea.reymar.chat.domain.FirebaseHelper;
import corp.kingsea.reymar.chat.entities.User;
import corp.kingsea.reymar.chat.lib.EventBus;
import corp.kingsea.reymar.chat.lib.GreenRobotEventBus;
import corp.kingsea.reymar.chat.login.events.LoginEvent;

public class LoginRepositoryImpl implements LoginRepository {

    private final FirebaseAuth authReference;//creo un atributo para autenticarme
    private FirebaseHelper helper;
    private DatabaseReference myUserReference;

    public LoginRepositoryImpl() {
        this.helper = FirebaseHelper.getInstance();
        this.myUserReference = helper.getMyUserReference();
        this.authReference = FirebaseAuth.getInstance();//lo instancio para llenarlo
    }

    @Override
    public void signUp(final String email, final String password) {
        authReference.createUserWithEmailAndPassword(email, password).addOnSuccessListener
                new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
            postEvent(LoginEvent.onSignUpSuccess);
            signIn(email, password);
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            postEvent(LoginEvent.onSignUpError, e.getMessage());
            }
        });
    }

    @Override
    public void signIn(String email, String password) {
        authReference.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                initSignIn();
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                postEvent(LoginEvent.onSignInError, e.getMessage());
            }
        });
    }

    @Override
    public void checkSession() {
        if(authReference.getCurrentUser() != null){
            initSignIn();
        } else {
            postEvent(LoginEvent.onFailedToRecoverSession);
        }
    }

    private void initSignIn(){
        myUserReference = helper.getMyUserReference();
        myUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User currentUser = dataSnapshot.getValue(User.class);

                if(currentUser == null){
                    registerNewUser();
                }
                helper.changeUserConnectionStatus(User.ONLINE);
                postEvent(LoginEvent.onSignInSuccess);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    private void registerNewUser(){
        String email = helper.getAuthUserEmail();
        if(email != null){
            User currentUser = new User();
            currentUser.setEmail(email);
            myUserReference.setValue(currentUser);
        }
    }

    private void postEvent(int type, String errorMessage){
        LoginEvent loginEvent = new LoginEvent();
        loginEvent.setEventType(type);
        if(errorMessage != null){
            loginEvent.setErrorMessage(errorMessage);
        }
        EventBus eventBus = GreenRobotEventBus.getInstance();
        eventBus.post(loginEvent);
    }

    private void postEvent(int type) {
        postEvent(type, null);
    }
}