package tw.com.taipower.firebasequickstart;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created by new on 2016/4/27.
 */
public class StructuringData {
    private final static String myFirebaseURL = "https://prework-db.firebaseio.com/";
    Firebase ref = new Firebase(myFirebaseURL);

    public StructuringData(){
        this.ref = this.ref.child("structuring-data");     // setting default root
    }

    /**
     * 1、嘗試讀取good json structure data
     */
    public void testStructuringDataBenifit(){
        // See if Mary is in the 'alpha' group
        Firebase chatRef = this.ref.child("users/mchen/groups/alpha");
        chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String result = snapshot.getValue() == null ? "is not" : "is";
                System.out.println("Mary " + result + " a member of alpha group");
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                // ignore
            }
        });
    }
}
