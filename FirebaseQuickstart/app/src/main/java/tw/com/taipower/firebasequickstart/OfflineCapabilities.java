package tw.com.taipower.firebasequickstart;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

/**
 * Created by new on 2016/4/29.
 */
public class OfflineCapabilities {

    private final static String myFirebaseURL = "https://prework-db.firebaseio.com/";
    Firebase ref = new Firebase(myFirebaseURL);

    public OfflineCapabilities(){
        this.ref = this.ref.child("retriving-data");     // setting default root
    }

    /**
     * 1、測試離線再連線後，資料會再去從Server讀取資料。(listener約10秒1次)
     */
    public void testQueryingDataOffline(){
        Firebase scoresRef = this.ref.child("scores");
        scoresRef.orderByValue().limitToLast(4).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                System.out.println("The " + snapshot.getKey() + " dinosaur's score is " + snapshot.getValue());
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

    /**
     * 2、測試離線再連線後，資料會再去寫入資料到Server。(listener約1~2分鐘)
     */
    public void testManagingPresence(){
        Firebase presenceRef = this.ref.child("disconnectmessage");
        // Write a string when this client loses connection
        presenceRef.onDisconnect().setValue("I disconnected!");
    }
}
