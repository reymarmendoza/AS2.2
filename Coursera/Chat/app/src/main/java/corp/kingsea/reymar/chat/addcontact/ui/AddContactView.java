package corp.kingsea.reymar.chat.addcontact.ui;

/**
 * Created by reyma on 25/06/2016.
 */
public interface AddContactView {
    void showInput();
    void hideInput();
    void showProgress();
    void hideProgress();

    void contactAdded();
    void contactNotAdded();
}
