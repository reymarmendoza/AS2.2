package edu.galileo.android.tipcalc.models;

import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Created by reyma on 6/06/2016.
 */
public class TipRecord {
    private double bill;
    private int tipPercentage;
    private Date timestamp;

    public double getBill() {
        return bill;
    }

    public void setBill(double bill) {
        this.bill = bill;
    }

    public int getTipPercentage() {
        return tipPercentage;
    }

    public void setTipPercentage(int tipPercentage) {
        this.tipPercentage = tipPercentage;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public double getTip(){
        return bill*(tipPercentage/100d);
    }

    public String getDateFormatted(){
        SimpleDateFormat simpleDataFormat = new SimpleDateFormat("MMM dd,yyyy HH:mm");
        return simpleDataFormat.format(timestamp);
    }
}
