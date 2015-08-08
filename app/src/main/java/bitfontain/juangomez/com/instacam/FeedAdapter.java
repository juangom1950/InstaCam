package bitfontain.juangomez.com.instacam;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Juan on 7/27/2015.
 */
//Add extends
public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    //Add this class
    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mCaption;
        private final ImageView mPhoto;
        private final ImageView mAvatar;
        private final TextView mUsername;

        //Add this constructor
        public ViewHolder(View itemView) {
            super(itemView);

            //These values come from the layout feed_item.xm
            mCaption = (TextView)itemView.findViewById((R.id.feed_item_caption));
            mPhoto = (ImageView)itemView.findViewById((R.id.feed_item_photo));
            mAvatar = (ImageView)itemView.findViewById((R.id.feed_item_user_avatar));
            mUsername = (TextView)itemView.findViewById((R.id.feed_item_user_name));
        }
    }

    private List<Photo> mPhotos;
    private Context mContext;

    //Add these override methods and this constructor. right click > generate... > Override Methods...
    // choose methods that you want to add
    //After this you need to create the layout for the feed item (feed_item.xml)
    public FeedAdapter(Context context,  List<Photo> photos) {
        mContext = context;
        mPhotos = photos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feed_item, viewGroup, false);

        return new ViewHolder(v);
    }

    //Here we bind the information
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        //Here we bind the values to the viewHolder. This is the last thing that we do
        //Get specific photo
        Photo photo = mPhotos.get(i);
        User user = photo.getUser();
        //Add photo
        Picasso.with(mContext).load(photo.getFile()).into(viewHolder.mPhoto);
        ///Add captions
        viewHolder.mCaption.setText(photo.getCaption());
        viewHolder.mUsername.setText(user.getFirstName() + " " + user.getLastName());
        Picasso.with(mContext).load(user.getAvatarURL()).into(viewHolder.mAvatar);
    }

    @Override
    public int getItemCount() {
        return mPhotos.size();
    }

}
