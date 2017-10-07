package edu.galileo.android.tipcalc;

import android.app.Application;

import java.net.URL;

/**
 * Created by reyma on 5/06/2016.
 */
public class TipCalcApp extends Application{
    private final static String ABOUT_URL = "http://laopinion.com.co/";

    public String getAboutUrl(){
        return ABOUT_URL;
    }
}
