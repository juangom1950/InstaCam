package bitfontain.juangomez.com.instacam;

import android.os.Environment;

import java.io.File;
import java.util.UUID;

/**
 * Created by Juan on 7/27/2015.
 */
public class Photo {

    private UUID mId;

    Photo() {
        mId = UUID.randomUUID();
    }

    public UUID getId() {
        return mId;
    }

    public File getFile(){
        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File photo = new File(directory, "sample.jpeg");
        return photo;
        //return  new File(directory, mId.toString());
    }
}
