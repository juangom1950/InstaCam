package bitfontain.juangomez.com.instacam;

import android.app.Activity;
import android.app.Fragment;

/**
 * Created by Juan on 8/3/2015.
 */

//This is a supper class that use generics, which let us to share the same methods, but allows us to use an interface
// that implements their methods according with their needs. Video section 17 #10
public class ContractFragment<T> extends Fragment {

    private T mContract;

    //onAttach is a lifecycle method of a fragment that is called when the fragment is attached to an activity
    //onAttach() and onDetach() are implemented in the classes that we extend and and set our contract (interface)
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        //Do this to catch any exception cause in the implementation of the interface in the ContactListActivity
        try{
            mContract = (T)getActivity();
        }catch (ClassCastException e){
            throw new IllegalStateException("Activity does not implement contract");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        //We clean up the contract object
        mContract = null;
    }

    public T getContract(){
        return mContract;
    }
}
