package corp.kingsea.reymar.twittermyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import butterknife.Bind;
import butterknife.ButterKnife;
import corp.kingsea.reymar.twittermyapp.main.ui.MainActivity;


/*
en el gradle properties Consumer key,Consumer secret,Access token son las credenciales de acceso que son traidas de
la pagina de developers oAuthTool
 */
public class LoginActivity extends AppCompatActivity {
    @Bind(R.id.twitterLoginButton)
    TwitterLoginButton twitterLoginButton;//loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
    @Bind(R.id.container)
    RelativeLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        if (Twitter.getSessionManager().getActiveSession() != null) {
            navigateToMainScreen();
        }

        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                navigateToMainScreen();
            }

            @Override
            public void failure(TwitterException e) {
                String msgError = String.format(getString(R.string.login_error_message),
                        e.getLocalizedMessage());//en base a la ubicacion del usuario me dara un mensaje segun el idioma
                Snackbar.make(container, msgError, Snackbar.LENGTH_SHORT).show();//se envia una vista por lo que se usa el container
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        twitterLoginButton.onActivityResult(requestCode, resultCode, data);
    }

    private void navigateToMainScreen() {
        //evito que al oprimir back me lleve desde el activitymain al loginactivity
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
