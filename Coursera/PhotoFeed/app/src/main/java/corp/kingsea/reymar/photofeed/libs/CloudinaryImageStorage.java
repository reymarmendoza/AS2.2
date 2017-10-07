package corp.kingsea.reymar.photofeed.libs;

import android.content.Context;
import android.os.AsyncTask;

import com.cloudinary.Cloudinary;
import com.cloudinary.android.Utils;
import com.cloudinary.utils.ObjectUtils;

import java.io.File;
import java.util.Map;

import corp.kingsea.reymar.photofeed.libs.base.EventBus;
import corp.kingsea.reymar.photofeed.libs.base.ImageStorage;
import corp.kingsea.reymar.photofeed.libs.base.ImageStorageFinishedListener;

/**
 * Created by reyma on 8/07/2016.
 */
public class CloudinaryImageStorage implements ImageStorage {

    private Cloudinary cloudinary;

    public CloudinaryImageStorage(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String getImageURL(String id) {
        return cloudinary.url().generate(id);//pertenece al api y pide la imagen a la bd segun el id
    }

    @Override
    public void upload(final File file, final String id, final ImageStorageFinishedListener listener) {
        new AsyncTask<Void, Void, Void>() {//peticion asincrona, mientras que se ejecuta la peticion a la bd sigue ejecutando
            boolean success = false;
            @Override
            protected Void doInBackground(Void... voids) {
                Map params = ObjectUtils.asMap("public_id", id);

                try {
                    cloudinary.uploader().upload(file, params);
                    success = true;
                } catch (Exception e) {
                    listener.onError(e.getMessage());
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (success) {
                    listener.onSuccess();
                }
            }
        }.execute();
    }
}