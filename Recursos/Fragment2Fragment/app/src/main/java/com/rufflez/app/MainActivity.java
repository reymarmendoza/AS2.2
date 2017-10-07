package com.rufflez.app;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements OnFragmentSendText {

    String sent_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new Fragment_1())
                        .commit();
            }
        } else {
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new Fragment_1())
                        .commit();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container2, new Fragment_2(), "fragment2")
                        .commit();
            }
        }

    }

    @Override
    public void onSentText(String text) {
        Fragment_2 fragment_2 = (Fragment_2) getSupportFragmentManager().findFragmentByTag("fragment2");
        if (fragment_2 != null) {
            fragment_2.setText(text);//todo lo que hace es llmara a un etodo que escribe en el fragment crl + b
        } else {
            Fragment_2 fragment = new Fragment_2();
            Bundle args = new Bundle();
            args.putString("text", text);
            fragment.setArguments(args);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .addToBackStack(null).commit();
            fragment.sentText();
        }

    }
}