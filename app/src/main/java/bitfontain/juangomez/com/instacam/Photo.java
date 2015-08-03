package bitfontain.juangomez.com.instacam;

import android.os.Environment;

import java.io.File;
import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Juan on 7/27/2015.
 */
//We make our photo serializable to put this class inside of an intent
public class Photo implements Serializable {

    //This is the location where the pictures has been stored
    private static final File sDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
    private UUID mId;
    private String mCaption;

    Photo(){
        mId = UUID.randomUUID();
    }

    public UUID getId() {
        return mId;
    }

    public String getCaption() {
        return mCaption;
    }

    public void setCaption(String caption) {
        mCaption = caption;
    }

    public File getFile(){
        //Constructs a new file using the specified directory and name.
        return new File(sDirectory, mId.toString());
    }
}
