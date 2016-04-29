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
     * 1、嘗試使用匿名登入的使用者資訊
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
                System.out.println("Firebase Error:\n"
                        + firebaseError.getCode() + "\n"
                        + firebaseError.getMessage() + "\n"
                        + firebaseError.getDetails());
            }
        });
    }

    /**
     * 2、嘗試使用【Email & Password】登入的使用者資訊
     */
    public void testUserAuthentication(){
        Firebase authRef = this.ref;

        // Create a handler to handle the result of the authentication
        Firebase.AuthResultHandler authResultHandler = new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                // Authenticated successfully with payload authData
                ref.child("users").child(authData.getUid()).setValue(authData.getToken());

                System.out.println("authData:\n"
                        + "Uid:" + authData.getUid() + "\n"
                        + "Provider:" + authData.getProvider() + "\n"
                        + "Token:" + authData.getToken() + "\n"
                        + "ProviderData:" + authData.getProviderData().toString() + "\n"
                        + "Expires:" + authData.getExpires()
                );

                Map<String, String> map = new HashMap<>();
                map.put("provider", authData.getProvider());
                if(authData.getProviderData().containsKey("displayName")) {
                    map.put("displayName", authData.getProviderData().get("displayName").toString());
                }
                ref.child("users").child(authData.getUid()).setValue(map);
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                // Authenticated failed with error firebaseError
                System.out.println("Firebase Error:\n"
                        + "Code:" + firebaseError.getCode() + "\n"
                        + "Message:" + firebaseError.getMessage() + "\n"
                        + "Details:" + firebaseError.getDetails()
                );
            }
        };

        // 方法一：Authenticate users with a custom Firebase token
//        ref.authWithCustomToken("<token>", authResultHandler);

        // (K.O.)方法二：Alternatively, authenticate users anonymously
        ref.authAnonymously(authResultHandler);

        // (K.O.)方法三：Or with an email/password combination
        ref.authWithPassword("abc@abc.com", "abc", authResultHandler);

        // 方法四：Or via popular OAuth providers ("facebook", "github", "google", or "twitter")
//        ref.authWithOAuthToken("<provider>", "<oauth-token>", authResultHandler);
    }
}
