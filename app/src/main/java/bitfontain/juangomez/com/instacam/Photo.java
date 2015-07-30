package bitfontain.juangomez.com.instacam;

import android.os.Environment;

import java.io.File;
import java.util.UUID;

/**
 * Created by Juan on 7/27/2015.
 */
public class Photo {

    private static final File sDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
    private UUID mId;

    Photo() {
        mId = UUID.randomUUID();
    }

    public UUID getId() {
        return mId;
    }

    public File getFile(){
        return new File(sDirectory, mId.toString());
    }
}
