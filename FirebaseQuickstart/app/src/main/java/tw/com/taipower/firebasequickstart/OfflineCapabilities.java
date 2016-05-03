package tw.com.taipower.firebasequickstart;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ServerValue;
import com.firebase.client.ValueEventListener;

import java.util.Date;

/**
 * Created by new on 2016/4/29.
 */
public class OfflineCapabilities {

    private final static String myFirebaseURL = "https://prework-db.firebaseio.com/";
    Firebase ref = new Firebase(myFirebaseURL);

    public OfflineCapabilities(){
        this.ref = this.ref.child("retriving-data");     // setting default root
//        this.ref.keepSynced(true);
    }

    /**
     * 1、測試離線再連線後，資料會再去從Server讀取資料。(listener約10秒1次)
     */
    public void testQueryingDataOffline(){
        Firebase scoresRef = this.ref.child("scores");
        scoresRef.orderByValue().limitToLast(2).addChildEventListener(new ChildEventListener() {
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
     * 2、測試離線再連線後，資料會再去寫入資料到Server。(listener約2~3分鐘)
     */
    public void testManagingPresence(){
        Firebase presenceRef = this.ref.child("disconnectmessage");
        // Write a string when this client loses connection
        presenceRef.onDisconnect().setValue("I disconnected!");
    }

    /**
     * 3、測試目前與Firebase連線的狀況
     */
    public void testDetectingConnectionState(){
        Firebase connectedRef = new Firebase(OfflineCapabilities.myFirebaseURL + ".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    //斷線再連線後，約5~10秒才有回應
                    System.out.println("connected");
                } else {
                    //連線再斷線後，約1秒內就有回應
                    System.out.println("not connected");
                }
            }

            @Override
            public void onCancelled(FirebaseError error) {
                System.err.println("Listener was cancelled");
            }
        });
    }

    /**
     * 4、測試抓取Firebase上的time offset再加上Local Time就可以推估Server上的Time。
     */
    public void testHandlingLatency(){
        Firebase offsetRef = new Firebase(OfflineCapabilities.myFirebaseURL + ".info/serverTimeOffset");
//        Firebase offsetRef = this.ref.child("users/joe/lastOnline");
//        offsetRef.onDisconnect().setValue(ServerValue.TIMESTAMP);

        offsetRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
//                long offset = snapshot.getValue(Long.class);
//                long estimatedServerTimeMs = System.currentTimeMillis() + offset;
                System.out.println("Server offset    ：" + snapshot.getValue(Long.class));
                System.out.println("Server Time(long)：" + (System.currentTimeMillis() + snapshot.getValue(Long.class)) + "\n" +
                                   "Now Time(long)   ：" + System.currentTimeMillis());
                System.out.println("Server Time      ：" + new Date(System.currentTimeMillis() + snapshot.getValue(Long.class)) + "\n" +
                                   "Now Time         ：" + new Date()
                );
            }
            @Override
            public void onCancelled(FirebaseError error) {
                System.err.println("Listener was cancelled");
            }
        });
    }

    /**
     * 5、simple user presence system的範例，但還是不知道該如何使用。
     */
    public void testSamplePresenceApp(){
        // since I can connect from multiple devices, we store each connection instance separately
        // any time that connectionsRef's value is null (i.e. has no children) I am offline
        final Firebase myConnectionsRef = this.ref.child("users/joe/connections");

        // stores the timestamp of my last disconnect (the last time I was seen online)
        final Firebase lastOnlineRef = this.ref.child("users/joe/lastOnline");

        final Firebase connectedRef = new Firebase(OfflineCapabilities.myFirebaseURL + ".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    // add this device to my connections list
                    // this value could contain info about the device or a timestamp too
                    Firebase con = myConnectionsRef.push();
                    con.setValue(Boolean.TRUE);

                    // when this device disconnects, remove it
                    con.onDisconnect().removeValue();

                    // when I disconnect, update the last time I was seen online
                    lastOnlineRef.onDisconnect().setValue(ServerValue.TIMESTAMP);
                }
            }

            @Override
            public void onCancelled(FirebaseError error) {
                System.err.println("Listener was cancelled at .info/connected");
            }
        });
    }
}
