package bitfontain.juangomez.com.instacam;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ShareActionProvider;

import java.io.File;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;


//public class MainActivity extends ActionBarActivity implements android.support.v7.widget.ShareActionProvider.OnShareTargetSelectedListener{
//public class MainActivity extends ActionBarActivity implements MaterialTabListener {
public class MainActivity extends ActionBarActivity implements MaterialTabListener {

    private static final int CAMERA_REQUEST = 10;
    private static final String TAG = "MainActivity";
    public Photo mPhoto;
    private FeedFragment mFeedFragment;
    private MaterialTabHost mTabBar;
    private ProfileFragment mProfileFragment;
    private File mFilePhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ImageButton cameraFAB =  (ImageButton)findViewById(R.id.camera_fab);
        cameraFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //We need to give it a file were we want the image save, and hen when the camera is done it is going to give us the result
                //MediaStore.ACTION_IMAGE_CAPTURE this will lunch the camera app
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                mPhoto = new Photo();

                //mFilePhoto = mPhoto.getFile();
                //File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                //mFilePhoto = new File(directory, "sample.jpeg");

                //Second parameter is going to create a string that lets the camera know were is it going to save the photo
                i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPhoto.getFile()));

                startActivityForResult(i, CAMERA_REQUEST);
            }
        });

        /*Button button =  (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                mFilePhoto = new File(directory, "sample.jpeg");

                //Second parameter is going to create a string that lets the camera know were is it going to save the photo
                i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mFilePhoto));

                startActivityForResult(i, CAMERA_REQUEST);
            }
        });*/

        //Set toolbar as an actionbar
        Toolbar toolbar =  (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //neokree/MaterialTabs libraries https://github.com/neokree/MaterialTabs
        //Implement MaterialTabListener interface.
        //Add tab libraries to our project  compile 'it.neokree:MaterialTabs:0.11' in build.gradle
        //Add <it.neokree.materialtabs.MaterialTabHost> to activity_main.xml
        mTabBar = (MaterialTabHost)findViewById(R.id.tab_bar);
        mTabBar.addTab(mTabBar.newTab().setIcon(getResources().getDrawable(R.drawable.ic_home)).setTabListener(this));
        mTabBar.addTab(mTabBar.newTab().setIcon(getResources().getDrawable(R.drawable.ic_profile)).setTabListener(this));
        /*mTabBar.addTab(mTabBar.newTab().setText("HOME").setTabListener(this));
        mTabBar.addTab(mTabBar.newTab().setText("PROFILE").setTabListener(this));*/

        //Attaching the fragment to the activity
        mFeedFragment = (FeedFragment)getFragmentManager().findFragmentById(R.id.feed_container);

        if (mFeedFragment == null) {

            mFeedFragment = new FeedFragment();

            getFragmentManager().beginTransaction()
                    .add(R.id.feed_container, mFeedFragment)
                    .commit();
        }

        //Explicitly intent. We explicitly say wre we want to go
        //Intent i = new Intent(context, NextActivity.class);

        //Implicit intent. It says what kind of action we want to do.
        //Android figures out the different activities that provide this action and let you choose were you want to go

        /*Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override

        });*/

    }

    //public void onClick(View v) {

        //Send email.
        //Check this link http://javatechig.com/android/how-to-send-email-in-android
        //Send email without user interaction: http://www.mindstick.com/Articles/177fc802-a020-4c9f-970c-af73773cd911/Sending%20mail%20without%20user%20interaction%20in%20Android#.VbPH1_lViko
        //How to add dependency (.jar .aar) in Android Studio: https://www.youtube.com/watch?v=dpuJPoXkFG4
        //Android Studio: How to add library (jar) as dependency in android studio: https://www.youtube.com/watch?v=tfPgx6c5wgA
        //Android Studio Tutorials || Adding a Jar library: https://www.youtube.com/watch?v=fHEvI_G6UtI
                /*Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"juangom73@gmail.con"});
                emailIntent.putExtra(Intent.EXTRA_CC, new String[]{"juangom73@yahoo.con"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");

                emailIntent.setType("message/rfc822");
                startActivity(Intent.createChooser(emailIntent, "Choose email client..."));*/

                /*//Send a message
                Intent i = new Intent(Intent.ACTION_SEND);

                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "Hey");
                i.putExtra(Intent.EXTRA_TEXT, "This is a message!");
                startActivity(i);*/




    //}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST) {
            if (resultCode == RESULT_OK) {

                //Log.d(TAG, "We took a picture. Get file is "+ mPhoto.getFile());


                //Log.d(TAG, "We took a picture test ");
//
                //   mFilePhoto = new File("/storage/emulated/0/DCIM/sample.jpeg//");
                //mFilePhoto = mPhoto.getFile();

                //Log.d(TAG, "We took a picture test " + mFilePhoto);
                //Uri infData = Uri.fromFile(mFilePhoto);
//
//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.setDataAndType(Uri.fromFile(mFilePhoto), "image/jpeg");
//
//                //Activity start another activity where you ca see the photo
//                startActivity(i);

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setDataAndType(Uri.fromFile(mPhoto.getFile()), "image/jpeg");

                startActivity(i);
            }

        }
    }



    /*//This is a method require when implement this interface ShareActionProvider.OnShareTargetSelectedListener
    @Override
    public boolean onShareTargetSelected(android.support.v7.widget.ShareActionProvider source, Intent intent) {
        //It will start whatever intent was selected
        startActivity(intent);

        return false;
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

       /* //Open the share options in the menu
        android.support.v7.widget.ShareActionProvider share = (android.support.v7.widget.ShareActionProvider)MenuItemCompat.getActionProvider(menu.findItem(R.id.action_settings));

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        share.setShareIntent(i);

        //To make it works you need to implement this interface ShareActionProvider.OnShareTargetSelectedListener at the top
        share.setOnShareTargetSelectedListener(this);*/

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

    //These three methods are part of the interface MaterialTabListener so they need to be implemented
    @Override
    public void onTabSelected(MaterialTab materialTab) {

        //The position is going to tell us which tab was selected
        int position = materialTab.getPosition();

        //Set selected tab to that position. Use this to underline tab at the bottom
        mTabBar.setSelectedNavigationItem(position);

        Fragment fragment = null;
        switch (position) {
            case 0 :
                fragment = mFeedFragment;
                break;
            case 1:
                if (mProfileFragment == null){
                    mProfileFragment = new  ProfileFragment();
                }
                fragment = mProfileFragment;
                break;

        }

        //Replace itr here with whatever fragment was selected
        getFragmentManager().beginTransaction()
                .replace(R.id.feed_container, fragment)
                .commit();
    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {

    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {

    }
}
