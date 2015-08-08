package bitfontain.juangomez.com.instacam;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class NewPhotoActivity extends ActionBarActivity implements NewPhotoFragment.Contract {

    private static final int CAMERA_REQUEST = 20;
    public static final String PHOTO_EXTRA = "PHOTO_EXTRA";
    private static final String PHOTO_STATE_EXTRA = "PHOTO";
    private static final String PHOTO_FILE = "PHOTO_FILE";

    Photo mPhoto;
    private NewPhotoFragment fragment;
    //public BroadcastReceiver bc receiver =

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_photo);


        //*** Attaching the fragment to the activity ***
        //Get NewPhotoFragment from FragmentLayot new_photo_frag_container
        fragment = (NewPhotoFragment) getFragmentManager().findFragmentById(R.id.new_photo_frag_container);
        if (fragment == null) {

            fragment = new NewPhotoFragment();

           /* //Send object to a fragment
            Bundle bundle = new Bundle();
            bundle.putString(PHOTO_FILE, "Juan");

            fragment.setArguments(bundle);*/


            getFragmentManager().beginTransaction()
                    .add(R.id.new_photo_frag_container, fragment)
                    .commit();

        }

        //We save here the information that we lost wen the activity is stop when the camera is activated.
        if (savedInstanceState != null) {
            mPhoto = (Photo)savedInstanceState.getSerializable(PHOTO_STATE_EXTRA);

        }

    }

    //Use this method to pass mPhoto object to NewPhotoFragment and access it in onCreateView
    public Photo photoObj(){
        return mPhoto;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST) {
            if (resultCode == RESULT_OK) {

                //Let the fragment know that We get my photo back
                fragment.updatePhoto(mPhoto);

            }

        }
    }

    //Method implemented from Contract interface
    public void launchCamera(){

        //It lunches the camera
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        mPhoto = new Photo();

        //Tells the camara where to save the picture
        i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPhoto.getFile()));
        startActivityForResult(i, CAMERA_REQUEST);


    }

    //Method implemented from Contract interface
    @Override
    public void finishedPhoto(Photo photo) {
        //Send it back with an intent
        Intent i = new Intent();
        i.putExtra(PHOTO_EXTRA, photo);
        setResult(RESULT_OK, i);
        //It will finish this activity and will go back to the activity that called this activity (main activity
        finish();
    }

    //It is called when the activity is about to be destroyed
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Bundle saves extra information
        outState.putSerializable(PHOTO_STATE_EXTRA, mPhoto);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_photo, menu);
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
}
