package bitfontain.juangomez.com.instacam;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
//We tell ContractFragment that we are going to be using the interface that we created at the bottom
public class NewPhotoFragment extends ContractFragment<NewPhotoFragment.Contract> {

    private static final String PHOTO_STATE_EXTRA = "PHOTO";
    //private static final String CAMERA_REQUEST = "20";

    private ImageView mPreview;
    Photo mPhoto;

    public NewPhotoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_new_photo, container, false);

        mPreview = (ImageView)v.findViewById(R.id.photo_preview);

        final EditText caption =  (EditText)v.findViewById(R.id.new_photo_caption);

        Button saveButton =  (Button)v.findViewById(R.id.save_new_photo);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Save caption for photo
                mPhoto.setCaption(caption.getText().toString());
                mPhoto.setUser(User.getCurrentUser());
                getContract().finishedPhoto(mPhoto);

            }
        });

        //Get mPhoto obj saved instanceState from activity.
        //You loose this value in the activity when camera get lunch
        NewPhotoActivity activity = (NewPhotoActivity) getActivity();
        mPhoto = activity.photoObj();

        /*//Test passing sting value from activity
        Bundle bundle = getArguments();
        if (bundle != null) {
            String val = bundle.getString("PHOTO_FILE");
            Log.d("Hello", "hello");
            //caption.setText(val);
            //mPhoto = (Photo)bundle.getSerializable("PHOTO_FILE");
        }*/

        if (mPhoto == null) {
            getContract().launchCamera();
        }else{
            //mPhoto = (Photo)getArguments().getSerializable("PHOTO_FILE");
            loadThumbnail(mPhoto);
        }

        return v;
    }

    /*  //It is called when the activity is about to be destroyed
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Bundle saves extra information
        outState.putSerializable(PHOTO_STATE_EXTRA, mPhoto);
    }*/

    public void updatePhoto(Photo photo){
        mPhoto = photo;
        loadThumbnail(mPhoto);
    }

    private void loadThumbnail(Photo photo){

        Picasso.with(getActivity()).load(photo.getFile()).into(mPreview);
    }

    //This interface is implemented in NewPhotoActivity
    public interface Contract{
        //We tell the parent activiti to lunch the camera for us
        public void launchCamera();
        public void finishedPhoto(Photo photo);

    }

}
