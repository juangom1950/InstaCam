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


public class MainActivity extends ActionBarActivity implements MaterialTabListener {

    private static final int NEW_PHOTO_REQUEST = 10;
    private static final String TAG = "MainActivity";
    private FeedFragment mFeedFragment;
    private MaterialTabHost mTabBar;
    private ProfileFragment mProfileFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Floating action button
        ImageButton cameraFAB =  (ImageButton)findViewById(R.id.camera_fab);
        cameraFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, NewPhotoActivity.class);
                startActivityForResult(i, NEW_PHOTO_REQUEST);
            }
        });

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

        //I get the fragment from the FrameLayout that is in the main activity
        mFeedFragment = (FeedFragment)getFragmentManager().findFragmentById(R.id.feed_container);
        if (mFeedFragment == null){

            //Attach the fragment
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_PHOTO_REQUEST) {
            if (resultCode == RESULT_OK) {
                Photo photo = (Photo)data.getSerializableExtra(NewPhotoActivity.PHOTO_EXTRA);
                Log.d(TAG, "Photo Test: " + photo);
                if (photo != null) {
                    mFeedFragment.addPhoto(photo);
                }

            }

        }
    }

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
