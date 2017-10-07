package corp.kingsea.reymar.photofeed.domain;

import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created by reyma on 8/07/2016.
 */
public class Util {

    private Geocoder geocoder;
    private final static String GRAVATAR_URL = "http://www.gravatar.com/avatar/";

    public Util(Geocoder geocoder) {
        this.geocoder = geocoder;
    }

    public String getAvatarUrl(String email){//voy a tomar la imagen de perfil a partir del correo
        return GRAVATAR_URL +md5(email) + "?s=64";
    }

    public String getFromLocation(double lat, double lng){//voy a tomar la ubicacion
        String result = "";
        List<Address> addresses = null; //creo una lista vacia para guardar las direcciones el <> es propio de Android
        try {//puede arrojar una exepcion
            addresses = geocoder.getFromLocation(lat, lng, 1);//obtengo la direccion
            Address address = addresses.get(0);//asigno la primera posicion
            for(int i = 0; i < address.getMaxAddressLineIndex(); i++){
                result += address.getAddressLine(i) + ", ";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String md5(final String s) {//sirve para validar la integriad de un app
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
