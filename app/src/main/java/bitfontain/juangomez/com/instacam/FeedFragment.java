package bitfontain.juangomez.com.instacam;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends Fragment {

    private static final String TAG = "FeedFragment";

    private List<Photo> mPhotos;
    private FeedAdapter mAdapter;

    public FeedFragment() {

        //Put it in this constructor because it is going to be called only once
        mPhotos = new ArrayList<Photo>();
        //Test data
       /* mPhotos.add(new Photo());
        mPhotos.add(new Photo());*/

        mAdapter = new FeedAdapter(getActivity(), mPhotos);
    }

   /* @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(TAG, "On create view called");

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_feed, container, false);

        setRetainInstance(true);

        RecyclerView recyclerView = (RecyclerView)v.findViewById(R.id.feed_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //To do this we need ro create first our adapter
        recyclerView.setAdapter(mAdapter);

        return v;
    }

    public void addPhoto(Photo photo){

        mPhotos.add(0, photo);
        //Notify the adapter of the recycler view that the data has change
        mAdapter.notifyDataSetChanged();
    }
}
