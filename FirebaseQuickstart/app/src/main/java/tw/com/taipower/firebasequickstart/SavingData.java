package tw.com.taipower.firebasequickstart;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.Transaction;
import com.firebase.client.core.Context;

import java.util.HashMap;
import java.util.Map;

import tw.com.taipower.firebasequickstart.entity.User;

/**
 * Created by new on 2016/4/26.
 */
public class SavingData {
    Firebase ref;

    public SavingData(String firebaseUrl){
        this.ref = new Firebase(firebaseUrl).child("saving-data");  // setting default root
    }

    /**
     * 1、嘗試使用setValue把資料寫入Firebase資料庫
     */
    public void testSetValue() {
        //(推薦)方法一：使用user物件寫入Firebase
        Firebase alanRef = ref.child("users").child("alanisawesome");
        User alan = new User("Alan Turing", 1912);
        alanRef.setValue(alan);

//        //方法二：直接指定位置寫入Firebase
//        Firebase alansRef = ref.child("users").child("Beethoven");
//        //Referencing the child node using a .child() on it's parent node
//        alansRef.child("fullName").setValue("Ludwig van Beethoven");
//        alansRef.child("birthYear").setValue(1770);
//
//        //方法三：使用Map方式寫入Firebase
//        //※使用此方法會把該路徑下的值全部刪掉，再寫入新Map結構的值。
//        Firebase usersRef = ref.child("users");
//        Map<String, String> alanisawesomeMap = new HashMap<>();
//        alanisawesomeMap.put("birthYear", "1452");
//        alanisawesomeMap.put("fullName", "Leonardo di ser Piero da Vinci");
//        Map<String, Map<String, String>> users = new HashMap<>();
//        users.put("Leonardo da Vinci", alanisawesomeMap);
//        usersRef.setValue(users);

        //方法四：Passing null to setValue() will remove the data at the specified location.
    }

    /**
     * 2、嘗試使用updateChildren更新Firebase指定路徑的資料
     */
    public void testUpateChildren(){
        //方法一：write to specific children of a node at the same time without overwriting other child nodes
        Firebase alanRef = ref.child("users").child("alanisawesome");
        Map<String, Object> nickname = new HashMap<>();
        nickname.put("nickname", "Alan The Machine");
        alanRef.updateChildren(nickname);

        //方法二：multi-path updates
        Firebase alanRef2 = ref.child("users");
        Map<String, Object> nickname2 = new HashMap<>();
        nickname2.put("alanisawesome/nickname", "Alan The Machine");
        nickname2.put("gracehop/nickname", "Amazing Grace");
        alanRef2.updateChildren(nickname2);

        //方法三：其結果會與方法一和方法二不同
        /*
         * 因為updateChildren方法在對超過一層的更新時，
         * 其運作方式與setValue方法一樣，會先把舊的資料
         * 刪掉，再寫入新的資料。
         */
//        Firebase alanRef3 = ref.child("users");
//
//        Map<String, Object> alanNickname = new HashMap<>();
//        alanNickname.put("nickname", "Alan The Machine");
//        Map<String, Object> graceNickname = new HashMap<>();
//        graceNickname.put("nickname", "Amazing Grace");
//
//        Map<String, Object> nickname3 = new HashMap<>();
//        nickname3.put("alanisawesome", alanNickname);
//        nickname3.put("gracehop", graceNickname);
//        alanRef3.updateChildren(nickname3);
    }

    /**
     * 3、嘗試使用CompletionListener取得Firebase回傳的寫入結果(成功or失敗的訊息)
     */
    public void testCompletionCallback(){
        ref.setValue("I'm writing data", new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    System.out.println("Data could not be saved. " + firebaseError.getMessage());
                } else {
                    System.out.println("Data saved successfully.");
                }
            }
        });
    }

    /**
     * 4、嘗試使用push()方法，使寫入陣列或MAP類型資料時，系統會自動賦予唯一的ID。
     * ※Firebase provides a push() function that generates a unique ID
     * every time a new child is added to the specified Firebase reference.
     * ※Firebase generated a timestamp-based, unique ID for each blog post,
     * and no write conflicts will occur if multiple users create a blog post at the same time.
     */
    public void testPush(){
        Firebase postRef = ref.child("posts");
        Map<String, String> post1 = new HashMap<>();
        post1.put("author", "gracehop");
        post1.put("title", "Announcing COBOL, a New Programming Language");
        postRef.push().setValue(post1);

        Map<String, String> post2 = new HashMap<>();
        post2.put("author", "alanisawesome");
        post2.put("title", "The Turing Machine");
        postRef.push().setValue(post2);

        //取得push()的unique key。
//        System.out.println("Push Key值：" + postRef.push().getKey());
    }

    /**
     * 5、嘗試從push()中使用getKey()取得唯一的ID值
     */
    public void testGetUniqueIdByPush(){
        // Generate a reference to a new location and add some data using push()
        Firebase postRef = ref.child("posts");
        Firebase newPostRef = postRef.push();

        // Add some data to the new location
        Map<String, String> post1 = new HashMap<>();
        post1.put("author", "gracehop");
        post1.put("title", "Announcing COBOL, a New Programming Language");
        newPostRef.setValue(post1);

        // Get the unique ID generated by push()
        String postId = newPostRef.getKey();
        System.out.println("Push Key值：" + postId);
    }

    /**
     * 6、嘗試使用RunTransaction()方法
     * ※使用此方法可以保證交易最後的正確性
     */
    public void testRunTransaction(){
        Firebase upvotesRef = ref.child("upvotes");

        upvotesRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData currentData) {
                if(currentData.getValue() == null) {
                    currentData.setValue(1);
                } else {
                    currentData.setValue((Long) currentData.getValue() + 1);
                }

                return Transaction.success(currentData); //we can also abort by calling Transaction.abort()
            }

            @Override
            public void onComplete(FirebaseError firebaseError, boolean committed, DataSnapshot currentData) {
                //This method will be called once with the results of the transaction.
            }
        });
    }
}
