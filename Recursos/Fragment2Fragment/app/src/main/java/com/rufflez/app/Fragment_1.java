package com.rufflez.app;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Fragment_1 extends Fragment implements View.OnClickListener {
    OnFragmentSendText mSendText;
    String send_text;
    EditText text;

    public Fragment_1(){
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //infla la vista y la capturo en v
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        //busco en v por el id, la capturo y lo casteo para guardarlo en la variable TEXT
        text = (EditText)v.findViewById(R.id.input);
        //se captura el view de tipo boton y se le asigna el escuchador
        Button b = (Button)v.findViewById(R.id.send);
        b.setOnClickListener(this);
        //una vez creada la vista padre devuelvo la esta inflada
        return v;
    }
    //despues de que se creo el fragmento
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mSendText = (OnFragmentSendText)context;
        } catch(ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement OnFragmentSendText");
        }
    }
    //captura los eventos click sobre la vista de acuerdo al nombre del view que recibe el evento ejecuto
    @Override
    public void onClick(View v) {
        //si el click en la vista (V.) tiene el id == ... ejecuta
        if (v.getId() == R.id.send) {
            send_text = text.getText().toString();
            mSendText.onSentText(send_text);
        }

    }

}
