package corp.kingsea.reymar.chat.login.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import corp.kingsea.reymar.chat.R;
import corp.kingsea.reymar.chat.contactlist.ui.ContactListActivity;
import corp.kingsea.reymar.chat.login.LoginPresenter;
import corp.kingsea.reymar.chat.login.LoginPresenterImpl;

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

    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        ButterKnife.bind(this);

        loginPresenter = new LoginPresenterImpl(this);
        loginPresenter.onCreate();
        loginPresenter.checkForAuthenticatedUser();
    }

    @Override
    protected void onDestroy(){
        loginPresenter.onDestroy();
        super.onDestroy();
    }
/*
    @Override
    protected void onResume() {
        loginPresenter.onResume();
        loginPresenter.checkForAuthenticatedUser();
        super.onResume();
    }

    @Override
    protected void onPause() {
        loginPresenter.onPause();
        super.onPause();
    }
*/
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
        /*AQUI CAMBIE ALGO*/
        loginPresenter.registerNewUser(inputEmail.getText().toString(), inputPassword.getText().toString());
    }

    @OnClick(R.id.SignIn)
    @Override
    public void handleSignIn() {
        loginPresenter.validateLogin(inputEmail.getText().toString(), inputPassword.getText().toString());
    }

    @Override
    public void navigateToMainScreen() {
        startActivity(new Intent(this, ContactListActivity.class));
        /*
        Intent intent = new Intent(this, ContactListActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        */
    }

    @Override
    public void loginError(String error) {
        inputPassword.setText("");
        String msgError = String.format(getString(R.string.login_error_message_signin), error);
        inputPassword.setError(msgError);
    }

    @Override
    public void newUserSuccess() {
        Snackbar.make(container, R.string.login_notice_message_signup,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void newUserError(String error) {
        inputPassword.setText("");
        String msgError = String.format(getString(R.string.login_error_message_signup), error);
        inputPassword.setError(msgError);
    }

    private void setInputs(boolean enabled){
        inputEmail.setEnabled(enabled);
        inputPassword.setEnabled(enabled);
        btnSignIn.setEnabled(enabled);
        btnSignUp.setEnabled(enabled);
    }
}