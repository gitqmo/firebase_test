package tw.com.taipower.firebasequickstart;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by new on 2016/4/28.
 */
public class UserAuthentication {
    private final static String myFirebaseURL = "https://prework-db.firebaseio.com/";
    Firebase ref = new Firebase(myFirebaseURL);

    /**
     * 1、嘗試儲存匿名登入的使用者資訊
     */
    public void testAuthAnonymously(){
        Firebase authRef = this.ref;

        authRef.authAnonymously(new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                ref.child("users").child(authData.getUid()).setValue(authData.getToken());
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {

            }
        });
    }

    /**
     * 2、嘗試儲存【Email & Password】登入的使用者資訊(尚未撰寫完成)
     */
    public void testUserAuthentication(){
        ref.authWithPassword("jenny@example.com", "correcthorsebatterystaple",
            new Firebase.AuthResultHandler() {
                @Override
                public void onAuthenticated(AuthData authData) {
                    // Authentication just completed successfully :)
                    Map<String, String> map = new HashMap<>();
                    map.put("provider", authData.getProvider());
                    if(authData.getProviderData().containsKey("displayName")) {
                        map.put("displayName", authData.getProviderData().get("displayName").toString());
                    }
                    ref.child("users").child(authData.getUid()).setValue(map);
                }

                @Override
                public void onAuthenticationError(FirebaseError firebaseError) {
                    // Something went wrong :(
                    switch (firebaseError.getCode()) {
                        case FirebaseError.USER_DOES_NOT_EXIST:
                            // handle a non existing user
                            break;
                        case FirebaseError.INVALID_PASSWORD:
                            // handle an invalid password
                            break;
                        default:
                            // handle other errors
                            break;
                    }
                }
            });
    }
}
