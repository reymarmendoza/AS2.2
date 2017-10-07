package corp.kingsea.reymar.photofeed.login;

/**
 * Created by reyma on 18/06/2016.
 */
public interface LoginRepository {
    void signUp(String email, String password);
    void signIn(String email, String password);
}
