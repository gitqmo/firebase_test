package tw.com.taipower.firebasequickstart;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by new on 2016/5/3.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
//        Firebase.getDefaultConfig().setPersistenceEnabled(true);
    }
}
