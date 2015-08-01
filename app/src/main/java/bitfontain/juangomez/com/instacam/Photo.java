package bitfontain.juangomez.com.instacam;

import android.os.Environment;

import java.io.File;
import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Juan on 7/27/2015.
 */
public class Photo implements Serializable {

    //This is the location where the pictures has been stored
    private static final File sDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
    private UUID mId;

    Photo(){
        mId = UUID.randomUUID();
    }

    public UUID getId() {
        return mId;
    }

    public File getFile(){
        //Constructs a new file using the specified directory and name.
        return new File(sDirectory, mId.toString());
    }
}
