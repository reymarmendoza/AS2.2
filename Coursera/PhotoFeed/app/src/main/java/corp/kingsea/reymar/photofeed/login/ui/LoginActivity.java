package corp.kingsea.reymar.photofeed.login.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import corp.kingsea.reymar.photofeed.main.ui.MainActivity;
import corp.kingsea.reymar.photofeed.PhotoFeedApp;
import corp.kingsea.reymar.photofeed.R;
import corp.kingsea.reymar.photofeed.login.LoginPresenter;

public class LoginActivity extends AppCompatActivity implements LoginView {

    @Bind(R.id.inputEmail)
    EditText inputEmail;
    @Bind(R.id.inputPass)
    EditText inputPassword;
    @Bind(R.id.SignIn)
    Button btnSignIn;
    @Bind(R.id.SignUp)
    Button btnSignUp;
    @Bind(R.id.ProgressBar)
    ProgressBar progressBar;
    @Bind(R.id.LayoutMainContainer)
    RelativeLayout container;

    @Inject
    LoginPresenter loginPresenter;
    @Inject
    SharedPreferences sharedPreferences;
    private PhotoFeedApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        app = (PhotoFeedApp)getApplication();//Return the application that owns this activity.
        setupInjection();
        loginPresenter.onCreate();
        loginPresenter.validateLogin(null, null);
    }

    private void setupInjection() {
        app.getLoginComponent(this).inject(this);
                            //envio la vista
    }

    @Override
    protected void onDestroy(){
        loginPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void enabledInputs() {
        setInputs(true);
        hideProgress();
    }

    @Override
    public void disableInputs() {
        setInputs(false);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @OnClick(R.id.SignUp)
    @Override
    public void handleSignUp() {
        loginPresenter.registerNewUser(inputEmail.getText().toString(),
                inputPassword.getText().toString());
    }

    @OnClick(R.id.SignIn)
    @Override
    public void handleSignIn() {
        loginPresenter.validateLogin(inputEmail.getText().toString(),
                inputPassword.getText().toString());
    }

    @Override
    public void navigateToMainScreen() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void loginError(String error) {
        inputPassword.setText("");
        String msgError = String.format(getString(R.string.login_error_message_signin), error);
        inputPassword.setError(msgError);
    }

    @Override
    public void newUserSuccess() {
        Snackbar.make(container, R.string.login_notice_message_signup, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void newUserError(String error) {
        inputPassword.setText("");
        String msgError = String.format(getString(R.string.login_error_message_signup), error);
        inputPassword.setError(msgError);
    }

    @Override
    public void setUserEmail(String email){
        if(email != null){
            sharedPreferences.edit().putString(app.getEmailKey(), email).commit();
        }
    }

    private void setInputs(boolean enabled){
        inputEmail.setEnabled(enabled);
        inputPassword.setEnabled(enabled);
        btnSignIn.setEnabled(enabled);
        btnSignUp.setEnabled(enabled);
    }

}