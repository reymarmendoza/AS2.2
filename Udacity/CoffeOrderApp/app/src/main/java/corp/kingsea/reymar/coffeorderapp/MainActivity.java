package corp.kingsea.reymar.coffeorderapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.textName)
    EditText textName;
    @BindView(R.id.textNumber)
    TextView textNumber;
    @BindView(R.id.textPriceNumber)
    TextView textPriceNumber;
    @BindView(R.id.parentLayout)
    LinearLayout parentLayout;//le asigne una variable al layout para poder usar el snackbar, ya que un parametro pide que se asigne el layout donde aparecera
    @BindView(R.id.checkBoxWhippedCream)
    CheckBox checkBoxWhippedCream;
    @BindView(R.id.checkboxChocolate)
    CheckBox checkboxChocolate;
    @BindView(R.id.buttonSend)
    Button buttonSend;

    int cofeeValue = 5;// valor del cafe para un mantenimiento mas sencillo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.buttonIncrement)
    public void increase() {
        captureNumberCoffees(1);
    }

    @OnClick(R.id.buttonDecrement)
    public void decrease() {
        captureNumberCoffees(-1);
    }

    @OnClick(R.id.buttonOrder)
    public void submitOrder() {//le doy formato de moneda al entero capturado del TextView * el valor del cafe NumberFormat.getCurrencyInstance().format(Integer.parseInt(textNumber.getText().toString())
        textPriceNumber.setText(messageBill(validateAds(checkBoxWhippedCream), validateAds(checkboxChocolate)));
    }
    //intents http://www.vogella.com/tutorials/AndroidIntent/article.html
    @OnClick(R.id.buttonSend)
    public void submitSend(){
        Intent SendOrderByGmail = new Intent(Intent.ACTION_SENDTO);
        SendOrderByGmail.setData(Uri.parse("mailto:"));
        SendOrderByGmail.putExtra(Intent.EXTRA_TEXT, messageBill(validateAds(checkBoxWhippedCream), validateAds(checkboxChocolate)));//este es el body del correo
        if(SendOrderByGmail.resolveActivity(getPackageManager()) != null) {
            startActivity(SendOrderByGmail);
        }
    }

    private void captureNumberCoffees(int sign) {
        int numberCoffees = Integer.parseInt(textNumber.getText().toString());//capturo el contenido del TextView, lo paso a int y lo almaceno en la var.
        if (validatePositive(numberCoffees, sign)) {
            newNumberCoffees(numberCoffees, sign);
        } else {//muestro mensaje de error si el numero es 0 y se va a disminuir
            Snackbar.make(parentLayout, "No puedes regalar el cafe", Snackbar.LENGTH_SHORT).show();
        }
    }

    private boolean validatePositive(int numberCoffees, int sign) {
        boolean execute;
        if (numberCoffees == 0 && sign == -1) {
            execute = false;
        } else {
            execute = true;
        }
        return execute;
    }

    private void newNumberCoffees(int numberCoffees, int sign) {
        textNumber.setText(String.valueOf(numberCoffees + sign));//actualizo el valor del TextView
    }

    private String validateAds(CheckBox Add) {
        String answer;
        if (Add.isChecked()) {
            answer = "Yes";
        } else {
            answer = "No";
        }
        return answer;
    }

    private String messageBill(String checkBoxWhippedCream, String checkboxChocolate) {
        String messageCheck = "Name: " + textName.getText().toString();
        messageCheck += "\nAdd whipped cream? " + checkBoxWhippedCream;
        messageCheck += "\nAdd chocolate? " + checkboxChocolate;
        messageCheck += "\nQuantity: " + textNumber.getText().toString();
        messageCheck += "\nTotal: $ " + finalPrice();
        messageCheck += "\nThank you!";
        return messageCheck;
    }

    private double finalPrice() {
        double price = coffeOrder() + aditionalPacks();
        return price;
    }

    private int coffeOrder() {
        return Integer.parseInt(textNumber.getText().toString()) * cofeeValue;
    }

    private double aditionalPacks() {
        double aditionals = 0.0;
        if (checkBoxWhippedCream.isChecked()) {
            aditionals += 1;
        }
        if (checkboxChocolate.isChecked()) {
            aditionals += 1.5;
        }
        return aditionals * Integer.parseInt(textNumber.getText().toString());
    }

}