package bitfontain.juangomez.com.instacam;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
//import com.facebook.login.LoginClient;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;


public class LoginActivity extends ActionBarActivity {

    private TextView info;
    private LoginButton loginButton;

    private CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //The SDK needs to be initialized before using any of its methods. You can do so by calling sdkInitialize and passing the application's context to it
        FacebookSdk.sdkInitialize(getApplicationContext());
        //Next, initialize your instance of CallbackManager
        mCallbackManager = CallbackManager.Factory.create();

        //Call setContentView to set the layout defined in the previous step as the layout of this Activity and then use findViewById to initialize the widgets.
        setContentView(R.layout.activity_login);

        info = (TextView)findViewById(R.id.info);
        loginButton =  (LoginButton)findViewById(R.id.login_button);
        //automatically add permissions to show "user_birthday"
        loginButton.setReadPermissions("user_birthday");

        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {


                Log.e("LoginActivity", "I am here");

                //Check this site http://iswwwup.com/t/730885d6af68/how-to-re-ask-declined-permissions-in-facebook-sdk-4-0-android.html
                if (loginResult.getAccessToken() != null) {
                    Set<String> permissions = AccessToken.getCurrentAccessToken().getPermissions();

                    boolean hasBirthdayPermission = false;
                    for (String permission : permissions) {
                        if (permission.equals("user_birthday")) {
                            hasBirthdayPermission = true;
                        }
                    }

                    if (!hasBirthdayPermission) {
                        //It is going to ask the users if they wants to update their permissions to allow for birthday request
                        LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("user_birthday"));
                        return;

                    }
                }
                //loginButton.setReadPermissions(Arrays.asList("public_profile, email, user_birthday, user_friends"));

                // App code
                GraphRequest grabRequest = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {

                            @Override
                            public void onCompleted(JSONObject user, GraphResponse response) {

                                // Application code
                                Log.e("LoginActivity", response.toString());

                                //Set user from singleton
                                User.setCurrentUser(user);

                                User userInf = User.getCurrentUser();

                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);

                                /*String id = user.optString("id");
                                String firstName = user.optString("first_name");


                                try {
                                    JSONObject picture = user.getJSONObject("picture");
                                    JSONObject data = picture.getJSONObject("data");
                                    String pictureUrl = data.getString("url");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }*/

                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,first_name,last_name,email,picture,gender,birthday");
                grabRequest.setParameters(parameters);
                grabRequest.executeAsync();


               /* ProfileTracker profileTracker = new ProfileTracker() {
                    @Override
                    protected void onCurrentProfileChanged(
                            Profile oldProfile,
                            Profile currentProfile) {
                        Log.e("oldProfile", oldProfile.getName());
                    }
                };*/

                info.setText(
                        "User ID: "
                        + loginResult.getAccessToken().getUserId()
                        + "\n" +
                        "Auth Token: "
                        + loginResult.getAccessToken().getToken()
                );
            }

            @Override
            public void onCancel() {
                info.setText("Login attempt canceled.");
            }

            @Override
            public void onError(FacebookException e) {
                info.setText("Login attempt failed.");
            }


        });

        //printKeyHash(this);

    }

    //Tapping the login button starts off a new Activity, which returns a result. To receive and handle the result, override the onActivityResult
    // method of your Activity and pass its parameters to the onActivityResult method of CallbackManager.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        }
        catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }
}
