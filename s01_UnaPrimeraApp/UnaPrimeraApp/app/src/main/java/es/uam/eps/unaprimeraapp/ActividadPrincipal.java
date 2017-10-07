package es.uam.eps.unaprimeraapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

public class ActividadPrincipal extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* CAMBIO 3
         * Rellena el argumento del metodo setContentView usando el identificador
         * de layout adecuado de los recursos
         */
        setContentView();
        
        /* CAMBIO 6
         * 	Debes añadir el codigo necesario para cambiar el color de fondo del botón con 
         *  id boton1 a blanco opaco. Nota: para cambiar el color de fondo debes usar el 
         *  metodo setBackgroundColor.  
         */
        
        
        
    }

    public void botonArribaClick(View v){
    	v.setVisibility(View.GONE);
    }
    
    public void botonAbajoClick(View v){
    	
    	/* CAMBIO 4:
    	 * Substituye null por la llamada necesaria para obtener la referencia al boton2. 
    	 *   Pista: el método a usar es findViewById 
    	 */
    	Button boton2 = (Button) null;
    	
    	/* CAMBIO 5:
    	 * Substituye "" por la llamada necesaria para obtener la cadena definida en los
    	 *    recursos con nombre otra_etiqueta_boton2. 
    	 *   Pista: para obtener acceso a los recursos hay que usar el metodo getResources() 
    	 */
    	String texto = "";
    	boton2.setText(texto);
    }

}
