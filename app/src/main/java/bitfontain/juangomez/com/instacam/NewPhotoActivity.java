package bitfontain.juangomez.com.instacam;

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


public class NewPhotoActivity extends ActionBarActivity {

    private static final int CAMERA_REQUEST = 20;
    public static final String PHOTO_EXTRA = "PHOTO_EXTRA";
    private static final String PHOTO_STATE_EXTRA = "PHOTO";
    private ImageView mPreview;
    Photo mPhoto;
    //public BroadcastReceiver bc receiver =

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_photo);

        mPreview = (ImageView)findViewById(R.id.photo_preview);

        if (savedInstanceState != null) {
            mPhoto = (Photo)savedInstanceState.getSerializable(PHOTO_STATE_EXTRA);
        }
        if (mPhoto == null) {
            launchCamera();
        }//else{
//            loadThumbnail(mPhoto);
//        }
        
        final EditText caption =  (EditText)findViewById(R.id.new_photo_caption);

        Button saveButton =  (Button)findViewById(R.id.save_new_photo);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Save caption for photo
                mPhoto.setCaption(caption.getText().toString());
                //Send it back with an intent
                Intent i = new Intent();
                i.putExtra(PHOTO_EXTRA, mPhoto);
                setResult(RESULT_OK, i);
                //It will finish this activity
                finish();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST) {
            if (resultCode == RESULT_OK) {

                //To make it work we need to add permission in the manifest to allows us to read fom storage
                Picasso.with(this).load(mPhoto.getFile()).into(mPreview);

                //Bitmap cameraImage = (Bitmap) data.getExtras().get("data");
                //mPreview.setImageBitmap(cameraImage);

                /*// the address of the image on the SD Card.
                Uri imageUri = data.getData();*/

            }

        }
    }

    private void launchCamera(){

        //It lunches the camera
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        mPhoto = new Photo();

        //Tells the camara where to save the picture
        i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPhoto.getFile()));

        startActivityForResult(i, CAMERA_REQUEST);
    }

    //It is called when the activity is stop
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
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
