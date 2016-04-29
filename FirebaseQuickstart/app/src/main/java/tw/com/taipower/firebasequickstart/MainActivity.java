package tw.com.taipower.firebasequickstart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.firebase.client.Firebase;

public class MainActivity extends AppCompatActivity {

    public final static String exampleFirebaseURL = "https://docs-examples.firebaseio.com/web/saving-data/fireblog/posts/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);

        this.setContentView(R.layout.activity_main);

//        this.testSavingData();
//        this.testRetrivingData();
//        this.testStructuringData();
//        this.testUserAuthentication();
        this.testOfflineCapabilities();

    }

    /**
     * 測試寫入Firebase範例
     */
    private void testSavingData() {
        SavingData savingData = new SavingData();

        // test save method 1
        savingData.testSetValue();

        // test updateChildren method 2
        savingData.testUpateChildren();

        // test CompletionListener method 3
//        savingData.testCompletionCallback();

        // test push method 4、5
        savingData.testPush();
        savingData.testGetUniqueIdByPush();

        // test runTransaction method 6
        savingData.testRunTransaction();
    }

    /**
     * 測試讀取Firebase範例
     */
    private void testRetrivingData() {
        RetrivingData retrivingData = new RetrivingData();

        // test read method 1、2
        retrivingData.readDataByValueEventListener();
        retrivingData.readDataByChildEventListener();

        // test order method 3、4
        retrivingData.queryDataOrderByChild();
        retrivingData.queryDataOrderByValue();

        // test limit method 5、6
        retrivingData.queryDataLimitToFirst();
        retrivingData.queryDataLimitToLast();

        // test limit method 7、8
        retrivingData.queryDataInStartAt();
        retrivingData.queryDataBetweenStartAtAndEndAt();

        // test complex condition method 9
        retrivingData.queryDataInComplexCondition();
    }

    /**
     * 測試Good Structuring Data範例
     */
    private void testStructuringData() {
        StructuringData structuringData = new StructuringData();

        // test read method 1、2
        structuringData.testStructuringDataBenifit();
        structuringData.testJoiningFlattenedData();
    }

    /**
     * 測試User Authentication範例
     */
    private void testUserAuthentication() {
        UserAuthentication userAuthentication = new UserAuthentication();

        // test read method 1、2
//        userAuthentication.testAuthAnonymously();
        userAuthentication.testUserAuthentication();
    }

    private void testOfflineCapabilities(){
        OfflineCapabilities offlineCapabilities = new OfflineCapabilities();

//        offlineCapabilities.testQueryingDataOffline();
        offlineCapabilities.testManagingPresence();
    }
}
