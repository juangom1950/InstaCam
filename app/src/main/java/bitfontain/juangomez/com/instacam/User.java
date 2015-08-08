package bitfontain.juangomez.com.instacam;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Juan on 8/6/2015.
 */
//This is a singleton
//We make this serializable to be able to send this object back with an intend
public class User implements Serializable {

    private static final String TAG = "User";
    private String mFirstName;
    private String mLastName;
    private Date mBirthday;
    private String mAvatarURL;

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public Date getBirthday() {
        return mBirthday;
    }

    public String getAvatarURL() {
        return mAvatarURL;
    }

    //Add this to make this class a singleton
    private static User sCurrentUser;

    //Add this to make this class a singleton
    public static User getCurrentUser() {
        return sCurrentUser;
    }

    //Add this to make this class a singleton
    public static void setCurrentUser(JSONObject user) {
        if (sCurrentUser == null){
            sCurrentUser = new User(user);
        }
    }

    //The default constructor modifier is determine by the modifier of the class
    User(JSONObject user){

        try {
            mFirstName = user.getString("first_name");
            mLastName = user.getString("last_name");

            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

            try {
                mBirthday = sdf.parse(user.getString("birthday"));
            } catch (ParseException e) {
                Log.d(TAG, "Failed at parsing date "+user.getString("birthday"));
            }

            JSONObject picture = user.getJSONObject("picture");
            JSONObject data = picture.getJSONObject("data");
            mAvatarURL = data.getString("url");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*mFirstName = (String)graphObject.getProperty("first_name");
        mLastName = (String)graphObject.getProperty("last_name");
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        try {
            mBirthday = sdf.parse((String)graphObject.getProperty("birthday"));
        } catch (ParseException e) {
            Log.d(TAG, "Failed at parsing date " + graphObject.getProperty("birthday"));
        }
        mAvatarURL = (String) graphObject.getPropertyAs("picture",GraphObject.class)
                .getPropertyAs("data",GraphObject.class)
                .getProperty("url");*/
    }
}
