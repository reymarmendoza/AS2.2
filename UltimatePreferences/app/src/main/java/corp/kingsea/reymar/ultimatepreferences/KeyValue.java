package corp.kingsea.reymar.ultimatepreferences;

/**
 * Created by reyma on 1/13/2017.
 */

public interface KeyValue {
    String check_key = "check_box_preference";
    String edit_key = "edit_text_preference";
    String list_key = "list_preference";
    String switch_key = "switch_preference";
    //la LISTA deberia ser 'int list_value = 0;' pero no se puede ya que en su definicion XML se le ingresa un contenido string
    //por tanto se deben manejar sus elementos siempre como un string y darle la conversion en JAVA cuando sea invocado
    boolean check_value = false;
    String edit_value = "Cúcuta";
    String list_value = "Hep cats";
    boolean switch_value = false;
    //estas variables permiten guardar la informacion de los recursos(int) de acuerdo a los string obtenidos de list_key
    String listResouce_key = "listResource_preference";
    int listResource_value = R.raw.hep_cats;
}
