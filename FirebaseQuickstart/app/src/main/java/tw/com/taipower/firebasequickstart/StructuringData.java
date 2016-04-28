package tw.com.taipower.firebasequickstart;

import com.firebase.client.ChildEventListener;
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
        // See if Andrew is in the 'bravo' group
        Firebase chatRef = this.ref.child("users/andrew/groups/bravo");
        chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String result = snapshot.getValue() == null ? "is not" : "is";
                System.out.println("Andrew " + result + " a member of bravo group");
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                // ignore
            }
        });
    }

    /**
     * 2、嘗試結合扁平式資料表(Joining Flattened Data)去搜索資料
     */
    public void testJoiningFlattenedData(){
        // List the names of all Andrew's groups
        final Firebase chatRef = this.ref;

        // fetch a list of Andrew's groups
        chatRef.child("users/andrew/groups").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                // for each group, fetch the name and print it
                String groupKey = snapshot.getKey();
                chatRef.child("groups/" + groupKey + "/name").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        System.out.println("Andrew is a member of this group: " + snapshot.getValue());
                    }
                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        // ignore
                    }
                });
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
