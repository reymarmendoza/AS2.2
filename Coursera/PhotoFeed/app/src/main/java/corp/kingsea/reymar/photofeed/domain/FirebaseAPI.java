package corp.kingsea.reymar.photofeed.domain;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import corp.kingsea.reymar.photofeed.entities.Photo;

/**
 * se crean dos interfaces para tener mas control fireactliscall, son callbacks
 * voy a crear los objetos de firebase y los callbacks van a ser implementados x quien lo llama
 */
public class FirebaseAPIoriginal {

    private DatabaseReference firebase;
    private ChildEventListener photosEventListener;

    public FirebaseAPIoriginal(DatabaseReference firebase) {
        this.firebase = firebase;
    }
    //voy a trabajajar sobre photosEventListener, aun no tiene datos
    public void checkForData(final FirebaseActionListenerCallback listenerCallback){
        firebase.addListenerForSingleValueEvent(new ValueEventListener() {//me dice si hay algun resultado
            @Override//estos metodos se cargan automaticamente por la linea anterior, son los opciones de tener datos o no
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount() > 0){//valido si hay datos y llamo la interface FirebaseActionListenerCallback
                    listenerCallback.onSuccess();
                }else{
                    listenerCallback.onError(null);//se envia null porque no tengo error
                }
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
                listenerCallback.onError(firebaseError);//envio el error a la interface
            }
        });
    }

    public void subscribe(final FirebaseEventListenerCallback listenerCallback){
        if(photosEventListener == null){
            photosEventListener = new ChildEventListener() {
                @Override//aqui implemento la otra interface FirebaseEventListenerCallback
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    listenerCallback.onChildAdded(dataSnapshot);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    listenerCallback.onChildRemoved(dataSnapshot);
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(DatabaseError firebaseError) {
                    listenerCallback.onCancelled(firebaseError);
                }
            };
            firebase.addChildEventListener(photosEventListener);
        }
    }
    public void unsubscribe(){
        if(photosEventListener != null){
            firebase.removeEventListener((photosEventListener));
        }
    }
    /*
    para crear una foto en firebase primero creo el key y luego lo actualizo con la foto como tal.
    cuando guarde las fotos voy a necesitar algunas opciones, teniendola ya guardada voy a necesitar metodos para
    eliminar, actualiza, etc
     */
    public String create(){
        return firebase.push().getKey();//obtengo el key de firebase para sincronizar con cloudinary
    }
    public void update(Photo photo){
        this.firebase.child(photo.getId()).setValue(photo);
        //hasta aqui <-)  obtengo la referencia de la foto
    }
    //cuando elimino la foto, recibo la foto y
    public void remove(Photo photo, FirebaseActionListenerCallback listenerCallback){
        this.firebase.child(photo.getId()).removeValue();//removevalue es de firebase
        listenerCallback.onSuccess();
    }
    /*
    para crear una foto en firebase primero creo el key y luego lo actualizo con la foto como tal
    */
    //manejo de la sesion
    public String getAuthEmail(){
        String email = null;
        if(FirebaseAuth.getInstance() != null){//valido si tengo una sesion existe en fb
            Map<String, Object> providerData = firebase.getAuth().getProviderData();//si es asi cargo la informacion proporcionada en el
            email = providerData.get("email").toString();
        }
        return email;
    }
    public void logout(){
        FirebaseAuth.getInstance().signOut();//cerrar sesion
    }

    public void login(String email, String password, final FirebaseActionListenerCallback listenerCallback){

        //el tercer parametro es para realizar alguna accion bien sea por inicio ok o no
        firebase.authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                listenerCallback.onSuccess();
            }

            @Override
            public void onAuthenticationError(DatabaseError firebaseError) {
                listenerCallback.onError(firebaseError);
            }
        });
    }

    public void signup(String email, String password, final FirebaseActionListenerCallback listenerCallback){
        //el tercer parametro es para realizar alguna accion bien sea por inicio ok o no
        firebase.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>(){
            @Override
            public void onSuccess(Map<String, Object> O) {
                listenerCallback.onSuccess();
            }

            @Override
            public void onError(DatabaseError firebaseError) {
                listenerCallback.onError(firebaseError);
            }
        });
    }

    public void checkForSession(FirebaseActionListenerCallback listenerCallback){
        if(FirebaseAuth.getInstance() != null){//si es diferente de null quiere decir que tengo una sesion
            listenerCallback.onSuccess();
        }else{
            listenerCallback.onError(null);
        }
    }
}
