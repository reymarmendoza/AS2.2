package com.example.reyma.tipcalculator;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.etBillAmount)
    EditText etBillAmount;
    @BindView(R.id.tvTipPercent)
    TextView tvTipPercent;
    @BindView(R.id.tvTipTotal)
    TextView tvTipTotal;
    @BindView(R.id.tvBillTotalAmount)
    TextView tvBillTotalAmount;

    float percentage = 0;
    float tipTotal = 0;
    float finalBillAmount = 0;
    float totalBillAmount = 0;

    final float REGULAR_TIP_PERCENTAGE = 10;
    final float DEFAULT_TIP_PERCENTAGE = 15;
    final float EXCELLENT_TIP_PERCENTAGE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        /**
         * Si ya se creo una instancia(y se guardo) y esta se destruyo(rotacion de la pantalla, multitask, etc) aqui la puedo recuperar
         */
        if (savedInstanceState != null){
            float []recoveredState = savedInstanceState.getFloatArray("info");
            recoverTipValues(recoveredState);
        }else{
            setTipValues();
        }

    }

    private void recoverTipValues(float recoveredState[]) {

        tvTipPercent.setText(getString(R.string.main_msg_tipPercent, recoveredState[0]));
        tvTipTotal.setText(getString(R.string.main_msg_tipTotal, recoveredState[1]));
        tvBillTotalAmount.setText(getString(R.string.main_msg_billTotalResult, recoveredState[2]));

    }

    private void setTipValues() {

        tvTipPercent.setText(getString(R.string.main_msg_tipPercent, percentage));
        tvTipTotal.setText(getString(R.string.main_msg_tipTotal, tipTotal));
        tvBillTotalAmount.setText(getString(R.string.main_msg_billTotalResult, finalBillAmount));

    }

    @OnClick({R.id.ibRegularService, R.id.ibGoodService, R.id.ibExcellentService})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibRegularService:
                percentage = REGULAR_TIP_PERCENTAGE;
                break;
            case R.id.ibGoodService:
                percentage = DEFAULT_TIP_PERCENTAGE;
                break;
            case R.id.ibExcellentService:
                percentage = EXCELLENT_TIP_PERCENTAGE;
                break;
        }
        calculateFinalBill();
        setTipValues();
    }

    @OnTextChanged(R.id.etBillAmount)
    public void onTextChanged() {

        calculateFinalBill();
        setTipValues();

    }

    private void calculateFinalBill() {

        String billAmount = etBillAmount.getText().toString();

        if (percentage == 0){
            percentage = DEFAULT_TIP_PERCENTAGE;
        }
        if (!billAmount.equals("")){
            totalBillAmount = Float.valueOf(billAmount);
        }else{
            totalBillAmount = 0;
        }

        tipTotal = (totalBillAmount * percentage) / 100;
        finalBillAmount = totalBillAmount + tipTotal;

    }

    /**
     * Antes de destruirse la actividad con el onStop() del lifecycle al rotar o usar el multitask se invoca onSaveInstanceState.
     * Se puede guardar un bundle con la informacion de los parametros que quiero conservar y recuperarlos en el onCreate
     */

    @Override
    public void onSaveInstanceState(Bundle outState) {

        float []save = {percentage,tipTotal,finalBillAmount,totalBillAmount};
        outState.putFloatArray("info",save);
        super.onSaveInstanceState(outState);

    }

}
