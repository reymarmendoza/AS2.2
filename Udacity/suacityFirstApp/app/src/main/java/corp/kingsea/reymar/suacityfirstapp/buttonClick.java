package corp.kingsea.reymar.suacityfirstapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by reyma on 24/09/2016.
 **/

public class buttonClick {

    public class MainActivity extends AppCompatActivity {

        @BindView(R.id.buttonIncrement)
        Button buttonIncrement;
        @BindView(R.id.textNumber)
        TextView textNumber;
        @BindView(R.id.buttonDecrement)
        Button buttonDecrement;
        @BindView(R.id.textPriceNumber)
        TextView textPriceNumber;
        @BindView(R.id.buttonOrder)
        Button buttonOrder;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.button_click);
            ButterKnife.bind(this);
        }

        public void submitOrder(View view) {
            int numberOfCoffees = 2;
            /*
            display(numberOfCoffees);
            displayPrice(numberOfCoffees * 5);
            */
        }
/*
        private void display(int number) {
            TextView quantityTextView = textNumber;
            quantityTextView.setText("" + number);
        }

        private void displayPrice(int number) {
            TextView priceTextView = (TextView) findViewById(R.id.textPriceNumber);
            priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
        }
*/
        public void increase(View view) {
            int numberCoffees = Integer.parseInt(textNumber.getText().toString()) + 1;//capturo el contenido del TextView, lo paso a int y lo almaceno en la var.
            textNumber.setText(numberCoffees + 1);//actualizo el valor del TextView
            textPriceNumber.setText(numberCoffees * 5);//actualizo el valor del TextView
        }

        public void decrease(View view) {
            int numberCoffees = Integer.parseInt(textNumber.getText().toString()) + 1;//capturo el contenido del TextView, lo paso a int y lo almaceno en la var.
            textNumber.setText(numberCoffees + 1);//actualizo el valor del TextView
            textPriceNumber.setText(numberCoffees * 5);//actualizo el valor del TextView
        }
        //tengo que ordenar las inyecciones cuando vaya a usar los botones
        @OnClick({R.id.buttonIncrement, R.id.buttonDecrement, R.id.buttonOrder})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.buttonIncrement:
                    break;
                case R.id.buttonDecrement:
                    break;
                case R.id.buttonOrder:
                    break;
            }
        }
    }
}
