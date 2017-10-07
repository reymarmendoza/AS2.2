package com.rufflez.app;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by andrew on 1/31/14.
 */
public class Fragment_2 extends Fragment{
    TextView text;
    String stringtext;
    //cuando se creo la vista padre, se infla el fragmento, por ultimo se asigna a la variable TEXT la vista  SENT_TEXT del fragmento
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_2, container, false);
        text = (TextView)v.findViewById(R.id.sent_text);
        return v;
    }

    public void setText(final String string){
        text.setText(string);
    }

    public void sentText(){
        new MyTask().execute();

    }

    private class MyTask extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {
            Bundle b = getArguments();
            stringtext = b.getString("text");
            return null;
        }

        protected void onPostExecute(String result){
            setText(stringtext);
        }
    }
}
