package tw.com.taipower.firebasequickstart;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import tw.com.taipower.firebasequickstart.entity.BlogPost;
import tw.com.taipower.firebasequickstart.entity.DinosaurFacts;

/**
 * Created by new on 2016/4/26.
 */
public class RetrivingData {
    private String myFirebaseURL = "https://prework-db.firebaseio.com/";

    public RetrivingData(String firebaseUrl){
        this.myFirebaseURL = firebaseUrl;  // setting default root
    }

    /**
     * 1、嘗試使用ValueEventListener從Firebase讀取資料
     */
    public void readDataByValueEventListener() {
        // Get a reference to our posts
        Firebase ref = new Firebase(myFirebaseURL + "book/");
        // Attach an listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println("There are " + snapshot.getChildrenCount() + " blog posts");
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    BlogPost post = postSnapshot.getValue(BlogPost.class);
                    System.out.println(post.getAuthor() + " - " + post.getTitle());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
    }

    /**
     * 2、嘗試使用ChildEventListener從Firebase讀取資料
     */
    public void readDataByChildEventListener() {
        // Get a reference to our posts
        Firebase ref = new Firebase(myFirebaseURL + "book/");
        ref.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to the database
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                BlogPost newPost = snapshot.getValue(BlogPost.class);
                System.out.print("onChildAdded method:\t");
                System.out.println("Author: " + newPost.getAuthor() + "\t"
                        + "Title: " + newPost.getTitle());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                BlogPost newPost = dataSnapshot.getValue(BlogPost.class);
                System.out.print("onChildChanged method:\t");
                System.out.println("Author: " + newPost.getAuthor() + "\t"
                        + "Title: " + newPost.getTitle());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                BlogPost newPost = dataSnapshot.getValue(BlogPost.class);
                System.out.print("onChildRemoved method:\t");
                System.out.println("Author: " + newPost.getAuthor() + "\t"
                        + "Title: " + newPost.getTitle());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                BlogPost newPost = dataSnapshot.getValue(BlogPost.class);
                System.out.print("onChildMoved method:\t");
                System.out.println("Author: " + newPost.getAuthor() + "\t"
                        + "Title: " + newPost.getTitle());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    /**
     * 3、嘗試使用ChildEventListener從Firebase讀取資料
     * 並使用orderByChild方法排序資料
     */
    public void queryDataOrderByChild() {
        Firebase ref = new Firebase(myFirebaseURL + "dinosaurs/");
        Query queryRef;
//        queryRef = ref.orderByChild("height");
        queryRef = ref.orderByChild("weight");
        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                DinosaurFacts facts = snapshot.getValue(DinosaurFacts.class);
                System.out.print("onChildAdded method:\t");
                System.out.println(snapshot.getKey() + " was "
                        + facts.getHeight() + " meters tall and "
                        + facts.getWeight() + " tons.");
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
     * 4、嘗試使用ChildEventListener從Firebase讀取資料
     * 並使用orderByValue方法排序資料
     */
    public void queryDataOrderByValue() {
        Firebase scoresRef = new Firebase(myFirebaseURL + "scores/");
        Query queryRef = scoresRef.orderByValue();
        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {
                System.out.print("onChildAdded method:\t");
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
     * 5、嘗試使用ChildEventListener從Firebase讀取資料
     * 並使用orderByChild方法排序資料
     * 再使用limitToFirst擷取前n筆的資料
     */
    public void queryDataLimitToFirst() {
        Firebase ref = new Firebase(myFirebaseURL + "dinosaurs/");
        Query queryRef = ref.orderByChild("weight").limitToFirst(2);
        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                DinosaurFacts facts = snapshot.getValue(DinosaurFacts.class);
                System.out.print("onChildAdded method:\t");
                System.out.println(snapshot.getKey() + " was "
                        + facts.getHeight() + " meters tall and "
                        + facts.getWeight() + " tons.");
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
     * 6、嘗試使用ChildEventListener從Firebase讀取資料
     * 並使用orderByChild方法排序資料
     * 再使用limitToLast擷取前n筆的資料
     */
    public void queryDataLimitToLast() {
        Firebase ref = new Firebase(myFirebaseURL + "scores/");
        Query queryRef = ref.orderByValue().limitToLast(3);
        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                System.out.print("onChildAdded method:\t");
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
     * 7、嘗試使用ChildEventListener從Firebase讀取資料
     * 並使用orderByChild方法排序資料
     * 再使用startAt(n)方法取起始位置比n大的值
     */
    public void queryDataInStartAt() {
        Firebase ref = new Firebase(myFirebaseURL + "dinosaurs/");
        Query queryRef = ref.orderByChild("height").startAt(3);
        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                DinosaurFacts facts = snapshot.getValue(DinosaurFacts.class);
                System.out.print("onChildAdded method:\t");
                System.out.println(snapshot.getKey() + " was "
                        + facts.getHeight() + " meters tall and "
                        + facts.getWeight() + " tons.");
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
     * 8、嘗試使用ChildEventListener從Firebase讀取資料
     * 並使用orderByChild方法排序資料
     * 再使用startAt(n)與endAt(m)方法取起始位置比n大的值，結束位置比m小的值
     */
    public void queryDataBetweenStartAtAndEndAt() {
        Firebase ref = new Firebase(myFirebaseURL + "dinosaurs/");
        Query queryRef = ref.orderByKey().startAt("l").endAt("s");
        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {
                DinosaurFacts facts = snapshot.getValue(DinosaurFacts.class);
                System.out.print("onChildAdded method:\t");
                System.out.println(snapshot.getKey() + " was "
                        + facts.getHeight() + " meters tall and "
                        + facts.getWeight() + " tons.");
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
     * 9、嘗試把上述所介紹讀取資料的方法綜合應用。
     */
    public void queryDataInComplexCondition() {
        final Firebase ref = new Firebase(myFirebaseURL + "dinosaurs/");

        ref.child("stegosaurus").child("height").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot stegosaurusHeightSnapshot) {
                Long favoriteDinoHeight = stegosaurusHeightSnapshot.getValue(Long.class);

                //經由height排序後，取其「height <= favoriteDinoHeight」而且只取最後2筆
                Query queryRef = ref.orderByChild("height").endAt(favoriteDinoHeight).limitToLast(2);
                queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot querySnapshot) {
                        if (querySnapshot.getChildrenCount() == 2) {
                            // Data is ordered by increasing height, so we want the first entry
                            //其取出來恐龍的身高一定比劍龍(stegosaurus)的身高矮，但不是最矮的恐龍。
                            DataSnapshot dinosaur = querySnapshot.getChildren().iterator().next();
                            System.out.println("The dinosaur just shorter than the stegasaurus is " + dinosaur.getKey());
                        } else {
                            System.out.println("The stegosaurus is the shortest dino");
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError error) {
                    }
                });
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });
    }
}
