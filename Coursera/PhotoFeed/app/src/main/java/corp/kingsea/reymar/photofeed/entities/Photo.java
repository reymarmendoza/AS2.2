package corp.kingsea.reymar.photofeed.entities;

import com.google.firebase.database.Exclude;

/**
 * Created by reyma on 8/07/2016.
 */
public class Photo {//este es un plain old java object POJO

    @Exclude
    private String id;//el identificador del key en firebase

    @Exclude//no la quiero incluir en lo que guardo en firebase
    private boolean publishedByMe;//saber si yo publique la foto

    private String url;//lo obtengo de cloudinar en base al id de firebase
    private String email;//especificar el usuario que publico la foto
    private double latitude;
    private double longitude;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isPublishedByMe() {
        return publishedByMe;
    }

    public void setPublishedByMe(boolean publishedByMe) {
        this.publishedByMe = publishedByMe;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
